package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import com.fkmp.gutenberg.backend.model.postgres.Book;
import com.fkmp.gutenberg.backend.repository.PostgreSQLRepository;
import org.springframework.data.geo.Point;
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
        if (params.get("title") != null) {
            books = repository.getCitiesByTitle(params.get("title"));
        }

        if (params.get("city") != null) {
            books = repository.getBooksMentioningCity(params.get("city"));
        }

        if (params.get("author") != null) {
            books = repository.getBooksByAuthorName(params.get("author"));
        }

        if (params.get("lat") != null && params.get("long") != null) {
            Double latitude = Double.parseDouble(params.get("lat"));
            Double longitude = Double.parseDouble(params.get("long"));
            books = repository.getBooksByLocation(new Point(latitude, longitude));
        }

        if (books == null || books.size() == 0) {
            throw new NotFoundException("Nothing was found. Try querying for something else");
        }

        return BookMapper.postgresBooksToBooksDto(books);
    }
}
