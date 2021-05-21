package com.triggered.store.controller;

import com.triggered.store.service.Checkout;
import com.triggered.store.service.PricingRule;
import com.triggered.store.service.impl.PricingRuleAmountForDiscount;
import com.triggered.store.service.impl.PricingRuleXForZ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PurchaseController {

    @Autowired
    Checkout checkout;

    @GetMapping("/triggered")
    @ResponseBody
    public BigDecimal sayHello() {

        List<PricingRule> pricingRules = new ArrayList<>();
        pricingRules.add(new PricingRuleXForZ(2,1,"MUG"));
        pricingRules.add(new PricingRuleAmountForDiscount(3,BigDecimal.valueOf(30),"TSHIRT"));

        checkout.setPricingRules(pricingRules);
       // Checkout checkout = new Checkout(pricingRules);

        return checkout.scan("TSHIRT").scan("TSHIRT").scan("TSHIRT")
                .scan("MUG").scan("MUG").scan("MUG")
                .scan("USBKEY").total();
    }
}
