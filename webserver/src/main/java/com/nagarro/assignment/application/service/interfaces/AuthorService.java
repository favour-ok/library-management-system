package com.nagarro.assignment.application.service.interfaces;

import com.nagarro.assignment.domain.model.Author;
import com.nagarro.assignment.dto.AuthorDTO;
import com.nagarro.assignment.dto.request.AuthorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> findAllAuthors();

    AuthorDTO findAuthorById(Long id);

    void createAuthor(AuthorRequest author);

    void updateAuthor(AuthorRequest author);

    void deleteAuthor(Long id);

    Page<AuthorDTO> findPaginated(Pageable pageable);

}
