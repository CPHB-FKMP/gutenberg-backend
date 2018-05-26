package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.AuthorDto;
import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.api.model.CityDto;
import com.fkmp.gutenberg.backend.model.postgres.Author;
import com.fkmp.gutenberg.backend.model.postgres.Book;
import com.fkmp.gutenberg.backend.model.postgres.City;

import java.util.ArrayList;
import java.util.List;

public class PostgresBookMapper {
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

            if (book.getAuthors() != null) {
                book.getAuthors().forEach(author -> {
                    authors.add(mapAuthorToDto(author));
                });
                toBook.setAuthors(authors);
            }
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
        cityDto.setLatitude(city.getLatitude());
        cityDto.setLongitude(city.getLongitude());
        return cityDto;
    }

    private static AuthorDto mapAuthorToDto(Author author) {
        return new AuthorDto(author.getName());
    }
}
