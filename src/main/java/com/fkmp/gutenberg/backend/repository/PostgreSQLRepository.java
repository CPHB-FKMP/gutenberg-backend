package com.fkmp.gutenberg.backend.repository;


import com.fkmp.gutenberg.backend.model.postgres.Book;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostgreSQLRepository extends CrudRepository<Book, String> {

    List<Book> getBooksMentioningCity(@Param("cityName") String cityName);

    @Query(value = "SELECT name FROM cities JOIN books_cities ON (cities.latitude = books_cities.latitude AND cities.longitude = books_cities.longitude) JOIN books ON (books.book_id = books_cities.book_id) WHERE books.title = ?1;", nativeQuery = true)
    List<Book> getCitiesByTitle(String bookTitle);

    @Query(value = "SELECT * FROM books NATURAL JOIN authors_books NATURAL JOIN books_cities NATURAL JOIN cities WHERE author_id IN (SELECT author_id FROM authors WHERE name = ?1);", nativeQuery = true)
    List<Book> getBooksByAuthorName(String authorName);

    @Query(value = "SELECT title, books.book_id cities.name FROM books JOIN books_cities ON (books.book_id = books_cities.book_id) JOIN cities ON (books_cities.latitude = cities.latitude AND books_cities.longitude = cities.longitude) WHERE cities.location <@ circle(point'(?1)', 0.16);", nativeQuery = true)
    List<Book> getBooksByLocation(Point location);
}
