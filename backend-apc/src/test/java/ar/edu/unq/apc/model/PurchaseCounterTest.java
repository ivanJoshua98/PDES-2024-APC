package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseCounterTest {

    private PurchaseCounter anyCounter;

    @BeforeEach
    private void init(){
        this.anyCounter = new PurchaseCounter();
    }

    
    @Test
    void whenItsSetsThePurchaseCounterThenItsReturnsANewCounter() {
        anyCounter.setPurchaseCounter(3);

        assertEquals(3, anyCounter.getPurchaseCounter());
    }

    @Test
    void whenItsAddsAAmountOfPurchasesThenItsReturnsANewTotal() {
        anyCounter.setPurchaseCounter(1);
        anyCounter.addAmountOfPurchases(4);

        assertEquals(5, anyCounter.getPurchaseCounter());

    }

}
