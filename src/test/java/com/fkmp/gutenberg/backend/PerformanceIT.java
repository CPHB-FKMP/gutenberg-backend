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
    String[] values;

    public PerformanceIT(String path, int port, List<Double> list, String param, String... values) {
        this.path = path;
        this.port = port;
        this.list = list;
        this.param = param;
        this.values = values;
    }

    @Parameterized.Parameters(name = "{index} - {0} : Parameter({3} == {4})")
    public static Collection<Object[]> data(){
        String[] cities = {"Copenhagen", "Greve", "London", "Milton", "Venice"};
        String[] titles = {"War in the Garden of Eden", "La Boheme",
                "Social Value A Study in Economic Theory Critical and Constructive",
                "North America", "Hesiod, The Homeric Hymns, and Homerica"};
        String[] authors = {"Georgiana Cavendish", "Lilian Garis", "Maxime Provost", "James Cook", "Kermit Roosevelt"};
        return Arrays.asList(new Object[][]{
                {"/postgres/book", 8081, new ArrayList<>(), "city", cities},
                {"/postgres/book", 8081, new ArrayList<>(), "title", titles},
                {"/postgres/book", 8081, new ArrayList<>(), "author", authors},
                {"/neo4j/book", 8081, new ArrayList<>(), "city", cities},
                {"/neo4j/book", 8081, new ArrayList<>(), "title", titles},
                {"/neo4j/book", 8081, new ArrayList<>(), "author", authors}
        });
    }

    @Before
    public void setUp() {
        System.out.println("---------------------" + path + "-------------------------------");
    }

    @After
    public void tearDown() {
        System.out.println(path);
        System.out.println(param);
        System.out.println("Average : " + getAverage(list));
        System.out.println("Median : " + getMedian(list));

    }

    @Test
    public void getData(){
        for(String value : values) {
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
}
