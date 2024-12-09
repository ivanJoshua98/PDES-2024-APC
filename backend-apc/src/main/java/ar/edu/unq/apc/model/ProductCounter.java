package ar.edu.unq.apc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductCounter extends PurchaseCounter {

    @Id
    private String productId;

    private String title;

    private String picture;

    private Integer favoriteCounter;

    public ProductCounter(Integer purchaseCounter, String productId, String title, String picture, Integer favoriteCounter) {
        super(purchaseCounter);
        this.productId = productId;
        this.title = title;
        this.picture = picture;
        this.favoriteCounter = favoriteCounter;
    }

    public ProductCounter(){
        super();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getFavoriteCounter() {
        return favoriteCounter;
    }

    public void setFavoriteCounter(Integer favoriteCounter) {
        this.favoriteCounter = favoriteCounter;
    }

    public void addAmountOfFavorites(Integer amount){
        this.favoriteCounter = favoriteCounter + amount;
    }
    
}
