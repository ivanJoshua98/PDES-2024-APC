package ar.edu.unq.apc.webService.dto;

import ar.edu.unq.apc.model.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class RegisterUserDTO {

    @Size(min = 3, max = 30, message = "Invalid length for name")
    private String userName;

    @Email(message = "The email entered is invalid")
    private String email;

    @ValidPassword
	@Size(min = 6, message = "The password is less than 6 characters")
    private String password;


    public RegisterUserDTO(){
        super();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

}

    
