package com.gamesys.challenge.usecase;

import com.gamesys.challenge.domain.Book;
import com.gamesys.challenge.gateway.BookGateway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class ListBooksTest {

    @Mock
    private BookGateway gateway;

    @InjectMocks
    private ListBooks usecase;

    private Book book1 = Book.builder().title("Book 1").year(1).price(BigDecimal.valueOf(11)).build();
    private Book book2 = Book.builder().title("Book 2").year(2).price(BigDecimal.valueOf(22)).build();
    private Book book3 = Book.builder().title("Book 3").year(3).price(BigDecimal.valueOf(33)).build();

    private List<Book> storedBooks = new ArrayList<>();


    @Before
    public void setup() {
        storedBooks.add(book1);
        storedBooks.add(book2);
        storedBooks.add(book3);

        Mockito.when(gateway.findAllBooks()).thenReturn(storedBooks);
    }

    @Test
    public void shouldHandleGatewayReturn() {
        //When a call is made for list books
        String listing = usecase.execute();

        //Then a filled string is returned
        assertThat(listing, Matchers.not(Matchers.isEmptyOrNullString()));
    }

    @Test
    public void shouldReturnCustomMessageIfNoBookAreReturned() {
        //Given a empty book list return from gateway
        Mockito.when(gateway.findAllBooks()).thenReturn(new ArrayList<>());

        //When a call is made for list books
        String listing = usecase.execute();

        //Then an empty string is returned
        assertThat(listing, Matchers.equalTo("There are no books stored to list."));
    }

    @Test
    public void everyBookShouldHaveAnIndex() {
        //When a call is made for list books
        String listing = usecase.execute();

        //Then every book is returned and have a index
        assertThat(listing, Matchers.containsString("[1](title='Book 1', year=1, price=11)"));
        assertThat(listing, Matchers.containsString("[2](title='Book 2', year=2, price=22)"));
        assertThat(listing, Matchers.containsString("[3](title='Book 3', year=3, price=33)"));

    }

}