package com.fkmp.gutenberg.backend.api.resources;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/neo4j")
@Produces(MediaType.APPLICATION_JSON)
public class Neo4jResource {

    @GetMapping("/book")
    public List<BookDto> getBooksBy(@RequestParam Map<String, String> requestParams) {

        if (requestParams.isEmpty()) {
            throw new NotFoundException("You need to provide at least one parameter for this request");
        }

        if (requestParams.get("title") != null) {
            return new ArrayList<>();
        }

        if (requestParams.get("city") != null) {
            return new ArrayList<>();
        }

        if (requestParams.get("author") != null) {
            return new ArrayList<>();
        }

        if (requestParams.get("lat") != null && requestParams.get("long") != null) {
            return new ArrayList<>();
        }

        throw new NotFoundException("You need to provide a correct parameter for this request");
    }
}
