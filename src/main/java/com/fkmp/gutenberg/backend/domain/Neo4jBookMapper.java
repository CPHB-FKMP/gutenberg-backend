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
    public static List<BookDto> mapWithCities(List<Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();


        for (Book book : fromBooks) {
            List<CityDto> cities = new ArrayList<>();
            BookDto toBook = mapBookToDto(book);

            for (City fromCity : book.getCities()) {
                cities.add(mapCityToDto(fromCity));
            }
            toBook.setCities(cities);
            toBooks.add(toBook);
        }
        return toBooks;
    }

    public static List<BookDto> mapWithAuthors(List<Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();


        for (Book book : fromBooks) {
            List<AuthorDto> authors = new ArrayList<>();
            BookDto toBook = mapBookToDto(book);

            for (Author author : book.getAuthors()) {
                authors.add(mapAuthorToDto(author));
            }
            toBook.setAuthors(authors);
            toBooks.add(toBook);
        }
        return toBooks;
    }

    public static List<BookDto> map(List<Book> fromBooks) {
        List<BookDto> toBooks = new ArrayList<>();
        for (Book book : fromBooks) {
            toBooks.add(mapBookToDto(book));
        }
        return toBooks;
    }


    private static BookDto mapBookToDto(Book book) {
        return new BookDto(book.getId(), book.getTitle());
    }

    private static CityDto mapCityToDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setName(city.getName());
        String[] location = city.getLocation().split(",");
        cityDto.setLatitude(Double.parseDouble(location[0]));
        cityDto.setLongitude(Double.parseDouble(location[0]));
        return cityDto;
    }

    private static AuthorDto mapAuthorToDto(Author author) {
        return new AuthorDto(author.getName());
    }
}
