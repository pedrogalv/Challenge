package com.gamesys.challenge.usecase;

import com.gamesys.challenge.domain.Book;
import com.gamesys.challenge.gateway.BookGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class ListBooks {

    private final BookGateway bookGateway;

    @Autowired
    public ListBooks(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public String execute() {
        log.debug("Listing all books");

        List<Book> allBooks = bookGateway.findAllBooks();
        log.debug("Gateway returned {} books", allBooks.size());

        if (allBooks.size() == 0) {
            return "There are no books stored to list.";
        }

        return
                IntStream.range(0, allBooks.size())
                        .mapToObj(index -> "[" + (index + 1) + "]" + allBooks.get(index))
                        .collect(Collectors.joining("\n"));


    }
}
