package ar.edu.unq.apc.webService.dto;

public class ProductFromTopDTO {

    private String productId;

    private String title;

    private String picture;

    private Integer timesPurchased;

    private Integer timesChosenFavorite;

    public ProductFromTopDTO() {
    }

    public ProductFromTopDTO(String productId, Integer timesPurchased, String title, String picture, Integer timesChosenFavorite) {
        this.productId = productId;
        this.timesPurchased = timesPurchased;
        this.picture = picture;
        this.title = title;
        this.timesChosenFavorite = timesChosenFavorite;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getTimesPurchased() {
        return timesPurchased;
    }

    public void setTimesPurchased(Integer timesPurchased) {
        this.timesPurchased = timesPurchased;
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

    public Integer getTimesChosenFavorite() {
        return timesChosenFavorite;
    }

    public void setTimesChosenFavorite(Integer timesChosenFavorite) {
        this.timesChosenFavorite = timesChosenFavorite;
    }
    
}
