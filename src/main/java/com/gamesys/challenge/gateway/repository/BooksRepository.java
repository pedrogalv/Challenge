package com.gamesys.challenge.gateway.repository;

import com.gamesys.challenge.conf.BooksProperties;
import com.gamesys.challenge.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BooksRepository {

    private final BooksProperties booksProperties;

    @Autowired
    public BooksRepository(BooksProperties booksProperties) {
        this.booksProperties = booksProperties;
    }

    public List<Book> findAll() {
        List<Book> books = booksProperties.getBooks();
        log.debug("Repository returning all books({}) from store.", books.size());
        return books;
    }

    public List<Book> findAllByIds(List<Long> ids) {
        List books = booksProperties.getBooks();
        List<Book> filteredBooks = new ArrayList<>();

        ids.forEach(index -> {
            if (index <= books.size()) {
                filteredBooks.add((Book) books.get(index.intValue() - 1));
            }
        });
        log.debug("Repository filtered ({}) of all books({}) given ({}) ids from store.",
                filteredBooks.size(), books.size(), ids.size());
        return filteredBooks;
    }

}
