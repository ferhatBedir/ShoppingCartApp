package com.ferhat.service;

import com.ferhat.entity.Product;
import com.ferhat.exception.CartException;

import java.util.Map;

public interface DeliveryCostCalculator {

    double calculate(Map<Product, Integer> cartItems) throws CartException;
}
