package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.AuthorDto;
import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.api.model.CityDto;
import com.fkmp.gutenberg.backend.model.neo4j.Author;
import com.fkmp.gutenberg.backend.model.neo4j.Book;
import com.fkmp.gutenberg.backend.model.neo4j.City;

import java.util.ArrayList;
import java.util.List;

public class Neo4jBookMapper {
    public static List<BookDto> neo4jBooksToBooksDto(List<Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();

        for (Book fromBook : fromBooks) {
            BookDto toBook = new BookDto();
            toBook.setId(fromBook.getId());
            toBook.setTitle(fromBook.getTitle());

            if (fromBook.getAuthors() != null) {
                List<AuthorDto> authors = new ArrayList<>();
                for (Author fromAuthor : fromBook.getAuthors()) {
                    AuthorDto toAuthor = new AuthorDto();
                    toAuthor.setName(fromAuthor.getName());
                    authors.add(toAuthor);
                }

                toBook.setAuthors(authors);
            }

            if (fromBook.getCities() != null) {
                List<CityDto> cities = new ArrayList<>();

                for (City fromCity : fromBook.getCities()) {
                    CityDto toCity = new CityDto();
                    toCity.setName(fromCity.getName());
                    String[] locations = fromCity.getLocation().split(",");
                    toCity.setLatitude(Double.parseDouble(locations[0]));
                    toCity.setLongitude(Double.parseDouble(locations[1]));
                    cities.add(toCity);
                }

                toBook.setCities(cities);
            }
            toBooks.add(toBook);
        }

        return toBooks;
    }
}
