package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.context.ActiveProfiles;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.*;

@RunWith(Parameterized.class)
@ActiveProfiles(profiles = "performance", inheritProfiles = false)
public class PerformanceGeoTest extends AbstractResourceTests {

    String path;
    int port;
    List<Double> list;
    int times;
    String lat;
    String lng;

    public PerformanceGeoTest(String path, int port, List<Double> list, int times, String lat, String lng) {
        this.path = path;
        this.port = port;
        this.list = list;
        this.times = times;
        this.lat = lat;
        this.lng = lng;
    }

    @Parameterized.Parameters(name = "{index}: testEquals({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/postgres/book", 8081, new ArrayList<>(), 2, "34.366500", "-89.519250"},
                {"/postgres/book", 8081, new ArrayList<>(), 2, "42.440630", "-76.496610"},
                {"/postgres/book", 8081, new ArrayList<>(), 2, "34.802430", "-86.972190"},
                {"/postgres/book", 8081, new ArrayList<>(), 2, "43.983330", "25.333330"},
                {"/postgres/book", 8081, new ArrayList<>(), 2, "42.728410", "-73.691790"},
                {"/neo4j/book", 8081, new ArrayList<>(), 2, "34.366500", "-89.519250"},
                {"/neo4j/book", 8081, new ArrayList<>(), 2, "42.440630", "-76.496610"},
                {"/neo4j/book", 8081, new ArrayList<>(), 2, "34.802430", "-86.972190"},
                {"/neo4j/book", 8081, new ArrayList<>(), 2, "43.983330", "25.333330"},
                {"/neo4j/book", 8081, new ArrayList<>(), 2, "42.728410", "-73.691790"},
        });
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        System.out.println(lat + " : " + lng);
        System.out.println("Average : " + getAverage(list));
        System.out.println("Median : " + getMedian(list));

    }

    @Test
    @DisplayName("Geoloaction")
    public void getGeolocation() {
        for (int i = 0; i < times; i++) {
            MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
            params.putSingle("lat", lat);
            params.putSingle("long", lng);

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

