package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@ActiveProfiles(profiles = "itest", inheritProfiles = false)
public class Neo4JIT extends AbstractResourceTests {

    private final String PATH = "/neo4j/book";

    @Test
    @Description("Returns all book titles and authors in a given city")
    public void getBooksOnCity(){
        getBooksOnCityTest(PATH);
    }

    @Test
    @Description("Returns all cities mention in a given book title")
    public void getBookOnTitle(){
        getBooksOnTitleTest(PATH);
    }

    @Test
    @Description("Returns all books on the given author")
    public void getBooksOnAuthor(){
        getBooksOnAuthorTest(PATH);
    }

    @Test
    @Description("#4 - Returns all Books in a given vicinity")
    public void getBooksOnGeolocation(){
        // Expected book list
        ArrayList<BookDto> expectedBooks = new ArrayList<>();;
        expectedBooks.add(new BookDto());

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("lat", "74.000");
        params.putSingle("long", "12.000");
        Response response = getRequest("/neo4j/book", params).get();

        // Collect the result from the response
        ArrayList<BookDto> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result, "Books");
    }
}
