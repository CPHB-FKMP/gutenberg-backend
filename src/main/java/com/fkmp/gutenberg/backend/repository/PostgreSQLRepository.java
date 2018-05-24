package com.fkmp.gutenberg.backend.repository;


import com.fkmp.gutenberg.backend.model.postgres.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostgreSQLRepository extends CrudRepository<Book, String> {

    @Query(nativeQuery = true)
    List<Book> getBooksMentioningCity(@Param("cityName") String cityName);

    @Query(nativeQuery = true)
    List<Book> getCitiesByTitle(@Param("bookTitle") String bookTitle);

    @Query(nativeQuery = true)
    List<Book> getBooksByAuthorName(@Param("authorName") String authorName);

    @Query(nativeQuery = true)
    List<Book> getBooksByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}
