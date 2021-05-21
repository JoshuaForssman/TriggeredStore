package com.triggered.store.service.impl;

import com.triggered.store.entity.Product;
import com.triggered.store.service.PricingRule;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PricingRuleAmountForDiscount implements PricingRule {

    int itemsToBuy;
    BigDecimal discountPercentage;
    String productCode;
    BigDecimal productCost;
    BigDecimal discountAmount = BigDecimal.ZERO;

    public PricingRuleAmountForDiscount(int itemsToBuy, BigDecimal discountPercentage, String productCode) {
        this.itemsToBuy = itemsToBuy;
        this.discountPercentage = discountPercentage;
        this.productCode = productCode;
    }

    @Override
    public BigDecimal ruleCalulation(List<Product> checkoutItems) {

//        this.productCost = checkoutItems.stream().
//                filter(checkoutItem -> checkoutItem.getProductCode().equals(this.productCode)).
//                findFirst().get().getProductPrice();

        //retrieves count of specific productCode (item) that has discount from the checkout items
        int ruleItemCount = (int) checkoutItems.stream()
                .filter(checkoutItem -> checkoutItem.getProductCode().equals(this.productCode))
                .count();


        if(ruleItemCount >= this.itemsToBuy){

            BigDecimal discountItemsTotalCost = BigDecimal.valueOf(checkoutItems.stream().
                    filter(checkoutItem -> checkoutItem.getProductCode().equals(this.productCode))
                    .mapToDouble(product ->product.getProductPrice().doubleValue())
                    .sum());

            this.discountAmount = discountItemsTotalCost.multiply
                    (discountPercentage.divide
                            (BigDecimal.valueOf(100)));

        }

        return this.discountAmount;
    }
}
