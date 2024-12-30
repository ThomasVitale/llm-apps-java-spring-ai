package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.api.tools.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Tools {

    private final BookService bookService;

    Tools(BookService bookService) {
        this.bookService = bookService;
    }

    @Tool("Get the list of books written by the given author available in the library")
    public List<BookService.Book> booksByAuthor(String author) {
        return bookService.getBooksByAuthor(new BookService.Author(author));
    }

}
