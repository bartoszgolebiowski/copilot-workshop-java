package src;

public class DiscountService {

    public double calculateDiscount(double price, double discountRate) {
        return price - (price * discountRate);
    }
}