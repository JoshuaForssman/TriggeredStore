package com.triggered.store.service;


import com.triggered.store.dao.ProductRepository;
import com.triggered.store.entity.Product;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class Checkout {

    @Autowired
    ProductRepository productRepository;

    private List<Product> userScannedProducts = new ArrayList<>();
    private List<PricingRule> pricingRules;


    public Checkout(List<PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }

    public Checkout scan(String productCode){

        Product myProd = productRepository.findById(productCode).get();
        this.userScannedProducts.add(myProd);
        return this;

    }

    public List<Product> getUserProducts(){
        return this.userScannedProducts;
    }

    public BigDecimal total(){

        BigDecimal discountValue = BigDecimal.ZERO;

        for (PricingRule rule: this.pricingRules) {
            discountValue = discountValue.add(rule.ruleCalulation(this.userScannedProducts));
        }

        return BigDecimal.valueOf(this.userScannedProducts.stream()
                .mapToDouble(product ->product.getProductPrice().doubleValue())
                .sum()).subtract(discountValue);
    }
}
