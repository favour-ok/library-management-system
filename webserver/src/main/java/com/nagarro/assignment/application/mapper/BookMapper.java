package com.nagarro.assignment.application.mapper;

import com.nagarro.assignment.domain.model.Author;
import com.nagarro.assignment.domain.model.Book;
import com.nagarro.assignment.dto.AuthorSimpleDTO;
import com.nagarro.assignment.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toBookDTO(Book book);

    @Mapping(target = "authors", source = "authors")
    BookDTO toDetailedBookDTO(Book book);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AuthorSimpleDTO toAuthorSimpleDTO(Author author);

    default Set<AuthorSimpleDTO> mapAuthors(Set<Author> authors) {
        return authors.stream()
                .map(this::toAuthorSimpleDTO)
                .collect(Collectors.toSet());
    }
}
