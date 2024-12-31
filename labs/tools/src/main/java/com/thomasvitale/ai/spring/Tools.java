package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.api.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Tools {

    private static final Logger logger = LoggerFactory.getLogger(Tools.class);

    private final BookService bookService;

    Tools(BookService bookService) {
        this.bookService = bookService;
    }

    @Tool("Get the list of books written by the given author available in the library")
    public List<BookService.Book> booksByAuthor(BookService.Author author) {
        logger.info("Getting books by author: {}", author);
        return bookService.getBooksByAuthor(author);
    }

    @Tool("Get the list of books written by the given authors available in the library")
    public List<BookService.Book> booksByAuthors(List<String> authors) {
        logger.info("Getting books by authors: {}", String.join(", ", authors));
        return bookService.getBooksByAuthor(authors.stream().map(BookService.Author::new).toList());
    }

    @Tool("Welcome users to the library")
    public void welcome() {
        logger.info("Welcoming users to the library");
    }

}
