package com.fkmp.gutenberg.backend.repository;


import com.fkmp.gutenberg.backend.model.postgres.Book;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostgreSQLRepository extends CrudRepository<Book, String> {

    @Query("")
    List<Book> getBooksByCity(String cityName);

    @Query("")
    List<Book> getCitiesByTitle(String bookTitle);

    @Query("")
    List<Book> getBooksByAuthor(String authorName);

    @Query("")
    List<Book> getBooksByLocation(Point location);
}
