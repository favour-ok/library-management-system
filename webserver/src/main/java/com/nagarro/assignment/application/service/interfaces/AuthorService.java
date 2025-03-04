package com.nagarro.assignment.application.service.interfaces;

import com.nagarro.assignment.domain.model.Author;
import com.nagarro.assignment.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> findAllAuthors();

    AuthorDTO findAuthorById(Long id);

    void createAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Long id);

    Page<AuthorDTO> findPaginated(Pageable pageable);

}
