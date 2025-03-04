package com.nagarro.assignment.application.service.interfaces;

import com.nagarro.assignment.domain.model.Book;
import com.nagarro.assignment.dto.BookDTO;
import com.nagarro.assignment.dto.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    List<BookDTO> findAllBooks();

    Page<BookDTO> searchBooks(String keyword, Pageable pageable);

    BookDTO findBookById(Long id);

    void createBook(BookRequest book);

    void updateBook(BookRequest book);

    void deleteBook(Long id);

    Page<BookDTO> findPaginated(Pageable pageable);
}
