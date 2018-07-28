package com.gamesys.challenge.usecase;

import com.gamesys.challenge.domain.Book;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class ApplyDiscountTest {

    @InjectMocks
    private ApplyDiscount usecase;

    private List<Book> storedBooks = new ArrayList<>();

    @Before
    public void setup() {
        storedBooks.clear();

        ReflectionTestUtils.setField(usecase, "POST_TWENTY_CENTURY_DISCOUNT", "0.1");
        ReflectionTestUtils.setField(usecase, "POST_PRICE_DISCOUNT", "0.05");
        ReflectionTestUtils.setField(usecase, "MIN_PRICE", "30");

    }

    @Test
    public void ShouldReturnZeroWhenNoBooksAreReturned() {

        //When the use case is called having no books in list
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price 0.00 is returned
        assertThat(result.toString(), Matchers.equalTo("0.00"));

    }

    @Test
    public void ShouldApplySpecificDiscountToNewBooks() {
        //Given the existing new book
        Book book = Book.builder().title("Book 1").year(2000).price(BigDecimal.valueOf(10)).build();
        storedBooks.add(book);

        //When the use case is called
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price returned has a discount of 10%
        assertThat(result.toString(), Matchers.equalTo("9.00"));

    }

    @Test
    public void ShouldNotApplySpecificDiscountToOldBooks() {
        //Given the existing new book
        Book book = Book.builder().title("Book 1").year(1999).price(BigDecimal.valueOf(10)).build();
        storedBooks.add(book);

        //When the use case is called
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price returned should not have a discount of 10%
        assertThat(result.toString(), Matchers.equalTo("10.00"));
    }

    @Test
    public void ShouldNotApplySpecificDiscountToNewBooksHavingValueZero() {
        //Given the existing new book having price 0
        Book book = Book.builder().title("Book 1").year(2001).price(BigDecimal.valueOf(0)).build();
        storedBooks.add(book);

        //When the use case is called
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price returned should not have a discount of 10%
        assertThat(result.toString(), Matchers.equalTo("0.00"));
    }

    @Test
    public void ShouldApplyDiscountToTotalValueBiggerThanMinValue() {
        //Given the existing old books
        Book book1 = Book.builder().title("Book 1").year(1991).price(BigDecimal.valueOf(16)).build();
        Book book2 = Book.builder().title("Book 2").year(1990).price(BigDecimal.valueOf(15)).build();
        storedBooks.addAll(Arrays.asList(book1, book2));

        //When the use case is called
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price returned should have a discount of 5%
        assertThat(result.toString(), Matchers.equalTo("29.45"));
    }

    @Test
    public void ShouldNotApplyDiscountToTotalValueEqualsThanMinValue() {
        //Given the existing old books
        Book book1 = Book.builder().title("Book 1").year(1991).price(BigDecimal.valueOf(15)).build();
        Book book2 = Book.builder().title("Book 2").year(1990).price(BigDecimal.valueOf(15)).build();
        storedBooks.addAll(Arrays.asList(book1, book2));

        //When the use case is called
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price returned should not have a discount of 5%
        assertThat(result.toString(), Matchers.equalTo("30.00"));
    }

    @Test
    public void ShouldNotApplyDiscountToTotalValueLowerThanMinValue() {
        //Given the existing old books
        Book book1 = Book.builder().title("Book 1").year(1991).price(BigDecimal.valueOf(14)).build();
        Book book2 = Book.builder().title("Book 2").year(1990).price(BigDecimal.valueOf(15)).build();
        storedBooks.addAll(Arrays.asList(book1, book2));

        //When the use case is called
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price returned should not have a discount of 5%
        assertThat(result.toString(), Matchers.equalTo("29.00"));
    }

    @Test
    public void ShouldApplyBothDiscounts() {
        //Given the existing old books
        Book book1 = Book.builder().title("Book 1").year(2010).price(BigDecimal.valueOf(13.14)).build();
        Book book2 = Book.builder().title("Book 2").year(1889).price(BigDecimal.valueOf(12.87)).build();
        Book book3 = Book.builder().title("Book 2").year(1861).price(BigDecimal.valueOf(13.21)).build();
        storedBooks.addAll(Arrays.asList(book1, book2, book3));

        //When the use case is called
        BigDecimal result = usecase.execute(storedBooks);

        //Then the price returned should have a discount for the new book and for the total value bigger than 30
        assertThat(result.toString(), Matchers.equalTo("36.01"));
    }


}