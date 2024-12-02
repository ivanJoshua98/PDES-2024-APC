package ar.edu.unq.apc.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductInCart extends Product{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

    private String mercadoLibreId;

    private Integer amount;

    private String picture;

    public ProductInCart(   String link, 
                            String title, 
                            String categoryId, 
                            Double price, 
                            String condition, 
                            String mercadoLibreId, 
                            Integer amount, 
                            String picture){
        super(link, title, categoryId, price, condition);  
        this.mercadoLibreId = mercadoLibreId;
        this.amount = amount;
        this.picture = picture;
    }

    public ProductInCart(){
        super();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMercadoLibreId() {
        return mercadoLibreId;
    }

    public void setMercadoLibreId(String mercadoLibreId) {
        this.mercadoLibreId = mercadoLibreId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void addAmountOneTime(){
        ++amount;
    }

    public void subtractAmountOneTime(){
        if(amount > 1 ){
            --amount;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mercadoLibreId == null) ? 0 : mercadoLibreId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductInCart other = (ProductInCart) obj;
        if (mercadoLibreId == null) {
            if (other.getMercadoLibreId() != null)
                return false;
        } else if (!mercadoLibreId.equals(other.getMercadoLibreId()))
            return false;
        return true;
    }

   
    
    
}
