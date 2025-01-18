package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class Functions {

    public static final String AUTHORS_BY_BOOKS = "authorsByBooks";

    public static final String BOOKS_BY_AUTHOR = "booksByAuthor";

    public static final String WELCOME = "welcome";

    public static final String WELCOME_USER = "welcomeUser";

    private static final Logger logger = LoggerFactory.getLogger(Functions.class);

    private final BookService bookService = new BookService();

    @Bean(WELCOME)
    @Description("Welcome users to the library")
    Consumer<Void> welcome() {
        return (input) -> logger.info("Welcoming users to the library");
    }

    @Bean(WELCOME_USER)
    @Description("Welcome a specific user to the library")
    Consumer<User> welcomeUser() {
        return user -> logger.info("Welcoming {} to the library", user.name());
    }

    @Bean(BOOKS_BY_AUTHOR)
    @Description("Get the list of books written by the given author available in the library")
    Function<BookService.Author, List<BookService.Book>> booksByAuthor() {
        return author -> {
            logger.info("Getting books by author: {}", author.name());
            return bookService.getBooksByAuthor(author);
        };
    }

    @Bean(AUTHORS_BY_BOOKS)
    @Description("Get the list of authors who wrote the given books available in the library")
    Function<BookService.Books, List<BookService.Author>> authorsByBooks() {
        return books -> {
            logger.info("Getting authors by books: {}", books.books().stream().map(BookService.Book::title).toList());
            return bookService.getAuthorsByBook(books.books());
        };
    }

    public record User(String name) {}

}
