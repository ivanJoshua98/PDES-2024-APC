package ar.edu.unq.apc.webService.dto;

import java.util.UUID;

public class UserDTO {

    private UUID id;
    
    private String userName;

    private String email;



    public UserDTO(){
        super();
    }

    public UserDTO(UUID id, String userName, String email) {
        this.userName = userName;
        this.email = email;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    

}
