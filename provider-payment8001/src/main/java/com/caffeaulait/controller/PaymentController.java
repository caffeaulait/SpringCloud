package com.caffeaulait.controller;


import com.caffeaulait.entities.CommonResult;
import com.caffeaulait.entities.Payment;
import com.caffeaulait.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping(value = "/discovery")
    public Object discover() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("****** service: " + service + " ********");
        }
        List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(
                "CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : serviceInstances) {
            log.info(instance.getServiceId() + " " + instance.getHost() + " " +
                    instance.getPort() + " " + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/lb")
    public String getLB() {
        return serverPort;
    }

    @GetMapping(value = "/feign/timeout")
    public String timeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }
}
