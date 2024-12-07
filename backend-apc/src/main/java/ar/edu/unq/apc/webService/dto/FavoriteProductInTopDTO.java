package ar.edu.unq.apc.webService.dto;

public class FavoriteProductInTopDTO {

    private String productId;

    private Integer timesChosenFavorite;

    public FavoriteProductInTopDTO() {
    }

    public FavoriteProductInTopDTO(String productId, Integer timesChosenFavorite) {
        this.productId = productId;
        this.timesChosenFavorite = timesChosenFavorite;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getTimesChosenFavorite() {
        return timesChosenFavorite;
    }

    public void setTimesChosenFavorite(Integer timesChosenFavorite) {
        this.timesChosenFavorite = timesChosenFavorite;
    }
    
}
