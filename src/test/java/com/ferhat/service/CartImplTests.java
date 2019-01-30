package com.ferhat.service;

import com.ferhat.entity.*;
import com.ferhat.exception.CartException;
import com.ferhat.service.impl.CartImpl;
import com.ferhat.service.impl.DeliveryCostCalculatorImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CartImplTests {

    @Mock
    private DeliveryCostCalculator deliveryCostCalculator;


    @Test(expected = CartException.class)
    public void addItemToCart_whenProductBeNullShouldThrowException() throws CartException {
        CartImpl cart = new CartImpl(deliveryCostCalculator);
        Product p1 = null;
        cart.addItemToCart(p1, 1);
    }

    @Test(expected = CartException.class)
    public void addItemToCart_whenQuantityBeZeroShouldThrowException() throws CartException {
        CartImpl cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category1");
        Product p1 = new Product("P1", 1.00, category);
        cart.addItemToCart(p1, 0);
    }

    @Test
    public void addItemToCart_whenCorrectBeProductShouldAddToList() throws CartException {
        CartImpl cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Product p1 = new Product("P1", 2.00, category);
        cart.addItemToCart(p1, 3);
        Map<Product, Integer> cartList = cart.getCartItems();
        int actualProductCount = cartList.size();
        int expectedProductCount = 1;
        int expectedProductQuantity = 3;

        Assert.assertEquals(expectedProductCount, actualProductCount);
        Assert.assertEquals(expectedProductQuantity, cartList.get(p1), 0);
    }

    @Test
    public void applyDiscount_whenCreateCampaignShouldAddToAppliedCampaign() {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Campaign campaign1 = new Campaign(category, 10.00, 10, DiscountType.RATE);
        Campaign campaign2 = new Campaign(category, 15.00, 15, DiscountType.AMOUNT);

        cart.applyDiscount(campaign1, campaign2);
    }

    @Test
    public void applyCoupon_whenCreateCouponShouldAddToAppliedCoupon() {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Coupon coupon = new Coupon(100.00, 10.00, DiscountType.AMOUNT);

        cart.applyCoupon(coupon);
    }

    @Test
    public void getCampaignDiscount_whenCampaignListNullShouldReturnZero() {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Campaign campaign = null;
        cart.applyDiscount(campaign);
        double actualDiscountAmount = cart.getCampaignDiscount();
        double expectedDiscountAmount = 0;

        Assert.assertEquals(expectedDiscountAmount, actualDiscountAmount, 0.00);
    }

    @Test
    public void getCampaignDiscount_whenCampaignListNotNullShouldApplyDiscount() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category1 = new Category("Category1");
        Campaign campaign = new Campaign(category1, 10.00, 2, DiscountType.AMOUNT);
        cart.applyDiscount(campaign);

        Product p1 = new Product("P1", 10.00, category1);
        cart.addItemToCart(p1, 2);
        double actualDiscountAmount = cart.getCampaignDiscount();
        double expectedDiscountAmount = 10.00;

        Assert.assertEquals(expectedDiscountAmount, actualDiscountAmount, 0.00);
    }

    @Test
    public void getCampaignDiscount_whenCampaignTypeIsAmountShouldApplyDiscount() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category1 = new Category("Category1");
        Campaign campaign = new Campaign(category1, 5.00, 1, DiscountType.AMOUNT);
        cart.applyDiscount(campaign);

        Product p1 = new Product("P1", 10.00, category1);
        cart.addItemToCart(p1, 1);
        double actualDiscountAmount = cart.getCampaignDiscount();
        double expectedDiscountAmount = 5.00;

        Assert.assertEquals(expectedDiscountAmount, actualDiscountAmount, 0.00);
    }

    @Test
    public void getCampaignDiscount_whenCampaignTypeIsRateShouldApplyDiscount() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category1 = new Category("Category1");
        Campaign campaign = new Campaign(category1, 10.00, 1, DiscountType.RATE);
        cart.applyDiscount(campaign);

        Product p1 = new Product("P1", 10.00, category1);
        cart.addItemToCart(p1, 1);
        double actualDiscountAmount = cart.getCampaignDiscount();
        double expectedDiscountAmount = 1.00;

        Assert.assertEquals(expectedDiscountAmount, actualDiscountAmount, 0.00);
    }

    @Test
    public void getCampaignDiscount_whenFindBestCampaignShouldApplyDiscount() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category1 = new Category("Category1");
        Campaign campaign1 = new Campaign(category1, 10.00, 1, DiscountType.RATE);
        Campaign campaign2 = new Campaign(category1, 20.00, 2, DiscountType.RATE);
        cart.applyDiscount(campaign1, campaign2);

        Product p1 = new Product("P1", 10.00, category1);
        cart.addItemToCart(p1, 2);
        double actualDiscountAmount = cart.getCampaignDiscount();
        double expectedDiscountAmount = 4.00;
        Assert.assertEquals(expectedDiscountAmount, actualDiscountAmount, 0.00);
    }

    @Test
    public void getCouponDiscount_whenCouponIsNullShouldReturnZero() {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Coupon coupon = null;
        cart.applyCoupon(coupon);
        double actualCouponAmount = cart.getCouponDiscount();
        double expectedCouponAmount = 0;

        Assert.assertEquals(expectedCouponAmount, actualCouponAmount, 0.00);
    }

    @Test
    public void getCouponDiscount_whenCouponIsNotNullShouldApplyCoupon() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Coupon coupon = new Coupon(10.00, 2.00, DiscountType.AMOUNT);
        cart.applyCoupon(coupon);

        Product p1 = new Product("P1", 10.00, category);
        cart.addItemToCart(p1, 2);
        double actualCouponAmount = cart.getCouponDiscount();
        double expectedCouponDiscount = 2.00;

        Assert.assertEquals(expectedCouponDiscount, actualCouponAmount, 0.00);
    }

    @Test
    public void getCouponDiscount_whenCouponTypeIsAmountShouldApplyCoupon() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Coupon coupon = new Coupon(10.00, 5.00, DiscountType.AMOUNT);
        cart.applyCoupon(coupon);

        Product p1 = new Product("P1", 10.00, category);
        cart.addItemToCart(p1, 1);
        double actualCouponAmount = cart.getCouponDiscount();
        double expectedCouponDiscount = 5.00;

        Assert.assertEquals(expectedCouponDiscount, actualCouponAmount, 0.00);
    }

    @Test
    public void getCouponDiscount_whenCouponTypeIsRateShouldApplyCoupon() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Coupon coupon = new Coupon(10.00, 50.00, DiscountType.RATE);
        cart.applyCoupon(coupon);

        Product p1 = new Product("P1", 10.00, category);
        cart.addItemToCart(p1, 2);
        double actualCouponAmount = cart.getCouponDiscount();
        double expectedCouponDiscount = 10.00;

        Assert.assertEquals(expectedCouponDiscount, actualCouponAmount, 0.00);
    }

    @Test
    public void getCouponDiscount_whenCouponAmountBigFromCartAmountAfterCampaignShouldReturnZero() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Coupon coupon = new Coupon(10.00, 50.00, DiscountType.AMOUNT);
        cart.applyCoupon(coupon);

        Product p1 = new Product("P1", 10.00, category);
        cart.addItemToCart(p1, 2);
        double actualCouponAmount = cart.getCouponDiscount();
        double expectedCouponDiscount = 0;

        Assert.assertEquals(expectedCouponDiscount, actualCouponAmount, 0.00);
    }

    @Test
    public void getDeliveryCost_whenDeliveryCostCallShouldCalculateCost() throws CartException {
        deliveryCostCalculator = new DeliveryCostCalculatorImpl(1.00, 1.00, 2.99);
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category1");

        Product p1 = new Product("P1", 10.0, category);
        cart.addItemToCart(p1, 2);
        double actualDeliveryCostAmount = cart.getDeliveryCost();
        double expectedDeliveryCostAMount = 4.99;

        Assert.assertEquals(expectedDeliveryCostAMount, actualDeliveryCostAmount, 0.00);
    }

    @Test
    public void getCartTotalAmountAfterDiscount_whenCartItemListIsEmptyShouldReturnZero() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        double actualTotalCartAmount = cart.getCartTotalAmountAfterDiscount();
        double expectedTotalCartAmount = 0.00;

        Assert.assertEquals(expectedTotalCartAmount, actualTotalCartAmount, 0.00);
    }

    @Test
    public void getCartTotalAmountAfterDiscount_whenCartItemsIsNotEmptyShouldCalculateTotalAmount() throws CartException {
        deliveryCostCalculator = new DeliveryCostCalculatorImpl(1.00, 1.00, 2.99);
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category1 = new Category("Category1");
        Campaign campaign = new Campaign(category1, 10.00, 1, DiscountType.RATE);
        Coupon coupon = new Coupon(5.00, 1.00, DiscountType.AMOUNT);
        cart.applyDiscount(campaign);
        cart.applyCoupon(coupon);

        Product p1 = new Product("P1", 10.00, category1);
        cart.addItemToCart(p1, 1);
        double actualTotalCartAmountAfterDiscount = cart.getCartTotalAmountAfterDiscount();
        double expectedTotalcartAmountAfterDiscount = 12.99;

        Assert.assertEquals(expectedTotalcartAmountAfterDiscount, actualTotalCartAmountAfterDiscount, 0.00);
    }

    @Test
    public void getCartItems_whenCartItemsCallShouldReturnCartItemList() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Product product = new Product("Product", 10.00, category);
        cart.addItemToCart(product, 2);
        Map<Product, Integer> actualCArtItemList = cart.getCartItems();

        Assert.assertEquals(actualCArtItemList.containsKey(product), true);
    }

    @Test
    public void print_whenCartItemListBoughtThenShouldPrintCartInfo() throws CartException {
        Cart cart = new CartImpl(deliveryCostCalculator);
        Category category = new Category("Category");
        Product product = new Product("Product", 10.00, category);
        cart.addItemToCart(product, 1);
        cart.print();
    }
}
