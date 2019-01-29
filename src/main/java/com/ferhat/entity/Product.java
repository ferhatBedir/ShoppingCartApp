package com.ferhat.entity;

public class Product {

    private String productName;
    private Double productPrice;
    private Category category;

    public Product(String productName, Double productPrice, Category category) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
