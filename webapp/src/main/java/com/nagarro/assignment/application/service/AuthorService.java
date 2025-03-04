package com.nagarro.assignment.application.service;

import com.nagarro.assignment.dto.AuthorDTO;
import com.nagarro.assignment.dto.request.AuthorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final WebClient webClient;


    public List<AuthorDTO> findAllAuthors() {
        return webClient.get()
                .uri("/api/authors")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(AuthorDTO.class)
                .collectList()
                .block();
    }

    public AuthorDTO findAuthorById(Long id) {
        return webClient.get()
                .uri("/api/authors/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(new RuntimeException("Author not found")))
                .bodyToMono(AuthorDTO.class)
                .block();
    }

    public void createAuthor(AuthorRequest request) {
        webClient.post()
                .uri("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

        public void updateAuthor(Long id, AuthorRequest request) {
        webClient.put()
                .uri("/api/authors/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void deleteAuthor(Long id) {
        webClient.delete()
                .uri("/api/authors/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public Page<AuthorDTO> findPaginated(Pageable pageable) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/authors/paginated")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<AuthorDTO>>() {})
                .block();
    }
}
