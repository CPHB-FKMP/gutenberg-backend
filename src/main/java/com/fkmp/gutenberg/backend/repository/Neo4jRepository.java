package com.fkmp.gutenberg.backend.repository;

import com.fkmp.gutenberg.backend.model.neo4j.Book;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Neo4jRepository extends CrudRepository<Book, String> {

    /* Kasper og Phillips
    // MATCH (:City{name:"Copenhagen"})-[:CONTAINS]-(book:Book)-[:WRITTEN_BY]-(author:Author) RETURN book, author
    @Query("MATCH (n:Book) RETURN n LIMIT 10")
    List<Book> getBooksMentioningCity(String cityName);
    */
    @Query("MATCH (book:Book)-[:CONTAINS]->(city:City {name: {cityName}}) RETURN book LIMIT 1;")
    List<Book> getBooksMentioningCity(@Param("cityName") String cityName);

    @Query("MATCH (:Book{title:{bookTitle})-[:CONTAINS]-(city:City) RETURN city")
    List<Book> getCitiesByTitle(@Param("bookTitle") String bookTitle);

    @Query("MATCH (:Author{name:{authorName})-[:WRITTEN_BY]-(book:Book)-[:CONTAINS]-(city:City) RETURN book.title, city")
    List<Book> getBooksByAuthor(@Param("authorName") String authorName);

    @Query("WITH {lat} AS lat, {lon} AS lon\n" +
            "MATCH (l:City) \n" +
            "WHERE 2 * 6371 * asin(sqrt(haversin(radians(lat - toFloat(split(l.location, \",\")[0])))+ cos(radians(lat))* cos(radians(toFloat(split(l.location, \",\")[0])))* haversin(radians(lon - toFloat(split(l.location, \",\")[1]))))) < 20\n" +
            "MATCH (l)-[:CONTAINS]-(book:Book)\n" +
            "RETURN l")
    List<Book> getBooksByLocation(@Param("lat") Double latitude, @Param("lon") Double longitude);
}
