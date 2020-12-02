package com.caffeaulait.controller;


import com.caffeaulait.entities.CommonResult;
import com.caffeaulait.entities.Payment;
import com.caffeaulait.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****插入结果: " + result);
        if (result > 0) {
            return new CommonResult(200, "成功, port:" + serverPort, result);
        }else {
            return new CommonResult(400, "失败", null);
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****获取结果: " + payment);
        if (payment != null) {
            return new CommonResult<Payment>(200, "成功, port:" + serverPort, payment);
        }else {
            return new CommonResult<Payment>(400, "失败", null);
        }
    }
}
