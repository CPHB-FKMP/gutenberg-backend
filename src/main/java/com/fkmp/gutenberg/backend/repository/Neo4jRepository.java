package com.fkmp.gutenberg.backend.repository;

import com.fkmp.gutenberg.backend.model.neo4j.Book;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Neo4jRepository extends CrudRepository<Book, String> {

    @Query("MATCH (:City{name:{cityName}})<-[:CONTAINS]-(book:Book)-[r:WRITTEN_BY]->(author:Author) RETURN book, r, author;")
    List<Book> getBooksMentioningCity(@Param("cityName") String cityName);

    @Query("MATCH (b:Book {title: {bookTitle}})-[r:CONTAINS]->(city:City) RETURN b,r,city;")
    List<Book> getCitiesByTitle(@Param("bookTitle") String bookTitle);

    @Query("MATCH (:Author{name:{authorName}})-[:WRITTEN_BY]-(book:Book)-[r:CONTAINS]-(city:City) RETURN book, r, city")
    List<Book> getBooksByAuthor(@Param("authorName") String authorName);

    @Query("WITH {lat} AS lat, {lon} AS lon " +
            "MATCH (l:City) " +
            "WHERE 2 * 6371 * asin(sqrt(haversin(radians(lat - toFloat(split(l.location, \",\")[0])))+ cos(radians(lat))* cos(radians(toFloat(split(l.location, \",\")[0])))* haversin(radians(lon - toFloat(split(l.location, \",\")[1]))))) <= 20 " +
            "MATCH (l)-[:CONTAINS]-(book:Book) " +
            "RETURN book")
    List<Book> getBooksByLocation(@Param("lat") Double latitude, @Param("lon") Double longitude);
}
