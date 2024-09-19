package ar.edu.unq.apc.model;

import jakarta.persistence.*;

@Entity
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private BuyerUser buyer;
    private String productName;
    private Double price;

    public Buy(Long id, BuyerUser buyer, String productName, Double price) {
        this.id = id;
        this.buyer = buyer;
        this.productName = productName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public BuyerUser getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerUser buyer) {
        this.buyer = buyer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}