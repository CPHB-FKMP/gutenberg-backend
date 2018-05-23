package com.fkmp.gutenberg.backend.repository;


import com.fkmp.gutenberg.backend.model.postgres.Book;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostgreSQLRepository extends CrudRepository<Book, String> {

    @Query(value = "SELECT * FROM books LIMIT 10;", nativeQuery = true)
    List<Book> getBooksMentioningCity(String cityName);

    @Query(value = "SELECT * FROM books LIMIT 10;", nativeQuery = true)
    List<Book> getCitiesByTitle(String bookTitle);

    @Query(value = "SELECT * FROM books LIMIT 10;", nativeQuery = true)
    List<Book> getBooksByAuthorName(String authorName);

    @Query(value = "SELECT * FROM books LIMIT 10;", nativeQuery = true)
    List<Book> getBooksByLocation(Point location);
}
