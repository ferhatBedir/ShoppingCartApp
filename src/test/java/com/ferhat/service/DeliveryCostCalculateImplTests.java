package com.ferhat.service;

import com.ferhat.entity.Category;
import com.ferhat.entity.Product;
import com.ferhat.exception.CartException;
import com.ferhat.service.impl.DeliveryCostCalculatorImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class DeliveryCostCalculateImplTests {

    DeliveryCostCalculator deliveryCostCalculator;


    @Test
    public void calculate_whenCalculateDeliveryCostShouldBeCorrect() throws CartException {
        deliveryCostCalculator = new DeliveryCostCalculatorImpl(1.00, 1.00, 2.99);
        Category category1 = new Category("category1");
        Category category2 = new Category("category1");

        Product product1 = new Product("P1", 2.00, category1);
        Product product2 = new Product("P2", 2.00, category2);

        HashMap<Product, Integer> cartList = new HashMap<>();
        cartList.put(product1, 1);
        cartList.put(product2, 1);

        double actualAmount = deliveryCostCalculator.calculate(cartList);
        double expectedAmount = 6.99;
        Assert.assertEquals(expectedAmount, actualAmount, 0.00);
    }

    @Test(expected = CartException.class)
    public void calculate_whenCartListBeNullThrowException() throws CartException {
        deliveryCostCalculator = new DeliveryCostCalculatorImpl(1.00, 1.00, 2.99);
        HashMap<Product, Integer> cartList = null;
        deliveryCostCalculator.calculate(cartList);
    }

    @Test(expected = CartException.class)
    public void calculate_whenCartListBeEmptyThrowException() throws CartException {
        deliveryCostCalculator = new DeliveryCostCalculatorImpl(1.00, 1.00, 2.99);
        HashMap<Product, Integer> cartList = new HashMap<>();
        deliveryCostCalculator.calculate(cartList);
    }
}
