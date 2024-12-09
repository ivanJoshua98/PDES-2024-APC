package ar.edu.unq.apc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String userName; 
     
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinTable( name="user_roles",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    @ElementCollection
    @CollectionTable(name = "favorite_products", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<String> favoriteProducts;

    
    public UserModel() {
        this.roles = new ArrayList<>();
        this.favoriteProducts = new ArrayList<>();
    }

    public UserModel(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<>();
        this.favoriteProducts = new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public void addRole(Role role) {
        if(!this.roles.contains(role)){
            this.roles.add(role);
        }
	}

    public void removeAdminRole(Role adminRole){
        if(adminRole.getName().equals("ADMIN")){
            this.roles.remove(adminRole);
        }
    }

    public Boolean isAdmin(){
        Role adminRole = new Role("ADMIN");
        return this.roles.contains(adminRole);
    }

    public List<String> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<String> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public Boolean isFavoriteProduct(String productId){
        return this.favoriteProducts.contains(productId);
    }

    public void addFavoriteProduct(String productId){
        if(!isFavoriteProduct(productId)){
            this.favoriteProducts.add(productId);
        }
    }

    public void removeFavoriteProduct(String productId){
        this.favoriteProducts.remove(productId);
    }


}
