package com.nagarro.assignment.controller;

import com.nagarro.assignment.application.service.AuthorService;
import com.nagarro.assignment.application.service.BookService;
import com.nagarro.assignment.dto.request.BookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @ModelAttribute("book")
    public BookRequest book() {
        return new BookRequest();
    }

    @RequestMapping({ "/", "/books" })
    public String findAllBooks(Model model,
                               @RequestParam(value = "keyword", required = false) Optional<String> keyword,
                               @RequestParam(value = "page", required = false) Optional<Integer> page,
                               @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));

        var books = (keyword.isPresent())
                ? bookService.searchBooks(keyword.get(), pageable)
                : bookService.findPaginated(pageable);

        model.addAttribute("books", books);
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        return "list-books";
    }

    @PostMapping("/add-book")
    public String createBook(@Valid @ModelAttribute("book") BookRequest book,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("allAuthors", authorService.findAllAuthors());
            model.addAttribute("books", bookService.findPaginated(Pageable.ofSize(10))); // Ensure books is populated
            return "list-books";
        }

        try {
            bookService.createBook(book);
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add book: " + e.getMessage());
            model.addAttribute("allAuthors", authorService.findAllAuthors());
            model.addAttribute("books", bookService.findPaginated(Pageable.ofSize(10)));
            return "list-books";
        }
    }

    @PostMapping("/update-book/{id}")
    public String updateBook(@PathVariable(name = "id") Long id,
                             @Valid @ModelAttribute(name = "book") BookRequest bookRequest,
                             BindingResult result,
                             Model model) {
        try {
            System.out.println("Initiating update of book with id: " + id);
            bookService.updateBook(id, bookRequest);
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update book: " + e.getMessage());
            model.addAttribute("allAuthors", authorService.findAllAuthors());
            model.addAttribute("books", bookService.findPaginated(PageRequest.of(0, 10)));
            return "list-books";
        }
    }

    @RequestMapping("/remove-book/{id}")
    public String deleteBook(@PathVariable("id") Long id, Model model) {
        System.out.println("Delete book with id: " + id);
        bookService.deleteBook(id);

        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }
}