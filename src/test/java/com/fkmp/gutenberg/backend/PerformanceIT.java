package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.context.ActiveProfiles;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.*;

@RunWith(Parameterized.class)
@ActiveProfiles(profiles = "performance", inheritProfiles = false)
public class PerformanceIT extends AbstractResourceTests {

    String path;
    int port;
    List<Double> list;
    int times;
    String param;
    String value;

    public PerformanceIT(String path, int port, List<Double> list, int times, String param, String value) {
        this.path = path;
        this.port = port;
        this.list = list;
        this.times = times;
        this.param = param;
        this.value = value;
    }

    @Parameterized.Parameters(name = "{index}: testEquals({0})")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"/postgres/book", 8081, new ArrayList<>(), 5, "city", "Copenhagen"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "city", "Greve"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "city", "London"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "city", "Milton"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "city", "Venice"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "title", "War in the Garden of Eden"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "title", "La Boheme"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "title", "Social Value A Study in Economic Theory Critical and Constructive"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "title", "North America"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "title", "Hesiod, The Homeric Hymns, and Homerica"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "author", "Georgiana Cavendish"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "author", "Lilian Garis"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "author", "Maxime Provost"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "author", "James Cook"},
                {"/postgres/book", 8081, new ArrayList<>(), 5, "author", "Kermit Roosevelt"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "city", "Copenhagen"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "city", "Greve"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "city", "London"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "city", "Milton"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "city", "Venice"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "title", "War in the Garden of Eden"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "title", "La Boheme"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "title", "Social Value A Study in Economic Theory Critical and Constructive"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "title", "North America"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "title", "Hesiod, The Homeric Hymns, and Homerica"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "author", "Georgiana Cavendish"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "author", "Lilian Garis"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "author", "Maxime Provost"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "author", "James Cook"},
                {"/neo4j/book", 8081, new ArrayList<>(), 5, "author", "Kermit Roosevelt"}
        });
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        System.out.println(param + " : " + value);
        System.out.println("Average : " + getAverage(list));
        System.out.println("Median : " + getMedian(list));

    }

    @Test
    @DisplayName("")
    public void getData(){
        for (int i = 0; i < times; i++) {
            MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
            params.putSingle("city", "London");

            long startTime = System.nanoTime();
            Response response = getRequest(path, params, port).get();
            long endTime = System.nanoTime() - startTime;
            double totalTime = ((double) endTime / 1000000000.0);
            list.add(totalTime);
            System.out.println(totalTime);

            ArrayList<BookDto> result = response.readEntity(BookList.class);
            Assertions.assertAll("Must be done in order to know there where data", () -> {
                Assertions.assertEquals(200, response.getStatus());
                Assertions.assertTrue(!result.isEmpty());
            });
        }
    }
}
