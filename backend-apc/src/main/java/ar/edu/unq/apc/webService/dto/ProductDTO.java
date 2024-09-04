package ar.edu.unq.apc.webService.dto;

import java.util.List;

import ar.edu.unq.apc.model.Attribute;

public class ProductDTO {

    private String id;
    private String link;
    private String title;
    private String categoryId;
    private Double price;
    private List<String> pictures;
    private List<Attribute> attributes;
    private String condition;

    
    public ProductDTO(String id, String link, String title, String categoryId, Double price, List<String> pictures,
            List<Attribute> attributes, String condition) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.categoryId = categoryId;
        this.price = price;
        this.pictures = pictures;
        this.attributes = attributes;
        this.condition = condition;
    }


    public ProductDTO() {
    }


    


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public List<String> getPictures() {
        return pictures;
    }
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
    public List<Attribute> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
}
