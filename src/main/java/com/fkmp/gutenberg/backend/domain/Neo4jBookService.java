package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import com.fkmp.gutenberg.backend.model.neo4j.Book;
import com.fkmp.gutenberg.backend.repository.Neo4jRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Service
@Named("neo4j.book.service")
public class Neo4jBookService implements BookService {

    @Inject
    private Neo4jRepository repository;

    @Override
    public List<BookDto> getBook(Map<String, String> params) {
        List<Book> books = null;
        if (params.get("title") != null) {
            books = repository.getCitiesByTitle(params.get("title"));
            return Neo4jBookMapper.mapWithCities(books);
        }

        if (params.get("city") != null) {
            books = repository.getBooksMentioningCity(params.get("city"));
            return Neo4jBookMapper.mapWithAuthors(books);
        }

        if (params.get("author") != null) {
            books = repository.getBooksByAuthor(params.get("author"));
            return Neo4jBookMapper.mapWithCities(books);
        }

        if (params.get("lat") != null && params.get("long") != null) {
            Double latitude = Double.parseDouble(params.get("lat"));
            Double longitude = Double.parseDouble(params.get("long"));
            books = repository.getBooksByLocation(latitude, longitude);
            return Neo4jBookMapper.map(books);
        }

        throw new NotFoundException("Query was not valid. Try again");


    }
}
