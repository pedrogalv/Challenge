package com.gamesys.challenge.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {

    private String title;
    private Integer year;
    private BigDecimal price;

    @Override
    public String toString() {
        return "(" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", price=" + price +
                ')';
    }
}
