package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Tools {

    private static final Logger logger = LoggerFactory.getLogger(Tools.class);

    private final BookService bookService;

    public Tools(BookService bookService) {
        this.bookService = bookService;
    }

    @Tool(description = "Get the list of available books written by the given author")
    List<BookService.Book> booksByAuthor(String author) {
        logger.info("Calling function to get books by author");
        return bookService.getBooksByAuthor(author);
    }

    @Tool(description = "Get the bestseller book written by the given author")
    BookService.Book bestsellerBookByAuthor(String author) {
        logger.info("Calling function to get bestseller book by author");
        return bookService.getBestsellerByAuthor(author);
    }

}
