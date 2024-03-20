package hr.java.production.model;

import java.math.RoundingMode;
import java.math.BigDecimal;

public class Item extends NamedEntity {
    protected Category category;
    protected BigDecimal width, height, length, productionCost, sellingPrice;
    private Discount discount;

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        if (discount != null) {
            this.discount = discount;
        } else {
            throw new IllegalArgumentException("Discount cannot be null");
        }
    }


    public BigDecimal getDiscountedSellingPrice() {
        BigDecimal discountAmount = discount.discountAmount();
        BigDecimal discountedAmount = discountAmount.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                .multiply(sellingPrice);

        return sellingPrice.subtract(discountedAmount);
    }

    public BigDecimal calculateVolume() {
        return width.multiply(height).multiply(length);
    }

    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount != null ? discount : new Discount(BigDecimal.ZERO);
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
