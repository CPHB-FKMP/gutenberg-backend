package com.fkmp.gutenberg.backend.model.postgres;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "Book.getBooksMapping", entities = @EntityResult(entityClass = Book.class, fields = {
            @FieldResult(name = "id", column = "book_id"), @FieldResult(name = "title", column = "title")
    }))
})




@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "Book.getBooksMentioningCity",
                query = "SELECT DISTINCT ON (books.book_id) books.book_id, title FROM books NATURAL JOIN books_cities NATURAL JOIN cities WHERE cities.name = :cityName",
                resultSetMapping = "Book.getBooksMapping"
        ),
        @NamedNativeQuery(name = "Book.getCitiesByTitle",
                query = "SELECT DISTINCT ON (books.book_id) books.book_id, books.title, cities.name, cities.latitude, cities.longitude FROM cities NATURAL JOIN books_cities NATURAL JOIN books WHERE books.title = :bookTitle",
                resultSetMapping = "Book.getBooksMapping"
        ),
        @NamedNativeQuery(name = "Book.getBooksByAuthorName",
                query = "SELECT DISTINCT ON (books.book_id) books.book_id, books.title, cities.latitude, cities.longitude, cities.name FROM books NATURAL JOIN authors_books NATURAL JOIN books_cities NATURAL JOIN cities WHERE author_id IN (SELECT author_id FROM authors WHERE name = :authorName );",
                resultSetMapping = "Book.getBooksMapping"
        ),
        @NamedNativeQuery(name = "Book.getBooksByLocation",
                query = "SELECT DISTINCT ON (books.book_id) title, books.book_id FROM books JOIN books_cities ON (books.book_id = books_cities.book_id) JOIN cities ON (books_cities.latitude = cities.latitude AND books_cities.longitude = cities.longitude) GROUP BY (title, books.book_id, cities.latitude, cities.longitude) HAVING geodistance(cities.latitude, cities.longitude, ( :latitude ), ( :longitude )) <= 20;",
                resultSetMapping = "Book.getBooksMapping"
        )
})
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    private String id;

    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "authors_books",
                joinColumns = @JoinColumn(name = "book_id"),
                inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_cities",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id"),
            inverseJoinColumns = {@JoinColumn(name = "latitude", referencedColumnName = "latitude"), @JoinColumn(name = "longitude", referencedColumnName = "longitude")})
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
