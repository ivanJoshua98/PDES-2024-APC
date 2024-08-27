package ar.edu.unq.apc.model;

import java.util.List;

public class ManagementUser extends User {

    public ManagementUser(String id, String name, String email, String password) {
        super(id, name, email, password);
    }
    //consultar a la base de datos
    public void consultUser(User user){

    }

    public void consultProduct(Product pro){

    }

    public void consultBuy(Buy buy){
        
    }
    public void consultUsers(List<User> users) {

    }

    public void viewSavedProducts(List<Product> products) {

    }

    public void checkPurchasesMade(List<Buy> buys) {

    }
}
