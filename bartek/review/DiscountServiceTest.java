package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountServiceTest {

    @Test
    public void shouldApplyDiscountCorrectly() {
        DiscountService discountService = new DiscountService();
        double result = discountService.calculateDiscount(100, 0.1);
        assertEquals(90, result, 0.01);
    }

    @Test
    public void shouldReturnSamePriceForZeroDiscount() {
        DiscountService discountService = new DiscountService();
        double result = discountService.calculateDiscount(100, 0);
        assertEquals(100, result, 0.01);
    }
}