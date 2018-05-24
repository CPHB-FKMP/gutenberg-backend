package com.fkmp.gutenberg.backend.repository;

import com.fkmp.gutenberg.backend.model.neo4j.Book;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Neo4jRepository extends CrudRepository<Book, String> {

    @Query("MATCH (:CityDto{name:{cityName}})<-[:CONTAINS]-(book:Book)-[r:WRITTEN_BY]->(author:AuthorDto) RETURN book, r, author;")
    List<Book> getBooksMentioningCity(@Param("cityName") String cityName);

    @Query("MATCH (b:Book {title: {bookTitle}})-[r:CONTAINS]->(city:CityDto) RETURN b,r,city;")
    List<Book> getCitiesByTitle(@Param("bookTitle") String bookTitle);

    @Query("MATCH (:AuthorDto{name:{authorName}})-[:WRITTEN_BY]-(book:Book)-[r:CONTAINS]-(city:CityDto) RETURN book, r, city")
    List<Book> getBooksByAuthor(@Param("authorName") String authorName);

    @Query("WITH {lat} AS lat, {lon} AS lon\n" +
            "MATCH (l:CityDto) \n" +
            "WHERE 2 * 6371 * asin(sqrt(haversin(radians(lat - toFloat(split(l.location, \",\")[0])))+ cos(radians(lat))* cos(radians(toFloat(split(l.location, \",\")[0])))* haversin(radians(lon - toFloat(split(l.location, \",\")[1]))))) < 20\n" +
            "MATCH (l)-[:CONTAINS]-(book:Book)\n" +
            "RETURN book")
    List<Book> getBooksByLocation(@Param("lat") Double latitude, @Param("lon") Double longitude);
}
