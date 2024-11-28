package ar.edu.unq.apc.webService.dto;

public class FavoriteProductInTopFiveDTO {

    private String productId;

    private Integer timesChosenFavorite;

    public FavoriteProductInTopFiveDTO() {
    }

    public FavoriteProductInTopFiveDTO(String productId, Integer timesChosenFavorite) {
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
