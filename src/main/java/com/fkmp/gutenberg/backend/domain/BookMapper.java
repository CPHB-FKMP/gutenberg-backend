package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.model.neo4j.Author;
import com.fkmp.gutenberg.backend.model.neo4j.Book;
import com.fkmp.gutenberg.backend.model.neo4j.City;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    public static List<BookDto> neo4jBooksToBooksDto(List<Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();

        for (Book fromBook : fromBooks) {
            BookDto toBook = new BookDto();
            toBook.setId(fromBook.getId());
            toBook.setTitle(fromBook.getTitle());

            if (fromBook.getAuthors() != null) {
                List<com.fkmp.gutenberg.backend.api.model.Author> authors = new ArrayList<>();
                for (Author fromAuthor : fromBook.getAuthors()) {
                    com.fkmp.gutenberg.backend.api.model.Author toAuthor = new com.fkmp.gutenberg.backend.api.model.Author();
                    toAuthor.setName(fromAuthor.getName());
                    authors.add(toAuthor);
                }

                toBook.setAuthors(authors);
            }

            if (fromBook.getCities() != null) {
                List<com.fkmp.gutenberg.backend.api.model.City> cities = new ArrayList<>();

                for (City fromCity : fromBook.getCities()) {
                    com.fkmp.gutenberg.backend.api.model.City toCity = new com.fkmp.gutenberg.backend.api.model.City();
                    toCity.setName(fromCity.getName());
                    toCity.setLatitude(Double.toString(fromCity.getLocation().getX()));
                    toCity.setLongitude(Double.toString(fromCity.getLocation().getY()));
                    cities.add(toCity);
                }

                toBook.setCities(cities);
            }
            toBooks.add(toBook);
        }

        return toBooks;
    }

    public static List<BookDto> postgresBooksToBooksDto(List<com.fkmp.gutenberg.backend.model.postgres.Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();

        for (com.fkmp.gutenberg.backend.model.postgres.Book fromBook : fromBooks) {
            BookDto toBook = new BookDto();
            toBook.setId(fromBook.getId());
            toBook.setTitle(fromBook.getTitle());

            if (fromBook.getAuthors() != null) {
                List<com.fkmp.gutenberg.backend.api.model.Author> authors = new ArrayList<>();
                for (com.fkmp.gutenberg.backend.model.postgres.Author fromAuthor : fromBook.getAuthors()) {
                    com.fkmp.gutenberg.backend.api.model.Author toAuthor = new com.fkmp.gutenberg.backend.api.model.Author();
                    toAuthor.setName(fromAuthor.getName());
                    authors.add(toAuthor);
                }
                toBook.setAuthors(authors);
            }

            if (fromBook.getCities() != null) {
                List<com.fkmp.gutenberg.backend.api.model.City> cities = new ArrayList<>();
                for (com.fkmp.gutenberg.backend.model.postgres.City fromCity : fromBook.getCities()) {
                    com.fkmp.gutenberg.backend.api.model.City toCity = new com.fkmp.gutenberg.backend.api.model.City();
                    toCity.setName(fromCity.getName());
                    toCity.setLatitude(Double.toString(fromCity.getLatitude()));
                    toCity.setLongitude(Double.toString(fromCity.getLongitude()));
//                    toCity.setLatitude(Double.toString(fromCity.getLocation().getX()));
//                    toCity.setLongitude(Double.toString(fromCity.getLocation().getY()));
                    cities.add(toCity);
                }
                toBook.setCities(cities);
            }
            toBooks.add(toBook);
        }

        return toBooks;
    }
}
