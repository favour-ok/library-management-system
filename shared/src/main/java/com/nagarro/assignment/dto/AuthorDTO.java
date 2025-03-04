package com.nagarro.assignment.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private String description;
    private Set<BookDTO> books;
}
