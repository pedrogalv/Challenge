package com.gamesys.challenge.usecase;

import com.gamesys.challenge.domain.Book;
import com.gamesys.challenge.gateway.BookGateway;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.format.CurrencyStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class ObtainTotalPrice {

    private final BookGateway bookGateway;
    private final ApplyDiscount applyDiscount;

    @Value("${price.currency:GBP}")
    private String CURRENCY;

    @Autowired
    public ObtainTotalPrice(BookGateway bookGateway, ApplyDiscount applyDiscount) {
        this.bookGateway = bookGateway;
        this.applyDiscount = applyDiscount;
    }

    public String execute(List<Long> ids) {
        log.debug("Pricing {} ids ", ids.size());

        List<Book> books = bookGateway.findByIds(ids);

        BigDecimal totalValue = applyDiscount.execute(books);
        log.debug("Discounted price obtained {} ", totalValue.toString());

        MonetaryAmount value = Monetary.getDefaultAmountFactory()
                .setCurrency(CURRENCY)
                .setNumber(totalValue.setScale(2, RoundingMode.DOWN))
                .create();

        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder
                        .of(Locale.UK)
                        .set(CurrencyStyle.SYMBOL)
                        .build());

        log.debug("Returning value {} for currency {} ", value, CURRENCY);
        return customFormat.format(value);
    }

}
