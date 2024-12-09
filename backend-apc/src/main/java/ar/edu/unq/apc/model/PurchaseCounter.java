package ar.edu.unq.apc.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class PurchaseCounter extends SystemReport {

    private Integer purchaseCounter;


    public PurchaseCounter() {
    }

    public PurchaseCounter(Integer purchaseCounter) {
        this.purchaseCounter = purchaseCounter;
    }


    public Integer getPurchaseCounter() {
        return purchaseCounter;
    }

    public void setPurchaseCounter(Integer purchaseCounter) {
        this.purchaseCounter = purchaseCounter;
    }

    public void addAmountOfPurchases(Integer amount){
        this.purchaseCounter = purchaseCounter + amount;
    }
    
}
