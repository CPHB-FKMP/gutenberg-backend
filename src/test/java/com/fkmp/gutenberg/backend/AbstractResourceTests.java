package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.api.model.City;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractResourceTests {

    protected final Client client;

    @LocalServerPort
    protected int port;

    @Inject
    public AbstractResourceTests() {
        ClientConfig config = new ClientConfig();
        config.register(MultiPartFeature.class);
        client = ClientBuilder.newClient(config);
    }

    protected String getUri(String path) {
        return UriBuilder.fromPath(path).port(port).host("localhost").scheme("http").build().toString();
    }

    protected Invocation.Builder getRequest(String path, MultivaluedMap<String, String> queryParams) {
        WebTarget t = client.target(getUri(path));
        if (queryParams != null) {
            for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                t = t.queryParam(entry.getKey(), entry.getValue().toArray());
            }
        }
        return t.request();
    }

    protected void getBooksOnCityTest(String path) {
        // Expected book list
        ArrayList<BookDto> expectedBooks = new ArrayList<>();
        BookDto bookDto1 = new BookDto();
        bookDto1.setTitle("My Lady of the Chinese Courtyard");
        bookDto1.setId("19665");

        BookDto bookDto2 = new BookDto();
        bookDto2.setTitle("The Return of Peter Grimm Novelised From the Play");
        bookDto2.setId("24359");

        BookDto bookDto3 = new BookDto();
        bookDto3.setTitle("Morals and the Evolution of Man");
        bookDto3.setId("37998");

        BookDto bookDto4 = new BookDto();
        bookDto4.setTitle("History of the Reformation in the Sixteenth Century, Vol 2");
        bookDto4.setId("41470");

        expectedBooks.add(bookDto1);
        expectedBooks.add(bookDto2);
        expectedBooks.add(bookDto3);
        expectedBooks.add(bookDto4);

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("city", "London");
        Response response = getRequest(path, params).get();

        Assert.assertEquals(200, response.getStatus());
        // Collect the result from the response
        ArrayList<BookDto> result = response.readEntity(BookList.class);

        Assert.assertTrue(!result.get(0).getAuthors().isEmpty());

        testArray(expectedBooks, result);
    }

    protected void getBooksOnTitleTest(String path) {
        // Expected book list
        ArrayList<BookDto> expectedBooks = new ArrayList<>();
        BookDto bookDto = new BookDto();
        bookDto.setId("21271");
        bookDto.setTitle("Unknown title");

        ArrayList<City> cities = new ArrayList<>();
        City city1 = new City();
        city1.setName("Young");
        city1.setLatitude("-32.698440");
        city1.setLongitude("-57.626930");

        City city2 = new City();
        city2.setName("Roses");
        city2.setLatitude("42.261990");
        city2.setLongitude("3.176890");

        expectedBooks.add(bookDto);

        // Create Parameter for the query and sends a request
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("title", "Unknown title");
        Response response = getRequest(path, params).get();

        Assert.assertEquals(200, response.getStatus());

        // Collect the result from the response
        ArrayList<BookDto> result = response.readEntity(BookList.class);

        testArray(expectedBooks, result);
    }

    protected <T> void testArray(ArrayList<T> expected, ArrayList<T> actual) {
        Assertions.assertAll("Books", () -> {
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(expected.size(), actual.size());
            assertThat(actual, containsInAnyOrder(expected.toArray()));
        });
    }

    public static final class BookList extends ArrayList<BookDto> {
        private static final long serialVersionUID = 1L;
    }
}