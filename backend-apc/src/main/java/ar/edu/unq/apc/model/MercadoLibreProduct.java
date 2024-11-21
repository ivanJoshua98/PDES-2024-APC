package ar.edu.unq.apc.model;

import java.util.List;

public class MercadoLibreProduct extends Product{

    private String id;

    private List<String> pictures;

    public MercadoLibreProduct( String id, 
                                String link, 
                                String title, 
                                String categoryId, 
                                Double price, 
                                String condition, 
                                List<String> pictures) {
        super(link, title, categoryId, price, condition);    
        this.id = id;
        this.pictures = pictures;
    }


    public MercadoLibreProduct() {
        super();
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public List<String> getPictures() {
        return pictures;
    }


    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}