package com.fkmp.gutenberg.backend.repository;

import com.fkmp.gutenberg.backend.model.neo4j.Book;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Neo4jRepository extends CrudRepository<Book, String> {

    // MATCH (:City{name:"Copenhagen"})-[:CONTAINS]-(book:Book)-[:WRITTEN_BY]-(author:Author) RETURN book, author
    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getBooksMentioningCity(String cityName);

    // MATCH (:Book{title:"Morals and the Evolution of Man"})-[:CONTAINS]-(city:City) RETURN city
    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getCitiesByTitle(String bookTitle);

    // MATCH (:Author{name:"Maxime Provost"})-[:WRITTEN_BY]-(book:Book)-[:CONTAINS]-(city:City) RETURN book.title, city
    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getBooksByAuthor(String authorName);

    // WITH 55.675940 AS lat, 12.565530 AS lon
    //MATCH (l:City)
    //WHERE 2 * 6371 * asin(sqrt(haversin(radians(lat - toFloat(split(l.location, ",")[0])))+ cos(radians(lat))* cos(radians(toFloat(split(l.location, ",")[0])))* haversin(radians(lon - toFloat(split(l.location, ",")[1]))))) < 20
    //MATCH (l)-[:CONTAINS]-(book:Book)
    //RETURN l
    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getBooksByLocation(Point location);

    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getAll();
}
