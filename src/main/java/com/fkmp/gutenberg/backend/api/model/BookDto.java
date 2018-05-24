package com.fkmp.gutenberg.backend.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private String id;
    private String title;
    private List<AuthorDto> authorDtos;
    private List<CityDto> cities;

    public BookDto() {
    }

    public BookDto(String title) {
        this.title = title;
    }

    public BookDto(String id, String title, List<AuthorDto> authorDtos, List<CityDto> cities) {
        this.id = id;
        this.title = title;
        this.authorDtos = authorDtos;
        this.cities = cities;
    }

    public BookDto(String id, String title) {
        this.id = id;
        this.title = title;
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

    public List<AuthorDto> getAuthors() {
        return authorDtos;
    }

    public void setAuthors(List<AuthorDto> authorDtos) {
        this.authorDtos = authorDtos;
    }

    public List<CityDto> getCities() {
        return cities;
    }

    public void setCities(List<CityDto> cities) {
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
