package com.nagarro.assignment.rest;

import com.nagarro.assignment.application.service.interfaces.BookService;
import com.nagarro.assignment.domain.model.Book;
import com.nagarro.assignment.dto.BookDTO;
import com.nagarro.assignment.dto.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody BookRequest book) {
        bookService.createBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable(name = "id") Long id, @RequestBody BookRequest book) {
        book.setId(id);
        bookService.updateBook(book);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<BookDTO>> getPaginatedBooks(Pageable pageable) {
        Page<BookDTO> books = bookService.findPaginated(pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDTO>> searchBooks(@RequestParam(name = "keyword") String keyword, Pageable pageable) {
        return ResponseEntity.ok(bookService.searchBooks(keyword, pageable));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
