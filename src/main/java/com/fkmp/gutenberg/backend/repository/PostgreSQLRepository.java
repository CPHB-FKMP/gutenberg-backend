package com.fkmp.gutenberg.backend.repository;


import com.fkmp.gutenberg.backend.model.postgres.Book;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostgreSQLRepository extends CrudRepository<Book, String> {

    @Query(value = "SELECT DISTINCT ON (books.book_id) books.book_id, title FROM books join books_cities ON (books.book_id = books_cities.book_id) JOIN cities ON (books_cities.latitude = cities.latitude AND books_cities.longitude = cities.longitude) WHERE cities.name = ?1;", nativeQuery = true)
    List<Book> getBooksMentioningCity(String cityName);

    @Query(value = "SELECT * FROM books LIMIT 10;", nativeQuery = true)
    List<Book> getCitiesByTitle(String bookTitle);

    @Query(value = "SELECT * FROM books LIMIT 10;", nativeQuery = true)
    List<Book> getBooksByAuthorName(String authorName);

    @Query(value = "SELECT title, books.book_id cities.name FROM books JOIN books_cities ON (books.book_id = books_cities.book_id) JOIN cities ON (books_cities.latitude = cities.latitude AND books_cities.longitude = cities.longitude) WHERE cities.location <@ circle(point'(?1)', 0.16);", nativeQuery = true)
    List<Book> getBooksByLocation(Point location);
}
