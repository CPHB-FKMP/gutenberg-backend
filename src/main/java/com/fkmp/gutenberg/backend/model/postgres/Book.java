package com.fkmp.gutenberg.backend.model.postgres;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMapping(name = "Book.getBooksMapping", entities = @EntityResult(entityClass = Book.class, fields = {
        @FieldResult(name = "id", column = "book_id"), @FieldResult(name = "title", column = "title")
}))

@NamedNativeQueries(
        @NamedNativeQuery(name = "Book.getBooksMentioningCity",
                query = "SELECT DISTINCT ON (books.book_id) books.book_id, title FROM books join books_cities ON (books.book_id = books_cities.book_id) JOIN cities ON (books_cities.latitude = cities.latitude AND books_cities.longitude = cities.longitude) WHERE cities.name = :cityName ;",
                resultSetMapping = "Book.getBooksMapping"
        )
)
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    private String id;

    private String title;

    @ManyToMany
    @JoinTable(name = "authors_books",
                joinColumns = @JoinColumn(name = "book_id"),
                inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @ManyToMany
    @JoinTable(name = "books_cities",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = {@JoinColumn(name = "latitude"), @JoinColumn(name = "longitude")})
    private List<City> cities;

    public Book() {
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
}
