package com.gamesys.challenge.usecase;

import com.gamesys.challenge.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Component
public class ApplyDiscount {


    @Value("${price.discount.postTwentyCentury:0.1}")
    private String POST_TWENTY_CENTURY_DISCOUNT;

    @Value("${price.discount.postPrice:0.05}")
    private String POST_PRICE_DISCOUNT;

    @Value("${price.minPrice:30}")
    private String MIN_PRICE;

    public BigDecimal execute(List<Book> books) {
        log.debug("Applying discount over {} books", books.size());

        BigDecimal totalValue;

        BigDecimal newBooksValue = BigDecimal.ZERO;
        BigDecimal oldBooksValue = BigDecimal.ZERO;

        for (Book book : books) {
            if (book.getYear() >= 2000) {
                newBooksValue = newBooksValue.add(book.getPrice());
            } else {
                oldBooksValue = oldBooksValue.add(book.getPrice());
            }
        }
        log.debug("New books rough value: {}", newBooksValue.toString());
        log.debug("Old books rough value: {}", oldBooksValue.toString());

        newBooksValue = applyNewBooksDiscount(newBooksValue);
        log.debug("New books after discount value: {}", newBooksValue.toString());

        totalValue = newBooksValue.add(oldBooksValue);
        log.debug("Total rough value: {}", totalValue.toString());

        if (totalValue.compareTo(new BigDecimal(MIN_PRICE)) > 0) {
            totalValue = totalValue.multiply(BigDecimal.ONE.subtract(new BigDecimal(POST_PRICE_DISCOUNT)));
        }

        log.debug("Total value: {}", totalValue.toString());
        return totalValue.setScale(2, RoundingMode.DOWN);
    }

    private BigDecimal applyNewBooksDiscount(BigDecimal newBookValue) {
        if (newBookValue.compareTo(BigDecimal.ZERO) > 0) {
            return newBookValue.multiply(BigDecimal.ONE.subtract(new BigDecimal(POST_TWENTY_CENTURY_DISCOUNT)));
        }
        return newBookValue;
    }
}
