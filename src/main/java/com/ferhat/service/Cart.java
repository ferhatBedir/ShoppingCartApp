package com.ferhat.service;

import com.ferhat.entity.Campaign;
import com.ferhat.entity.Coupon;
import com.ferhat.entity.Product;
import com.ferhat.exception.CartException;

import java.util.Map;

public interface Cart {

    void addItemToCart(Product product, int quantity) throws CartException;

    void applyDiscount(Campaign... campaigns);

    void applyCoupon(Coupon coupon);

    double getCampaignDiscount();

    double getCouponDiscount();

    double getDeliveryCost() throws CartException;

    double getCartTotalAmountAfterDiscount() throws CartException;

    Map<Product, Integer> getCartItems();

    void print() throws CartException;
}
