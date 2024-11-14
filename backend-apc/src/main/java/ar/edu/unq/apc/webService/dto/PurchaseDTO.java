package ar.edu.unq.apc.webService.dto;

import java.util.List;
import java.util.UUID;

public class PurchaseDTO {
    
    private Double salePrice;

    private List<String> soldProductsIds;

    private UUID buyerId;

    public PurchaseDTO(Double salePrice, List<String> soldProductsIds, UUID buyerId) {
        this.salePrice = salePrice;
        this.soldProductsIds = soldProductsIds;
        this.buyerId = buyerId;
    }


    public PurchaseDTO() {
        super();
    }

    public Double getSalePrice() {
        return salePrice;
    }

    
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public List<String> getSoldProductsIds() {
        return soldProductsIds;
    }

    public void setSoldProductsIds(List<String> soldProductsIds) {
        this.soldProductsIds = soldProductsIds;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(UUID buyerId) {
        this.buyerId = buyerId;
    }

}
