package com.gamesys.challenge.usecase;

import com.gamesys.challenge.gateway.BookGateway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class ObtainTotalPriceTest {

    @Mock
    private BookGateway gateway;

    @Mock
    private ApplyDiscount applyDiscount;

    @InjectMocks
    private ObtainTotalPrice usecase;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(usecase, "CURRENCY", "GBP");
    }

    @Test
    public void shouldReturnPriceFormattedForOneDecimalPlace() {
        //Given the return of discount use case
        Mockito.when(applyDiscount.execute(Mockito.anyList())).thenReturn(BigDecimal.ONE);

        //When the use case is called
        String value = usecase.execute(Collections.singletonList(1L));

        //Then a formatted monetary value is returned
        assertThat(value, Matchers.equalTo("£1.00"));
    }

    @Test
    public void shouldReturnPriceFormattedForTwoDecimalPlaces() {
        //Given the return of discount use case
        Mockito.when(applyDiscount.execute(Mockito.anyList())).thenReturn(new BigDecimal(15.21));

        //When the use case is called
        String value = usecase.execute(Collections.singletonList(1L));

        //Then a formatted monetary value is returned
        assertThat(value, Matchers.equalTo("£15.21"));
    }

    @Test
    public void shouldReturnPriceFormattedForThreeDecimalPlaces() {
        //Given the return of discount use case
        Mockito.when(applyDiscount.execute(Mockito.anyList())).thenReturn(new BigDecimal(155.21));

        //When the use case is called
        String value = usecase.execute(Collections.singletonList(1L));

        //Then a formatted monetary value is returned
        assertThat(value, Matchers.equalTo("£155.21"));
    }

    @Test
    public void shouldReturnPriceFormattedForFourDecimalPlaces() {
        //Given the return of discount use case
        Mockito.when(applyDiscount.execute(Mockito.anyList())).thenReturn(new BigDecimal(1537.21));

        //When the use case is called
        String value = usecase.execute(Collections.singletonList(1L));

        //Then a formatted monetary value is returned
        assertThat(value, Matchers.equalTo("£1,537.21"));
    }

}