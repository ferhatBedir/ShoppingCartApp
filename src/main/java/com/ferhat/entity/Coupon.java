package com.ferhat.entity;

public class Coupon {

    private Double minPurchaseAmount;
    private Double rateOrAmunt;
    private DiscountType discountType;

    public Coupon(Double minPurchaseAmount, Double rateOrAmunt, DiscountType discountType) {
        this.minPurchaseAmount = minPurchaseAmount;
        this.rateOrAmunt = rateOrAmunt;
        this.discountType = discountType;
    }

    public Double getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(Double minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Double getRateOrAmunt() {
        return rateOrAmunt;
    }

    public void setRateOrAmunt(Double rateOrAmunt) {
        this.rateOrAmunt = rateOrAmunt;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
