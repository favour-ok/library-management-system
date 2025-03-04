package com.nagarro.assignment.application.service;

import com.nagarro.assignment.application.mapper.AuthorMapper;
import com.nagarro.assignment.application.service.interfaces.AuthorService;
import com.nagarro.assignment.domain.model.Author;
import com.nagarro.assignment.dto.AuthorDTO;
import com.nagarro.assignment.exception.NotFoundException;
import com.nagarro.assignment.intrastructure.persistence.jpa.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toAuthorDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public AuthorDTO findAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));
        return authorMapper.toDetailedAuthorDTO(author);
    }

    @Override
    public void createAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Author not found with ID %d", id)));

        authorRepository.deleteById(author.getId());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<AuthorDTO> findPaginated(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::toAuthorDTO);
    }
}