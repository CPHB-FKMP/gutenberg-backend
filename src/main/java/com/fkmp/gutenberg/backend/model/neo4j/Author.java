package com.fkmp.gutenberg.backend.model.neo4j;

import org.neo4j.ogm.annotation.Id;

public class Author {

    @Id
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
