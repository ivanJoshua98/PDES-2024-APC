package ar.edu.unq.apc.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Product {

    private String link;
    private String title;
    private String categoryId;
    private Double price;
    private String condition;


    public Product() {
        super();
        this.price = 0.0;
    }

    public Product(String link, String title, String categoryId, Double price, String condition) {
        this.link = link;
        this.title = title;
        this.categoryId = categoryId;
        this.price = price;
        this.condition = condition;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


}