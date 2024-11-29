package ar.edu.unq.apc.webService.dto;

import java.util.UUID;

public class UserWithMostPurchasesDTO {

    private UUID id;

    private String userName;

    private String email;

    private Integer purchases_count;

    public UserWithMostPurchasesDTO() {
    }

    public UserWithMostPurchasesDTO(UUID id, String userName, String email, Integer purchases_count) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.purchases_count = purchases_count;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPurchases_count() {
        return purchases_count;
    }

    public void setPurchases_count(Integer purchases_count) {
        this.purchases_count = purchases_count;
    }
    
}
