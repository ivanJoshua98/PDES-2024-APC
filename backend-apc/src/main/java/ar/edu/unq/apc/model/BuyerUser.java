package ar.edu.unq.apc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;
import java.util.ArrayList;

@Entity
public class BuyerUser extends User {

    @ManyToMany
    @JoinTable(
            name = "buyer_favorite_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> favoriteProducts = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Buy> purchasesMade = new ArrayList<>();

    public BuyerUser(String id, String name, String email, String password) {
        super(id, name, email, password);
    }


    public List<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void addFavoriteProduct(Product product){
        favoriteProducts.add(product);
    }

    public void deleteFavoriteProduct(Product product){
        favoriteProducts.remove(product);
    }

    public List<Buy> getPurchasesMade() {
        return purchasesMade;
    }

    public void addBuy(Buy purchaseMade){
        purchasesMade.add(purchaseMade);
    }

}

