package com.gamesys.challenge.gateway;

import com.gamesys.challenge.domain.Book;
import com.gamesys.challenge.gateway.repository.BooksRepository;
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
public class BookGatewayImplTest {

    @Mock
    BooksRepository repository;

    @InjectMocks
    BookGatewayImpl gateway;

    private Book book1 = Book.builder().title("Book 1").year(2000).price(BigDecimal.valueOf(23)).build();
    private Book book2 = Book.builder().title("Book 2").year(2001).price(BigDecimal.valueOf(24.31)).build();
    private Book book3 = Book.builder().title("Book 3").year(2002).price(BigDecimal.valueOf(25)).build();

    private List<Book> storedBooks = new ArrayList<>();

    @Before
    public void setup() {
        storedBooks.add(book1);
        storedBooks.add(book2);
        storedBooks.add(book3);
    }

    @Test
    public void shouldReturnAllBooksStored() {
        //Given a valid return from repository
        Mockito.when(repository.findAll()).thenReturn(storedBooks);

        //When a call is made for all books
        List<Book> allBooks = gateway.findAllBooks();

        //Then all books stored are returned accordingly
        assertThat(allBooks, Matchers.hasSize(3));

        assertThat(allBooks, Matchers.hasItem(book1));
        assertThat(allBooks, Matchers.hasItem(book2));
        assertThat(allBooks, Matchers.hasItem(book3));
    }

    @Test
    public void shouldFilterSuccessfullyAllIds() {
        //Given a list of ids [1,2,3]
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);

        //And a valid return from repository
        Mockito.when(repository.findAllByIds(ids)).thenReturn(storedBooks);

        //When a call is made for finding books 1, 2 and 3
        List<Book> allBooks = gateway.findByIds(ids);

        //Then just the correspondent books are returned
        assertThat(allBooks, Matchers.hasSize(3));

        assertThat(allBooks, Matchers.hasItem(book1));
        assertThat(allBooks, Matchers.hasItem(book2));
        assertThat(allBooks, Matchers.hasItem(book3));
    }
}