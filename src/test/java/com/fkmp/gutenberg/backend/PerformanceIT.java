package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.context.ActiveProfiles;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@ActiveProfiles(profiles = "performance", inheritProfiles = false)
public class PerformanceIT extends AbstractResourceTests {

    String path;
    int port;

    public PerformanceIT(String path, int port) {
        this.path = path;
        this.port = port;
    }

    @Parameterized.Parameters(name = "{index}: testEquals({0})")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
            {"/postgres/book", 8081},
            {"/neo4j/book", 8081}
        });
    }

    @Test
    @DisplayName("City")
    public void getCity(){
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("city", "London");

        long startTime = System.nanoTime();
        Response response = getRequest(path, params, port).get();
        long endTime = System.nanoTime() - startTime;
        double totalTime = ((double) endTime / 1000000000.0);
        System.out.println(totalTime);

        ArrayList<BookDto> result = response.readEntity(BookList.class);
        Assertions.assertAll("Must be done in order to know there where data", () -> {
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertTrue(!result.isEmpty());
        });
    }

    @Test
    @DisplayName("Title")
    public void getTitle(){
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("title", "Unknown title");

        long startTime = System.nanoTime();
        Response response = getRequest(path, params, port).get();
        long endTime = System.nanoTime() - startTime;
        double totalTime = ((double) endTime / 1000000000.0);
        System.out.println(totalTime);

        ArrayList<BookDto> result = response.readEntity(BookList.class);
        Assertions.assertAll("Must be done in order to know there where data", () -> {
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertTrue(!result.isEmpty());
        });
    }

    @Test
    @DisplayName("Author")
    public void getAuthor(){
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("author", "Max Simon Nordau");

        long startTime = System.nanoTime();
        Response response = getRequest(path, params, port).get();
        long endTime = System.nanoTime() - startTime;
        double totalTime = ((double) endTime / 1000000000.0);
        System.out.println(totalTime);

        ArrayList<BookDto> result = response.readEntity(BookList.class);
        Assertions.assertAll("Must be done in order to know there where data", () -> {
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertTrue(!result.isEmpty());
        });
    }

    @Test
    @DisplayName("Geoloaction")
    public void getGeolocation(){
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("lat", "51.50853");
        params.putSingle("long", "-0.12574");

        long startTime = System.nanoTime();
        Response response = getRequest(path, params, port).get();
        long endTime = System.nanoTime() - startTime;
        double totalTime = ((double) endTime / 1000000000.0);
        System.out.println(totalTime);

        ArrayList<BookDto> result = response.readEntity(BookList.class);
        Assertions.assertAll("Must be done in order to know there where data", () -> {
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertTrue(!result.isEmpty());
        });
    }
}
