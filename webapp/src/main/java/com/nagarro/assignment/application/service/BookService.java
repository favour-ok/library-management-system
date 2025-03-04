package com.nagarro.assignment.application.service;

import com.nagarro.assignment.dto.BookDTO;
import com.nagarro.assignment.dto.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final WebClient webClient;


    public List<BookDTO> findAllBooks() {
        return webClient.get()
                .uri("/api/books")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BookDTO.class)
                .collectList()
                .block();
    }

    public BookDTO findBookById(Long id) {
        return webClient.get()
                .uri("/api/books/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(new RuntimeException("Book not found")))
                .bodyToMono(BookDTO.class)
                .block();
    }

    public void createBook(BookRequest bookDTO) {
        webClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookDTO)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void updateBook(Long id, BookRequest request) {
        webClient.put()
                .uri("/api/books/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public Page<BookDTO> findPaginated(Pageable pageable) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/books/paginated")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<BookDTO>>(){})
                .block();
    }

    public Page<BookDTO> searchBooks(String keyword, Pageable pageable) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/books/search")
                        .queryParam("keyword", keyword)
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<BookDTO>>(){})
                .block();
    }

    public void deleteBook(@PathVariable Long id) {
        webClient.delete()
                .uri("/api/books/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
