package com.fkmp.gutenberg.backend.repository;

import com.fkmp.gutenberg.backend.model.Neo4jBook;
import org.springframework.data.neo4j.annotation.Query;

import java.util.List;

public interface Neo4jRepository {

    @Query("")
    List<Neo4jBook> getBooks();
}
