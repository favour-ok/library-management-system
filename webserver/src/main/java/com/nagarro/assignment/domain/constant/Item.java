package com.nagarro.assignment.domain.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Item {
    BOOK("all-book", "book-list.csv"), AUTHOR("all-author", "Author-List.csv");

    private final String name;
    private final String fileName;

    public static Optional<Item> getItemByValue(String value) {
        return Arrays.stream(Item.values())
                .filter(item -> item.name.equals(value) || item.fileName.equals(value)).findFirst();
    }
}
