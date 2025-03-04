package com.nagarro.assignment.application.mapper;

import com.nagarro.assignment.domain.model.Author;
import com.nagarro.assignment.domain.model.Book;
import com.nagarro.assignment.dto.AuthorDTO;
import com.nagarro.assignment.dto.BookSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO toAuthorDTO(Author author);

    @Mapping(target = "books", source = "books")
    AuthorDTO toDetailedAuthorDTO(Author author);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "isbn", source = "isbn")
    BookSimpleDTO toBookSimpleDTO(Book book);

    default Set<BookSimpleDTO> mapBooks(Set<Book> books) {
        return books.stream()
                .map(this::toBookSimpleDTO)
                .collect(Collectors.toSet());
    }
}