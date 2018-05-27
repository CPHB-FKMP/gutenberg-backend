package com.fkmp.gutenberg.backend.model.postgres;

import javax.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Column(name = "author_id")
    private long id;

    private String name;

    public Author() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
