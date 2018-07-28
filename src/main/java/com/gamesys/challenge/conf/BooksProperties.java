package com.gamesys.challenge.conf;

import com.gamesys.challenge.domain.Book;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "books-properties")
@Configuration
@Component
public class BooksProperties {

    @Getter
    @Setter
    private List<Book> books;


}
