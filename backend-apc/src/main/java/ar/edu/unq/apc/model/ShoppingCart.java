package ar.edu.unq.apc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;


@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

    private CartState cartState;

    @ManyToOne
    private UserModel buyer;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="cart",
                joinColumns = @JoinColumn(name="cart_id"),
                inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<ProductInCart> cart;

    private Double totalAmountPurchase;

    public ShoppingCart(List<ProductInCart> cart, Double totalAmountPurchase, UserModel buyer){
        this.cartState = CartState.INPROGRESS;
        this.cart = cart;
        this.totalAmountPurchase = totalAmountPurchase;
        this.buyer = buyer;
    }

    public ShoppingCart(){
        super();
        this.cart = new ArrayList<>();
        this.totalAmountPurchase = 0.0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CartState getCartState() {
        return cartState;
    }

    public void setCartState(CartState cartState) {
        this.cartState = cartState;
    }

    public UserModel getBuyer() {
        return buyer;
    }

    public void setBuyer(UserModel buyer) {
        this.buyer = buyer;
    }

    public List<ProductInCart> getCart() {
        return cart;
    }

    public void setCart(List<ProductInCart> cart) {
        this.cart = cart;
    }

    public Double getTotalAmountPurchase() {
        return totalAmountPurchase;
    }

    public void setTotalAmountPurchase(Double totalAmountPurchase) {
        this.totalAmountPurchase = totalAmountPurchase;
    }


    public void addProductOneTime(ProductInCart product){
        Integer index = this.cart.indexOf(product);
        this.cart.get(index).addAmountOneTime();
        setTotalAmountPurchase(totalAmountPurchase + product.getPrice());
    }
    
    
    public void subtractProductOneTime(ProductInCart product){
        Integer index = this.cart.indexOf(product);
        this.cart.get(index).subtractAmountOneTime();
        if (this.cart.get(index).getAmount() > 1){
            setTotalAmountPurchase(totalAmountPurchase - product.getPrice());
        }
    }

    public void addProduct(ProductInCart product){
        if(this.cart.contains(product)){
            addProductOneTime(product);
        } else {
            this.cart.add(product);
            setTotalAmountPurchase(totalAmountPurchase + product.getPrice() * product.getAmount());
        }
    }

    public void removeProduct(ProductInCart product){
        this.cart.remove(product);
        setTotalAmountPurchase(totalAmountPurchase - (product.getAmount() * product.getPrice()));
    }

}
