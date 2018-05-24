package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.AuthorDto;
import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.api.model.CityDto;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import com.fkmp.gutenberg.backend.model.postgres.Book;
import com.fkmp.gutenberg.backend.repository.PostgreSQLRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Service
@Named("postgres.book.service")
public class PostgresBookService implements BookService {

    @Inject
    private PostgreSQLRepository repository;

    @Override
    public List<BookDto> getBook(Map<String, String> params) {
        List<Book> books = null;
        List<AuthorDto> authorDtos;
        List<CityDto> cities;
        if (params.get("title") != null) {
            books = repository.getCitiesByTitle(params.get("title"));
            return PostgresBookMapper.mapWithCities(books);
        }

        if (params.get("city") != null) {
            books = repository.getBooksMentioningCity(params.get("city"));
            return PostgresBookMapper.mapWithAuthors(books);
        }

        if (params.get("author") != null) {
            books = repository.getBooksByAuthorName(params.get("author"));
            return PostgresBookMapper.mapWithCities(books);
        }

        if (params.get("lat") != null && params.get("long") != null) {
            Double latitude = Double.parseDouble(params.get("lat"));
            Double longitude = Double.parseDouble(params.get("long"));
            books = repository.getBooksByLocation(latitude, longitude);
            return PostgresBookMapper.map(books);
        }

        throw new NotFoundException("Query not valid. Try again");
    }
}
