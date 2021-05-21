package com.triggered.store.service.impl;

import com.triggered.store.dao.ProductRepository;
import com.triggered.store.entity.Product;
import com.triggered.store.service.PricingRule;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PricingRuleXForZ implements PricingRule {

    int itemsToBuy;
    int itemsFree;
    String productCode;
    BigDecimal productCost;
    BigDecimal discountAmount = BigDecimal.ZERO;

    public PricingRuleXForZ(int itemsToBuy, int itemsFree, String productCode) {
        this.itemsToBuy = itemsToBuy;
        this.itemsFree = itemsFree;
        this.productCode = productCode;
    }


    @Override
    public BigDecimal ruleCalulation(List<Product> checkoutItems) {

        //retrieves count of specific productCode (item) that has discount from the checkout items
        int ruleItemCount = (int) checkoutItems.stream()
                .filter(checkoutItem -> checkoutItem.getProductCode().equals(this.productCode))
                .count();

        if(ruleItemCount > 0) {
            //setting price for current product rule using code to get value
            this.productCost = checkoutItems.stream().
                    filter(checkoutItem -> checkoutItem.getProductCode().equals(this.productCode)).
                    findFirst().get().getProductPrice();


            //doing the calculation of discount amount
            while (ruleItemCount >= itemsToBuy) {
                this.discountAmount = this.discountAmount.add(this.productCost);
                ruleItemCount = ruleItemCount - itemsToBuy;
            }
        }
        return this.discountAmount;
    }


}
