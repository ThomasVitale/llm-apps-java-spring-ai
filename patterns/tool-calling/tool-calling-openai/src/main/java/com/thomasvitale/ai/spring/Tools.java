package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Tools {

    private static final Logger logger = LoggerFactory.getLogger(Tools.class);

    private final BookService bookService = new BookService();

    @Tool(description = "Welcome users to the library")
    void welcome() {
        logger.info("Welcoming users to the library");
    }

    @Tool(description = "Welcome a specific user to the library")
    void welcomeUser(String user) {
        logger.info("Welcoming {} to the library", user);
    }

    @Tool(description = "Get the list of books written by the given author available in the library")
    List<BookService.Book> booksByAuthor(String author) {
        logger.info("Getting books by author: {}", author);
        return bookService.getBooksByAuthor(new BookService.Author(author));
    }

    @Tool(description = "Get the list of authors who wrote the given books available in the library")
    List<BookService.Author> authorsByBooks(List<String> books) {
        logger.info("Getting authors by books: {}", String.join(", ", books));
        return bookService.getAuthorsByBook(books.stream().map(b -> new BookService.Book(b, "")).toList());
    }

}
