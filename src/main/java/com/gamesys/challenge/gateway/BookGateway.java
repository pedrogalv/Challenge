package com.gamesys.challenge.gateway;

import com.gamesys.challenge.domain.Book;

import java.util.List;

public interface BookGateway {

    List<Book> findAllBooks();

    List<Book> findByIds(List<Long> ids);
}
