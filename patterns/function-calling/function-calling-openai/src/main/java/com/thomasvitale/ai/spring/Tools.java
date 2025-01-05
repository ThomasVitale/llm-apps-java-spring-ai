package com.thomasvitale.ai.spring;

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

    public void welcome() {
        logger.info("Welcoming users to the library");
    }

    public void welcomeUser(String user) {
        logger.info("Welcoming {} to the library", user);
    }

    public List<BookService.Book> booksByAuthor(String author) {
        logger.info("Getting books by author: {}", author);
        return bookService.getBooksByAuthor(new BookService.Author(author));
    }

    public List<BookService.Author> authorsByBooks(List<String> books) {
        logger.info("Getting authors by books: {}", String.join(", ", books));
        return bookService.getAuthorsByBook(books.stream().map(b -> new BookService.Book(b, "")).toList());
    }

    List<BookService.Book> findBooksByAuthor(String author) {
        logger.info("Getting books by author: {}", author);
        return bookService.getBooksByAuthor(new BookService.Author(author));
    }

}
