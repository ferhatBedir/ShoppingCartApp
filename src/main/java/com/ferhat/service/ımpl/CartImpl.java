package com.ferhat.service.ımpl;

import com.ferhat.entity.*;
import com.ferhat.exception.CartException;
import com.ferhat.exception.ExceptionMessage;
import com.ferhat.service.Cart;
import com.ferhat.service.DeliveryCostCalculator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CartImpl implements Cart {

    private HashMap<Product, Integer> cartItems;
    private Campaign[] appliedCampaign;
    private Coupon appliedCoupon;
    private DeliveryCostCalculator deliveryCostCalculator;

    public CartImpl(DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
        cartItems = new HashMap<>();
    }

    public void addItemToCart(Product product, int quantity) throws CartException {
        if (product == null || quantity <= 0) {
            throw new CartException(ExceptionMessage.PRODUCT_OR_QUANTITY_INVALID);
        }

        if (cartItems.containsKey(product)) {
            int totalQuantity = cartItems.get(product) + quantity;
            cartItems.put(product, totalQuantity);
        } else {
            cartItems.put(product, quantity);
        }
    }

    public void applyDiscount(Campaign... campaigns) {
        appliedCampaign = campaigns;
    }

    public void applyCoupon(Coupon coupon) {
        appliedCoupon = coupon;
    }

    public double getCampaignDiscount() {
        if (appliedCampaign == null) {
            return 0;
        }
        double campaignDiscountAmount = 0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            campaignDiscountAmount += findBestCampaignForCartItem(entry.getKey(), entry.getValue(), appliedCampaign);
        }
        return campaignDiscountAmount;
    }

    public double getCouponDiscount() {
        if (appliedCoupon == null) {
            return 0;
        }
        double cartAmountAfterCampaigns = getCartAmountWithoutDiscount() - getCampaignDiscount();
        double couponDiscountAmount = calculateCouponDiscountAmount(cartAmountAfterCampaigns, appliedCoupon);

        if (couponDiscountAmount > cartAmountAfterCampaigns) {
            return 0;
        }
        return couponDiscountAmount;
    }

    public double getDeliveryCost() throws CartException {
        return deliveryCostCalculator.calculate(cartItems);
    }

    public double getCartTotalAmountAfterDiscount() throws CartException {
        if (cartItems.isEmpty()) {
            return 0;
        }
        double totalAmount = getCartAmountWithoutDiscount();
        totalAmount -= getCampaignDiscount();
        totalAmount -= getCouponDiscount();
        totalAmount += getDeliveryCost();
        return totalAmount;
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public void print() throws CartException {
        String cartInfo = "####################CART INFO####################" + "\n" +
                "PRODUCTS " + "\n" +
                getCartItemsAsString() + "\n" +
                "Total Discount : " + getCartTotalDiscount() + "\n" +
                "Total Price : " + getCartTotalAmountAfterDiscount() + "\n" +
                "#################################################";
        System.out.println(cartInfo);
    }

    private String getCartItemsAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-------------------------------------------------").append("\n");

        Set<Category> categories = new HashSet<>();
        cartItems.keySet().forEach(product -> categories.add(product.getCategory()));

        for (Category category : categories) {
            cartItems.entrySet().stream().filter((entry) -> entry.getKey().getCategory().equals(category)).forEach(cartItem ->
                    stringBuilder.append("Cart Item : ").append(cartItem.getKey().getProductName())
                            .append(", Quantity : ").append(cartItem.getValue())
                            .append(" Category : ").append(cartItem.getKey().getCategory().getCategoryName())
                            .append("\n"));
        }

        stringBuilder.append("-------------------------------------------------").append("\n");
        return stringBuilder.toString();
    }

    private double getCartTotalDiscount() {
        return getCampaignDiscount() + getCouponDiscount();
    }

    private double findBestCampaignForCartItem(Product product, Integer quantity, Campaign[] appliedCampaign) {
        double maxDiscountAmount = 0;
        for (Campaign campaign : appliedCampaign) {
            if ((campaign.getCategory().equals(product.getCategory())) && campaign.getMinQuantity() <= quantity) {
                double discountAmount = calculateDiscount(product, quantity, campaign);
                if (discountAmount > maxDiscountAmount) {
                    maxDiscountAmount = discountAmount;
                }
            }
        }
        return maxDiscountAmount;
    }

    private double calculateDiscount(Product product, Integer quantity, Campaign campaign) {
        if (campaign.getDiscountType() == DiscountType.AMOUNT) {
            return campaign.getRateOrAmount();
        } else {
            return ((product.getProductPrice() * quantity) / 100) * campaign.getRateOrAmount();
        }
    }

    private double calculateCouponDiscountAmount(double cartAmountAfterCampaigns, Coupon coupon) {
        double couponDiscountAmount = 0;
        if (coupon.getDiscountType() == DiscountType.AMOUNT) {
            couponDiscountAmount = coupon.getRateOrAmunt();
        } else {
            couponDiscountAmount = ((cartAmountAfterCampaigns / 100) * coupon.getRateOrAmunt());
        }
        return couponDiscountAmount;
    }

    private double getCartAmountWithoutDiscount() {
        double cartAmount = 0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            cartAmount += entry.getKey().getProductPrice() * entry.getValue();
        }
        return cartAmount;
    }
}