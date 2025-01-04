package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class Functions {

    private static final Logger logger = LoggerFactory.getLogger(Functions.class);

    @Bean
    @Description("Get the list of books written by the given author available in the library")
    public Function<BookService.Author, List<BookService.Book>> booksByAuthor(BookService bookService) {
        return author -> {
            logger.info("Getting books by author: {}", author);
            return bookService.getBooksByAuthor(author);
        };
    }

    @Bean
    @Description("Get the list of authors who wrote the given books available in the library")
    public Function<List<String>, List<BookService.Author>> authorsByBooks(BookService bookService) {
        return books -> {
            logger.info("Getting authors by books: {}", String.join(", ", books));
            return bookService.getAuthorsByBook(books.stream().map(b -> new BookService.Book(b, "")).toList());
        };
    }

}
