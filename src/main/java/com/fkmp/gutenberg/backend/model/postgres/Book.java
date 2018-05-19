package com.fkmp.gutenberg.backend.model.postgres;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

public class Book {

    @Id
    @Column(name = "book_id")
    private String id;

    private String title;

    @ManyToMany
    private List<Author> authors;

    @ManyToMany
    private List<City> cities;
}
