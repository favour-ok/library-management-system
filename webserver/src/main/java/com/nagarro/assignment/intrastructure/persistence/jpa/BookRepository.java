package com.nagarro.assignment.intrastructure.persistence.jpa;

import com.nagarro.assignment.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', ?1, '%'))" +
            " OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', ?1, '%'))" +
            " OR LOWER(b.serialName) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Book> search(String keyword, Pageable pageable);
}
