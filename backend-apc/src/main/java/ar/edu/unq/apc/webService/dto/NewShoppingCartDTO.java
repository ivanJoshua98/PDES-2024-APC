package ar.edu.unq.apc.webService.dto;

import java.util.List;
import java.util.UUID;

public class NewShoppingCartDTO {

    private List<NewProductInCartDTO> productsInCart;

    private UUID buyerId;


    public NewShoppingCartDTO() {
        super();
    }

    public NewShoppingCartDTO(Double totalAmountPurchase, List<NewProductInCartDTO> productsInCart, UUID buyerId) {
        this.productsInCart = productsInCart;
        this.buyerId = buyerId;
    }

    public List<NewProductInCartDTO> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(List<NewProductInCartDTO> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(UUID buyerId) {
        this.buyerId = buyerId;
    }

}
