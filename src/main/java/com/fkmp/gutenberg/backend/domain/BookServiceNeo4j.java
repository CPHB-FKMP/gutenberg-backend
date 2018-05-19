package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.model.neo4j.Book;
import com.fkmp.gutenberg.backend.repository.Neo4jRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceNeo4j implements BookService {

    @Inject
    Neo4jRepository repository;


    @Override
    public List<BookDto> getBook(Map<String, String> params) {
        List<Book> books = null;
        if (params.get("title") != null) {
            books = repository.getBooks();
        }

        if (params.get("city") != null) {
            books = repository.getBooks();
        }

        if (params.get("author") != null) {
            books = repository.getBooks();
        }

        if (params.get("lat") != null && params.get("long") != null) {
            books = repository.getBooks();
        }

        return BookMapper.neo4jBooksToBooksDto(books);
    }
}
