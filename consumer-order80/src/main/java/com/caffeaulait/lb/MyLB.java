package com.caffeaulait.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer{

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public ServiceInstance getInstance(List<ServiceInstance> serviceInstances) {
        int size = serviceInstances.size();
        int index = getAndIncrement() % size;
        return serviceInstances.get(index);
    }

    public final int getAndIncrement() {
        int curr, next;
        do {
            curr = this.atomicInteger.get();
            next = curr >= Integer.MAX_VALUE ? 0 : curr + 1;
        } while (!this.atomicInteger.compareAndSet(curr, next));
        return next;
    }
}
