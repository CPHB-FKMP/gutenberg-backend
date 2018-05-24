package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import org.junit.Assert;
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

@ActiveProfiles(profiles = "itest", inheritProfiles = false)
public class PostgresIT extends AbstractResourceTests {

    @Test
    @Description("Returns all book titles and authors in a given city")
    public void getBooksOnCity(){
        // Expected book list
        ArrayList<BookDto> expectedBooks = new ArrayList<>();
        expectedBooks.add(new BookDto());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("city", "London");
        Response response = getRequest("/postgres/book", params).get();

        Assert.assertEquals(200, response.getStatus());
        // Collect the result from the response
        ArrayList<BookDto> result = response.readEntity(BookList.class);


        testArray(expectedBooks, result);
    }

    @Test
    @Description("Returns all cities mention in a given book title")
    public void getBookOnTitle(){
        // Expected book list
        ArrayList<BookDto> expectedBooks = new ArrayList<>();
        expectedBooks.add(new BookDto());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("title", "Unknown title");
        Response response = getRequest("/postgres/book", params).get();

        // Collect the result from the response
        ArrayList<BookDto> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result);
    }

    @Test
    @Description("Returns all books on the given author")
    public void getBooksOnAuthor(){
        // Expected book list
        ArrayList<BookDto> expectedBooks = new ArrayList<>();;
        expectedBooks.add(new BookDto());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("author", "XXXX");
        Response response = getRequest("/postgres/book", params).get();

        // Collect the result from the response
        ArrayList<BookDto> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result);

    }

    @Test
    @Description("#4 - Returns all Books in a given vicinity")
    public void getBooksOnGeolocation(){
        // Expected book list
        ArrayList<BookDto> expectedBooks = new ArrayList<>();;
        expectedBooks.add(new BookDto());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("lat", "");
        params.putSingle("long", "XXXX");
        Response response = getRequest("/postgres/book", params).get();

        // Collect the result from the response
        ArrayList<BookDto> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result);
    }
}
