package com.nagarro.assignment.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BookDTO {
    private Long id;
    private String isbn;
    private String name;
    private String serialName;
    private String description;
    private Set<AuthorSimpleDTO> authors;
}
