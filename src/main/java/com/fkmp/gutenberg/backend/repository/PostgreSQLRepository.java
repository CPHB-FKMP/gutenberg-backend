package com.fkmp.gutenberg.backend.repository;


import com.fkmp.gutenberg.backend.model.Neo4jBook;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostgreSQLRepository {

    @Query(value = "", nativeQuery = false)
    List<Neo4jBook> getBooks();
}
