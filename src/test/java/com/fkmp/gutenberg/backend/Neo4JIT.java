package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "itest", inheritProfiles = false)
public class Neo4JIT extends AbstractResourceTests {

    @Test
    @Description("Returns all book titles and authors in a given city")
    public void getBooksOnCity(){
        // Expected book list
        ArrayList<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("city", "XXXX");
        Response response = getRequest("/neo4j/book", params).get();

        // Collect the result from the response
        ArrayList<Book> result = response.readEntity(BookList.class);


        testArray(expectedBooks, result);
    }

    @Test
    @Description("Returns all cities mention in a given book title")
    public void getBookOnTitle(){
        // Expected book list
        ArrayList<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("title", "XXXX");
        Response response = getRequest("/neo4j/book", params).get();

        // Collect the result from the response
        ArrayList<Book> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result);
    }

    @Test
    @Description("Returns all books on the given author")
    public void getBooksOnAuthor(){
        // Expected book list
        ArrayList<Book> expectedBooks = new ArrayList<>();;
        expectedBooks.add(new Book());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("author", "XXXX");
        Response response = getRequest("/neo4j/book", params).get();

        // Collect the result from the response
        ArrayList<Book> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result);

    }

    @Test
    @Description("#4 - Returns all Books in a given vicinity")
    public void getBooksOnGeolocation(){
        // Expected book list
        ArrayList<Book> expectedBooks = new ArrayList<>();;
        expectedBooks.add(new Book());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("lat", "XXXX");
        params.putSingle("long", "XXXX");
        Response response = getRequest("/neo4j/book", params).get();

        // Collect the result from the response
        ArrayList<Book> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result);
    }
}
