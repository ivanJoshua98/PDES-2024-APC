package ar.edu.unq.apc.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

    private Double salePrice;

    
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable( name="sold_products",
                joinColumns = @JoinColumn(name="purchase_id"),
                inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<Product> soldProducts;

    @ManyToOne
    private UserModel buyer;


    public Purchase() {
        super();
    }

    public Purchase(Double salePrice, List<Product> soldProducts, UserModel buyer) {
        this.salePrice = salePrice;
        this.soldProducts = soldProducts;
        this.buyer = buyer;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public List<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public UserModel getBuyer() {
        return buyer;
    }

    public void setBuyer(UserModel buyer) {
        this.buyer = buyer;
    }

}
