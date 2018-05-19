package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import com.fkmp.gutenberg.backend.model.postgres.Book;
import com.fkmp.gutenberg.backend.repository.PostgreSQLRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class BookServicePostgres implements BookService {

    @Inject
    PostgreSQLRepository repository;

    @Override
    public List<BookDto> getBook(Map<String, String> params) {
        List<Book> books = null;
        if (params.get("title") != null) {
            books = repository.getBooks();
        }

        else if (params.get("city") != null) {
            books = repository.getBooks();
        }

        else if (params.get("author") != null) {
            books = repository.getBooks();
        }

        else if (params.get("lat") != null && params.get("long") != null) {
            books = repository.getBooks();
        }

        else {
            throw new NotFoundException("Query parameter is not correct");
        }

        return BookMapper.postgresBooksToBooksDto(books);
    }
}
