package hr.java.production.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
public class Fruit extends Item implements Edible {
    public static final Integer caloriesPerKilo = 100;
    private BigDecimal weightInKG;

    public Fruit(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weightInKG) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weightInKG = weightInKG;
    }

    public BigDecimal getWeightInKG() {
        return weightInKG;
    }

    public void setWeightInKG(BigDecimal weightInKG) {
        this.weightInKG = weightInKG;
    }
    @Override
    public Integer calculateKilocalories() {return weightInKG.multiply(BigDecimal.valueOf(caloriesPerKilo)).setScale(0, RoundingMode.HALF_UP).intValue();
    }

    @Override
    public BigDecimal calculatePrice() {
        return weightInKG.multiply(getDiscountedSellingPrice()).setScale(2, RoundingMode.HALF_UP);    }
}