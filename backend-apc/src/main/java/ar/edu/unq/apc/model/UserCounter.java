package ar.edu.unq.apc.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserCounter extends PurchaseCounter {

    @Id
    private UUID userId;

    private String userName;

    private String email;

    public UserCounter() {
        super();
    }

    public UserCounter(Integer counter, UUID userId, String userName, String email) {
        super(counter);
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
    
}
