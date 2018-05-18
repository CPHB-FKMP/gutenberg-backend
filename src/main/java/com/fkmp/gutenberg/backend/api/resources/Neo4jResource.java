package com.fkmp.gutenberg.backend.api.resources;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.domain.BookService;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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

    @Inject
    BookService bookService;

    @GetMapping("/book")
    public List<BookDto> getBooksBy(@RequestParam Map<String, String> requestParams) {

        if (requestParams.isEmpty()) {
            throw new NotFoundException("You need to provide at least one parameter for this request");
        }

        return bookService.getBook(requestParams, "neo4j");

    }
}
