package com.fkmp.gutenberg.backend.repository;

import com.fkmp.gutenberg.backend.model.neo4j.Book;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Neo4jRepository extends CrudRepository<Book, String> {

    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getBooksMentioningCity(String cityName);

    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getCitiesByTitle(String bookTitle);

    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getBooksByAuthor(String authorName);

    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getBooksByLocation(Point location);

    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getAll();
}
