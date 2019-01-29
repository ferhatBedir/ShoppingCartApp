package com.ferhat.entity;

public class Campaign {

    private Category category;
    private Double rateOrAmount;
    private Integer minQuantity;
    private DiscountType discountType;

    public Campaign(Category category, Double rateOrAmount, Integer minQuantity, DiscountType discountType) {
        this.category = category;
        this.rateOrAmount = rateOrAmount;
        this.minQuantity = minQuantity;
        this.discountType = discountType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getRateOrAmount() {
        return rateOrAmount;
    }

    public void setRateOrAmount(Double rateOrAmount) {
        this.rateOrAmount = rateOrAmount;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
