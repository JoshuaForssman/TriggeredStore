package com.triggered.store.service;

import com.triggered.store.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface PricingRule {

    BigDecimal ruleCalulation(List<Product> cartItems);

}
