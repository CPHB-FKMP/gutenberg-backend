package com.fkmp.gutenberg.backend.repository;


import com.fkmp.gutenberg.backend.model.postgres.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostgreSQLRepository extends CrudRepository<Book, String> {

//    @Query(value = "SELECT * " +
//            "FROM books " +
//            "INNER JOIN books_cities ON (books.book_id = books_cities.book_id) " +
//            "INNER JOIN cities ON (books_cities.latitude = cities.latitude AND books_cities.longitude = cities.longitude) " +
//            "INNER JOIN authors_books ON (books.book_id = authors_books.book_id) " +
//            "INNER JOIN authors ON (authors_books.author_id = authors.author_id) WHERE cities.name = :cityName ;",
//            nativeQuery = true)
//    List<Book> getBooksMentioningCity(@Param("cityName") String cityName);

    @Query("select distinct b from Book b join fetch b.authors join b.cities c where c.name = :cityName")
    List<Book> getBooksMentioningCity(@Param("cityName") String cityName);

    @Query(nativeQuery = true)
    List<Book> getCitiesByTitle(@Param("bookTitle") String bookTitle);

    @Query(nativeQuery = true)
    List<Book> getBooksByAuthorName(@Param("authorName") String authorName);

    @Query(nativeQuery = true)
    List<Book> getBooksByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}
