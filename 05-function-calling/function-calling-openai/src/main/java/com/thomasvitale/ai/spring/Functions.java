package com.thomasvitale.ai.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class Functions {

    @Bean
    @Description("Get the list of available books written by the given author")
    public Function<BookService.Author, List<BookService.Book>> booksByAuthor(BookService bookService) {
        return bookService::getBooksByAuthor;
    }

}
