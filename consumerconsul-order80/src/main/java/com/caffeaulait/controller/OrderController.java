package com.caffeaulait.controller;

import com.caffeaulait.entities.CommonResult;
import com.caffeaulait.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://consul-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/payment/consul")
    public String testConsul() {
        return  restTemplate.getForObject(PAYMENT_URL+"/payment/zk",
                String.class);
    }
}