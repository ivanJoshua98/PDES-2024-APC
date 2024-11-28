package ar.edu.unq.apc.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductsPurchasedByUserCounter extends SystemReport{

    @Id
    private UUID userId;

    private Integer productsPurchasesCount;

    public ProductsPurchasedByUserCounter() {
        super();
    }

    public ProductsPurchasedByUserCounter(String mercadoLibreId, Integer productsPurchasesCount, UUID userId) {
        this.productsPurchasesCount = productsPurchasesCount;
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getProductsPurchasesCount() {
        return productsPurchasesCount;
    }

    public void setProductsPurchasesCount(Integer productsPurchasesCount) {
        this.productsPurchasesCount = productsPurchasesCount;
    }
    
}
