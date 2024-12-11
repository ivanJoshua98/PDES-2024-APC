package ar.edu.unq.apc.service;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class PurchasesMetrics {

    private final Counter purchasesCounter;

    public PurchasesMetrics(MeterRegistry registry){
        purchasesCounter = Counter.builder("purchases.completed")
                                 .description("Number of completed purchases")
                                 .register(registry);
    }

    public void countedCall(){
        purchasesCounter.increment();
    }
    
}
