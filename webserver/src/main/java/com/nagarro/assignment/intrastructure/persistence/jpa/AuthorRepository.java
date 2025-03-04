package com.nagarro.assignment.intrastructure.persistence.jpa;

import com.nagarro.assignment.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
