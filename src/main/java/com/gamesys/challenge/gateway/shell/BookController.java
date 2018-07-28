package com.gamesys.challenge.gateway.shell;

import com.gamesys.challenge.usecase.ListBooks;
import com.gamesys.challenge.usecase.ObtainTotalPrice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ShellComponent
public class BookController {

    private final ListBooks listBooks;
    private final ObtainTotalPrice obtainTotalPrice;

    @Autowired
    public BookController(ListBooks listBooks, ObtainTotalPrice obtainTotalPrice) {
        this.listBooks = listBooks;
        this.obtainTotalPrice = obtainTotalPrice;
    }

    @ShellMethod(value = "List all books stored.", key = "list")
    public String listAllBooks() {
        log.debug("Received call for listing all books stored");
        return listBooks.execute();

    }

    @ShellMethod(value = "Check price of book ids.", key = "price")
    public String price(
            @ShellOption(help = "List of all books ids desired to check price. Example: 2,5,11") String[] list
    ) {

        List<Long> ids = Arrays
                .stream(list)
                .filter(StringUtils::isNumeric)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        log.debug("Received call for pricing of the following books: {}", ids);

        return obtainTotalPrice.execute(ids);
    }
}
