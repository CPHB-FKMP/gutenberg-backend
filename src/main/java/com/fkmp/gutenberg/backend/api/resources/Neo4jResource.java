package com.fkmp.gutenberg.backend.api.resources;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.domain.BookService;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/neo4j")
@Produces(MediaType.APPLICATION_JSON)
public class Neo4jResource {

    @Inject @Named("neo4j.book.service")
    BookService neo4jBookService;

    @GetMapping("/book")
    public List<BookDto> getBooksBy(@RequestParam Map<String, String> requestParams) {

        if (requestParams.isEmpty()) {
            throw new NotFoundException("You need to provide at least one parameter for this request");
        }

        return neo4jBookService.getBook(requestParams);

    }
}
