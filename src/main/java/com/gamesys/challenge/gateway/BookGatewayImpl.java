package com.gamesys.challenge.gateway;

import com.gamesys.challenge.domain.Book;
import com.gamesys.challenge.gateway.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookGatewayImpl implements BookGateway {

    private final BooksRepository repository;

    @Autowired
    public BookGatewayImpl(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> findAllBooks() {
        return repository.findAll();
    }

    @Override
    public List<Book> findByIds(List<Long> ids) {
        return repository.findAllByIds(ids);
    }
}
