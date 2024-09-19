package ar.edu.unq.apc.model;


import jakarta.persistence.*;

@Entity
public class AdminUser extends User {

    public AdminUser(String name, String email, String password) {
        super(name, email, password);
    }
}