package ar.edu.unq.apc.model;


import jakarta.persistence.*;

@Entity
public class AdminUser extends User {

    public AdminUser(String id, String name, String email, String password) {
        super(id, name, email, password);
    }
}