package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.model.neo4j.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {
    public static List<BookDto> neo4jBooksToBooksDto(List<Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();

        for (Book fromBook : fromBooks) {
            BookDto toBook = new BookDto();
        }

        return toBooks;
    }

    public static List<BookDto> postgresBooksToBooksDto(List<com.fkmp.gutenberg.backend.model.postgres.Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();

        for (com.fkmp.gutenberg.backend.model.postgres.Book fromBook : fromBooks) {
            BookDto toBook = new BookDto();
        }

        return toBooks;
    }
}
