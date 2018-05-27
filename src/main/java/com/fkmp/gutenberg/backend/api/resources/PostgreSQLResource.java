package com.fkmp.gutenberg.backend.api.resources;

import com.fkmp.gutenberg.backend.api.model.BookDto;
import com.fkmp.gutenberg.backend.domain.BookService;
import com.fkmp.gutenberg.backend.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/postgres")
@Produces(MediaType.APPLICATION_JSON)
public class PostgreSQLResource {

    @Inject @Named("postgres.book.service")
    BookService postgresBookService;

    @GetMapping("/book")
    public List<BookDto> getBooksBy(@RequestParam Map<String, String> requestParams) {

        if (requestParams.isEmpty()) {
            throw new NotFoundException("You need to provide at least one parameter for this request");
        }

        return postgresBookService.getBook(requestParams);
    }
}
