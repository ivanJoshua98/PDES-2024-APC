package ar.edu.unq.apc.webService.dto;

public class PurchasedProductInTopDTO {

    private String productId;

    private Integer timesPurchased;

    public PurchasedProductInTopDTO() {
    }

    public PurchasedProductInTopDTO(String productId, Integer timesPurchased) {
        this.productId = productId;
        this.timesPurchased = timesPurchased;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getTimesPurchased() {
        return timesPurchased;
    }

    public void setTimesPurchased(Integer timesPurchased) {
        this.timesPurchased = timesPurchased;
    }
    
}
