package com.fkmp.gutenberg.backend;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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

    protected void testArray(ArrayList<BookDto> expected, ArrayList<BookDto> actual) {
        Assertions.assertAll("Books", () -> {
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(expected.size(), actual.size());
            Assertions.assertTrue(actual.containsAll(expected));
        });
    }

    public static final class BookList extends ArrayList<BookDto> {
        private static final long serialVersionUID = 1L;
    }
}