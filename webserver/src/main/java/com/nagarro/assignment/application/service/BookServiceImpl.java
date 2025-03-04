package com.nagarro.assignment.application.service;

import com.nagarro.assignment.application.mapper.BookMapper;
import com.nagarro.assignment.application.service.interfaces.BookService;
import com.nagarro.assignment.domain.model.Author;
import com.nagarro.assignment.domain.model.Book;
import com.nagarro.assignment.dto.BookDTO;
import com.nagarro.assignment.dto.request.BookRequest;
import com.nagarro.assignment.exception.NotFoundException;
import com.nagarro.assignment.intrastructure.persistence.jpa.AuthorRepository;
import com.nagarro.assignment.intrastructure.persistence.jpa.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toBookDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<BookDTO> searchBooks(String keyword, Pageable pageable) {
        if (keyword != null) {
            return bookRepository.search(keyword, pageable).map(bookMapper::toBookDTO);
        }
        return bookRepository.findAll(pageable).map(bookMapper::toBookDTO);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book not found with ID %d", id)));
        return bookMapper.toBookDTO(book);
    }

    @Override
    public void createBook(BookRequest book) {
        Book created = buildBookFromRequest(book);
        bookRepository.save(created);
    }

    @Override
    public void updateBook(BookRequest book) {
        // Fetch the existing book first
        Book existingBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + book.getId()));

        // Update the existing book's properties
        existingBook.setIsbn(book.getIsbn());
        existingBook.setName(book.getName());
        existingBook.setSerialName(book.getSerialName());
        existingBook.setDescription(book.getDescription());

        // Save the updated book
        bookRepository.save(existingBook);
    }

    private Book buildBookFromRequest(BookRequest bookRequest) {
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(bookRequest.getAuthorIds()));
        Book book = Book.builder()
                .isbn(bookRequest.getIsbn())
                .name(bookRequest.getName())
                .serialName(bookRequest.getSerialName())
                .description(bookRequest.getDescription())
                .build();
        book.setAuthors(authors);
        return book;
    }

    @Override
    public void deleteBook(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book not found with ID %d", id)));
        bookRepository.deleteById(book.getId());
    }

    @Override
    public Page<BookDTO> findPaginated(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookDTO);
    }
}
