package ar.edu.unq.apc.webService.dto;

import java.util.UUID;

public class ProductInCartDTO extends NewProductInCartDTO{

    private UUID id;

    public ProductInCartDTO() {
        super();
    }

    public ProductInCartDTO(UUID id, String mercadoLibreId, Integer amount, String picture, String link, String title,
            String categoryId, Double price, String condition) {
        super(mercadoLibreId, amount, picture, link, title, categoryId, price, condition);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
