package com.fkmp.gutenberg.backend.domain;

import com.fkmp.gutenberg.backend.api.model.BookDto;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<BookDto> getBook(Map<String, String> params);
}
