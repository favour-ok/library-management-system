package com.nagarro.assignment.rest;

import com.nagarro.assignment.application.service.interfaces.AuthorService;
import com.nagarro.assignment.domain.model.Author;
import com.nagarro.assignment.dto.AuthorDTO;
import com.nagarro.assignment.dto.request.AuthorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    public final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(authorService.findAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody AuthorRequest author) {
        authorService.createAuthor(author);
        return new  ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable(name = "id") Long id, @RequestBody AuthorRequest author) {
        author.setId(id);
        authorService.updateAuthor(author);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable(name = "id") Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AuthorDTO>> getAuthorsByPage(
            Pageable pageable) {
        Page<AuthorDTO> authors = authorService.findPaginated(pageable);
        return ResponseEntity.ok(authors);
    }
}
