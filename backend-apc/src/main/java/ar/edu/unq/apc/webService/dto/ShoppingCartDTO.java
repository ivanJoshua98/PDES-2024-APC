package ar.edu.unq.apc.webService.dto;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.CartState;

public class ShoppingCartDTO {

    private UUID id;

    private Double totalAmountPurchase;

    private List<ProductInCartDTO> productsInCart;

    private UUID buyerId;

    private CartState cartState;


    public ShoppingCartDTO(UUID id, Double totalAmountPurchase, List<ProductInCartDTO> productsInCart, UUID buyerId, CartState cartState) {
        this.id = id;
        this.totalAmountPurchase = totalAmountPurchase;
        this.productsInCart = productsInCart;
        this.buyerId = buyerId;
        this.cartState = cartState;
    }

    public ShoppingCartDTO() {
        super();
    }

    public CartState getCartState() {
        return cartState;
    }

    public void setCartState(CartState cartState) {
        this.cartState = cartState;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getTotalAmountPurchase() {
        return totalAmountPurchase;
    }

    public void setTotalAmountPurchase(Double totalAmountPurchase) {
        this.totalAmountPurchase = totalAmountPurchase;
    }

    public List<ProductInCartDTO> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(List<ProductInCartDTO> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(UUID buyerId) {
        this.buyerId = buyerId;
    }


    
}
