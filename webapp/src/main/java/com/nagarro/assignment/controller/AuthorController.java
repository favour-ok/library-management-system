package com.nagarro.assignment.controller;

import com.nagarro.assignment.application.service.AuthorService;
import com.nagarro.assignment.dto.request.AuthorRequest;
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
class AuthorController {

    private final AuthorService authorService;

    @ModelAttribute("author")
    public AuthorRequest author() {
        return new AuthorRequest();
    }

    @RequestMapping("/authors")
    public String findAllAuthors(Model model,
                                 @RequestParam(value = "page", required = false) Optional<Integer> page,
                                 @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        var authors = authorService.findPaginated(pageable);
        System.out.println("authors: " + authors.getContent());
        model.addAttribute("allAuthors", authors);
        return "list-authors";
    }

    @PostMapping("/add-author")
    public String createAuthor(@Valid @ModelAttribute("book") AuthorRequest author, BindingResult result, Model model) {
        System.out.println("Author: " +  author);
        if (result.hasErrors()) {
            model.addAttribute("author", authorService.findPaginated(Pageable.ofSize(10)));
            return "list-authors";
        }

        try {
            authorService.createAuthor(author);
            return "redirect:/authors";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add book: " + e.getMessage());
            model.addAttribute("author", authorService.findPaginated(Pageable.ofSize(10)));
            return "list-authors";
        }
    }

    @PostMapping("/update-author/{id}")
    public String updateAuthor(@PathVariable(name = "id") Long id,
                             @Valid @ModelAttribute(name = "author") AuthorRequest author,
                             BindingResult result,
                             Model model) {
        try {
            System.out.println("Initiating update of book with id: " + id);
            authorService.updateAuthor(id, author);
            return "redirect:/authors";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update book: " + e.getMessage());
            model.addAttribute("author", authorService.findPaginated(Pageable.ofSize(10)));
            return "list-authors";
        }
    }

    @RequestMapping("/remove-author/{id}")
    public String deleteAuthors(@PathVariable("id") Long id, Model model) {
        authorService.deleteAuthor(id);

        model.addAttribute("author", authorService.findPaginated(Pageable.ofSize(10)));
        return "redirect:/authors";
    }
}
