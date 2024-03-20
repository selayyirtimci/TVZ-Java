package hr.java.production.model;

import java.math.BigDecimal;

public interface Edible {
    Integer calculateKilocalories();
    BigDecimal calculatePrice();
}
