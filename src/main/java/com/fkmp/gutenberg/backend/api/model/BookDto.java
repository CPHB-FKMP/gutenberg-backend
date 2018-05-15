package com.fkmp.gutenberg.backend.api.model;

import java.util.List;

public class BookDto {
    private String title;
    private List<Author> authors;
    private List<City> cities;

    public BookDto() {
    }

    public BookDto(String title) {
        this.title = title;
    }

    public BookDto(String title, List<Author> authors, List<City> cities) {
        this.title = title;
        this.authors = authors;
        this.cities = cities;
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
}
