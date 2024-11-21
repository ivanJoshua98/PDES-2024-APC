package ar.edu.unq.apc.service;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserModel;

public interface ShoppingCartService {

    
    ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

    List<ShoppingCart> getAllShoppingCart();

    ShoppingCart getShoppingCartById(UUID id);

    ShoppingCart getShoppingCartByBuyer(UserModel buyer);

    void deleteShoppingCartById(UUID id);

    ShoppingCart getShoppingCartInProgressByBuyer(UserModel buyer);
    
}
