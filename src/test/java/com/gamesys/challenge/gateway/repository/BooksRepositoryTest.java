package com.gamesys.challenge.gateway.repository;

import com.gamesys.challenge.conf.BooksProperties;
import com.gamesys.challenge.domain.Book;
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
public class BooksRepositoryTest {

    @Mock
    BooksProperties booksProperties;

    @InjectMocks
    BooksRepository repository;

    private Book book1 = Book.builder().title("Book 1").year(2000).price(BigDecimal.valueOf(23)).build();
    private Book book2 = Book.builder().title("Book 2").year(2001).price(BigDecimal.valueOf(24.31)).build();
    private Book book3 = Book.builder().title("Book 3").year(2002).price(BigDecimal.valueOf(25)).build();

    private List<Book> storedBooks = new ArrayList<>();

    @Before
    public void setup() {
        storedBooks.add(book1);
        storedBooks.add(book2);
        storedBooks.add(book3);

        Mockito.when(booksProperties.getBooks()).thenReturn(storedBooks);
    }

    @Test
    public void findAllShouldReturnAll() {
        //Given a call for finding all books
        List<Book> all = repository.findAll();

        //Then all books stored are returned
        assertThat(all, Matchers.hasSize(3));

        assertThat(all, Matchers.hasItem(book1));
        assertThat(all, Matchers.hasItem(book2));
        assertThat(all, Matchers.hasItem(book3));
    }

    @Test
    public void findByIdShouldFilterOnlyIdsInList() {
        //Given list of ids [1,3]
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);

        //When a call is made for finding books having ids 1 and 3
        List<Book> all = repository.findAllByIds(ids);

        //Then books 1 and 3 are returned
        assertThat(all, Matchers.hasSize(2));

        assertThat(all, Matchers.hasItem(book1));
        assertThat(all, Matchers.hasItem(book3));
        assertThat(all, Matchers.not(Matchers.hasItem(book2)));

    }

    @Test
    public void findByIdShouldNotBreakWhenIdDoesNotExists() {
        //Given list of ids [1,5]
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(5L);

        //When a call is made for finding books having ids 1 and 5
        List<Book> all = repository.findAllByIds(ids);

        //Then books 1 and 3 are returned
        assertThat(all, Matchers.hasSize(1));

        assertThat(all, Matchers.hasItem(book1));
    }

    @Test
    public void findByIdShouldNotBreakWhenIdListIsEmpty() {
        //Given an empty list of ids []
        List<Long> ids = new ArrayList<>();

        //When a call is made for finding books having ids
        List<Book> all = repository.findAllByIds(ids);

        //Then no books are returned
        assertThat(all, Matchers.empty());
    }
}