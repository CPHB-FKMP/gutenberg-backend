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
    int times = 10;
    String param;
    String value;

    public PerformanceIT(String path, int port, List<Double> list, String param, String value) {
        this.path = path;
        this.port = port;
        this.list = list;
        this.param = param;
        this.value = value;
    }

    @Parameterized.Parameters(name = "{index}: testEquals({0})")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"/postgres/book", 8081, new ArrayList<>(), "city", "Copenhagen"},
                {"/postgres/book", 8081, new ArrayList<>(), "city", "Greve"},
                {"/postgres/book", 8081, new ArrayList<>(), "city", "London"},
                {"/postgres/book", 8081, new ArrayList<>(),  "city", "Milton"},
                {"/postgres/book", 8081, new ArrayList<>(),  "city", "Venice"},
                {"/postgres/book", 8081, new ArrayList<>(), "title", "War in the Garden of Eden"},
                {"/postgres/book", 8081, new ArrayList<>(), "title", "La Boheme"},
                {"/postgres/book", 8081, new ArrayList<>(), "title", "Social Value A Study in Economic Theory Critical and Constructive"},
                {"/postgres/book", 8081, new ArrayList<>(), "title", "North America"},
                {"/postgres/book", 8081, new ArrayList<>(), "title", "Hesiod, The Homeric Hymns, and Homerica"},
                {"/postgres/book", 8081, new ArrayList<>(), "author", "Georgiana Cavendish"},
                {"/postgres/book", 8081, new ArrayList<>(), "author", "Lilian Garis"},
                {"/postgres/book", 8081, new ArrayList<>(), "author", "Maxime Provost"},
                {"/postgres/book", 8081, new ArrayList<>(), "author", "James Cook"},
                {"/postgres/book", 8081, new ArrayList<>(), "author", "Kermit Roosevelt"},
                {"/neo4j/book", 8081, new ArrayList<>(), "city", "Copenhagen"},
                {"/neo4j/book", 8081, new ArrayList<>(), "city", "Greve"},
                {"/neo4j/book", 8081, new ArrayList<>(), "city", "London"},
                {"/neo4j/book", 8081, new ArrayList<>(), "city", "Milton"},
                {"/neo4j/book", 8081, new ArrayList<>(), "city", "Venice"},
                {"/neo4j/book", 8081, new ArrayList<>(), "title", "War in the Garden of Eden"},
                {"/neo4j/book", 8081, new ArrayList<>(), "title", "La Boheme"},
                {"/neo4j/book", 8081, new ArrayList<>(), "title", "Social Value A Study in Economic Theory Critical and Constructive"},
                {"/neo4j/book", 8081, new ArrayList<>(), "title", "North America"},
                {"/neo4j/book", 8081, new ArrayList<>(), "title", "Hesiod, The Homeric Hymns, and Homerica"},
                {"/neo4j/book", 8081, new ArrayList<>(), "author", "Georgiana Cavendish"},
                {"/neo4j/book", 8081, new ArrayList<>(), "author", "Lilian Garis"},
                {"/neo4j/book", 8081, new ArrayList<>(), "author", "Maxime Provost"},
                {"/neo4j/book", 8081, new ArrayList<>(), "author", "James Cook"},
                {"/neo4j/book", 8081, new ArrayList<>(), "author", "Kermit Roosevelt"}
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
    public void getData(){
        for (int i = 0; i < times; i++) {
            MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
            params.putSingle(param, value);

            long startTime = System.nanoTime();
            Response response = getRequest(path, params, port).get();
            long endTime = System.nanoTime() - startTime;
            double totalTime = ((double) endTime / 1000000.0);
            list.add(totalTime);

            ArrayList<BookDto> result = response.readEntity(BookList.class);
            Assertions.assertAll("Must be done in order to know there where data", () -> {
                Assertions.assertEquals(200, response.getStatus());
                Assertions.assertTrue(!result.isEmpty());
            });
        }
    }
}
