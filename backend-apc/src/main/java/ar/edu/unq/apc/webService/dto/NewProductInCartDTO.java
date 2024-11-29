package ar.edu.unq.apc.webService.dto;

public class NewProductInCartDTO {

    private String mercadoLibreId;

    private Integer amount;

    private String picture;

    private String link;

    private String title;

    private String categoryId;

    private Double price;

    private String condition;


    public NewProductInCartDTO() {
        super();
    }

    public NewProductInCartDTO(String mercadoLibreId, Integer amount, String picture, String link, String title,
            String categoryId, Double price, String condition) {
        this.mercadoLibreId = mercadoLibreId;
        this.amount = amount;
        this.picture = picture;
        this.link = link;
        this.title = title;
        this.categoryId = categoryId;
        this.price = price;
        this.condition = condition;
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
