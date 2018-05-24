package com.fkmp.gutenberg.backend.api.model;

import java.util.List;
import java.util.Objects;

public class BookDto {
    private String id;
    private String title;
    private List<Author> authors;
    private List<City> cities;

    public BookDto() {
    }

    public BookDto(String title) {
        this.title = title;
    }

    public BookDto(String id, String title, List<Author> authors, List<City> cities) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.cities = cities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) &&
                Objects.equals(title, bookDto.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title);
    }
}
