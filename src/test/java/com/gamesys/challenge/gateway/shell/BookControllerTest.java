package com.gamesys.challenge.gateway.shell;

import com.gamesys.challenge.usecase.ListBooks;
import com.gamesys.challenge.usecase.ObtainTotalPrice;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class BookControllerTest {
    @Mock
    private ListBooks listBooks;

    @Mock
    private ObtainTotalPrice obtainTotalPrice;

    @InjectMocks
    private BookController bookController;

    @Captor
    private ArgumentCaptor<List<Long>> captor;

    @Before
    public void setup() {

    }

    @Test
    public void listAllBooksShouldCallOnlyListBooksController() {
        //When a call for listing is made
        bookController.listAllBooks();

        // Then only listBooks use case is called
        Mockito.verify(listBooks, Mockito.times(1)).execute();
        Mockito.verify(obtainTotalPrice, Mockito.times(0)).execute(Mockito.anyList());
    }

    @Test
    public void PriceBooksShouldCallOnlyListBooksController() {
        //When a call for listing is made
        bookController.price(new String[]{
                "1"
        });

        // Then only obtainTotalPrice use case is called
        Mockito.verify(listBooks, Mockito.times(0)).execute();
        Mockito.verify(obtainTotalPrice, Mockito.times(1)).execute(Mockito.anyList());
    }

    @Test
    public void PriceBooksShouldOnlyConvertNumbers() {
        //When a call for listing is made
        bookController.price(new String[]{
                "1",
                "A",
                "3B",
                "f7",
                "2",
                "115"
        });

        // Then only obtainTotalPrice use case is called
        Mockito.verify(listBooks, Mockito.times(0)).execute();
        Mockito.verify(obtainTotalPrice, Mockito.times(1)).execute(captor.capture());

        // And the id list should have only valid ids
        List<Long> ids = captor.getValue();
        assertThat(ids, Matchers.hasSize(3));
        assertThat(ids, Matchers.hasItem(1L));
        assertThat(ids, Matchers.hasItem(2L));
        assertThat(ids, Matchers.hasItem(115L));

    }
}