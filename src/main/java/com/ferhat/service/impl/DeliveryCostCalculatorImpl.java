package com.ferhat.service.impl;

import com.ferhat.entity.Category;
import com.ferhat.entity.Product;
import com.ferhat.exception.CartException;
import com.ferhat.exception.ExceptionMessage;
import com.ferhat.service.DeliveryCostCalculator;

import java.util.HashMap;
import java.util.Map;

public class DeliveryCostCalculatorImpl implements DeliveryCostCalculator {

    private Double costPerDelivery;
    private Double costPerProduct;
    private Double fixedCost;

    public DeliveryCostCalculatorImpl(Double costPerDelivery, Double costPerProduct, Double fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }


    public double calculate(Map<Product, Integer> cartItems) throws CartException {
        /**
         * Formul((teslimat maliyeti * teslimat sayısı)+(ürün maliyeti * ürün sayısı)+fixedCost)
         */
        if (cartItems == null || cartItems.isEmpty()) {
            throw new CartException(ExceptionMessage.CART_LIST_EMPTY_OR_NULL);
        }
        int differentCategoryCount = separateCategory(cartItems);
        return ((costPerDelivery * differentCategoryCount) + (costPerProduct * cartItems.size()) + fixedCost);
    }

    private int separateCategory(Map<Product, Integer> cartItems) {
        Map<Category, Integer> differentCategoryList = new HashMap<>();
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            differentCategoryList.put(entry.getKey().getCategory(), 1);
        }
        return differentCategoryList.size();
    }
}
