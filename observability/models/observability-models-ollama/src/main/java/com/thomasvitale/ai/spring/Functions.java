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
    @Description("Get the list of available books written by the given author")
    public Function<BookService.Author, List<BookService.Book>> booksByAuthor(BookService bookService) {
        logger.info("Calling function to get books by author");
        return bookService::getBooksByAuthor;
    }

    @Bean
    @Description("Get the bestseller book written by the given author")
    public Function<BookService.Author, BookService.Book> bestsellerBookByAuthor(BookService bookService) {
        logger.info("Calling function to get bestseller book by author");
        return bookService::getBestsellerByAuthor;
    }

}
