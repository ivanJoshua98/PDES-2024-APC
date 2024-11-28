package ar.edu.unq.apc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PurchasedProductCounter extends SystemReport{
    
    @Id
    private String mercadoLibreId;

    private Integer purchasesCount;

    public PurchasedProductCounter() {
    }

    public PurchasedProductCounter(String mercadoLibreId, Integer purchasesCount) {
        this.mercadoLibreId = mercadoLibreId;
        this.purchasesCount = purchasesCount;
    }

    public String getMercadoLibreId() {
        return mercadoLibreId;
    }

    public void setMercadoLibreId(String mercadoLibreId) {
        this.mercadoLibreId = mercadoLibreId;
    }

    public Integer getPurchasesCount() {
        return purchasesCount;
    }

    public void setPurchasesCount(Integer purchasesCount) {
        this.purchasesCount = purchasesCount;
    }

    public void countNewPurchase(){
        ++this.purchasesCount;
    }
}
