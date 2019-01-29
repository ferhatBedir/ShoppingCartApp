package com.ferhat;

import com.ferhat.entity.*;
import com.ferhat.exception.CartException;
import com.ferhat.service.Cart;
import com.ferhat.service.DeliveryCostCalculator;
import com.ferhat.service.ımpl.CartImpl;
import com.ferhat.service.ımpl.DeliveryCostCalculatorImpl;

public class Main {
    public static void main(String[] args) throws CartException {

        Category category1 = new Category("Category1");
        Category category2 = new Category("Category2");
        Category category3 = new Category("Category3", category2);

        Product p1 = new Product("P1", 10.00, category1);
        Product p2 = new Product("P2", 20.00, category1);
        Product p3 = new Product("P3", 30.00, category2);
        Product p4 = new Product("P4", 40.00, category2);
        Product p5 = new Product("P5", 50.00, category3);
        Product p6 = new Product("P6", 60.00, category3);
        Product p7 = new Product("P7", 70.00, category1);
        Product p8 = new Product("P8", 80.00, category2);

        Coupon coupon = new Coupon(100.00, 20.00, DiscountType.AMOUNT);

        Campaign campaign1 = new Campaign(category1, 20.00, 3, DiscountType.RATE);
        Campaign campaign2 = new Campaign(category2, 20.00, 3, DiscountType.AMOUNT);
        Campaign campaign3 = new Campaign(category3, 10.00, 2, DiscountType.RATE);

        DeliveryCostCalculator deliveryCostCalculator =
                new DeliveryCostCalculatorImpl(2.00, 2.5, 2.99);

        Cart cart = new CartImpl(deliveryCostCalculator);

        cart.addItemToCart(p1, 2);
        cart.addItemToCart(p1, 2);
        cart.addItemToCart(p2, 3);
        cart.addItemToCart(p3, 5);
        cart.addItemToCart(p4, 7);
        cart.addItemToCart(p5, 8);
        cart.addItemToCart(p6, 1);
        cart.addItemToCart(p7, 4);
        cart.addItemToCart(p8, 9);

        cart.applyDiscount(campaign1, campaign2, campaign3);
        cart.applyCoupon(coupon);

        cart.print();
    }
}
