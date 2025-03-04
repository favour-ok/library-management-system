package com.nagarro.assignment.application.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponsePage<T> extends PageImpl<T> {

    // This constructor is used by Jackson for deserialization
    @JsonCreator
    public RestResponsePage(
            @JsonProperty("content") List<T> content,
            @JsonProperty("number") int number,
            @JsonProperty("size") int size,
            @JsonProperty("totalElements") long totalElements,
            @JsonProperty("pageable") Object pageable, // can be ignored
            @JsonProperty("last") boolean last) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    // Default constructor needed by Jackson
    public RestResponsePage() {
        super(List.of());
    }
}
