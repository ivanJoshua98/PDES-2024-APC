package ar.edu.unq.apc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.persistence.ShoppingCartRepository;
import ar.edu.unq.apc.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        try {
            getShoppingCartInProgressByBuyer(shoppingCart.getBuyer());   
        } catch (HttpException notFoundCartInProgress) {
            return this.shoppingCartRepository.save(shoppingCart);
        }
        throw new HttpException("Shopping cart in progress already exists", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAllShoppingCart() {
        return this.shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart getShoppingCartById(UUID id) {
        return this.shoppingCartRepository.findById(id).orElseThrow(() -> new HttpException("Cart not found by id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public ShoppingCart getShoppingCartByBuyer(UserModel buyer) {
        return this.shoppingCartRepository.findByBuyer(buyer).orElseThrow(() -> new HttpException("Cart not found by user", HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteShoppingCartById(UUID id) {
        this.shoppingCartRepository.deleteById(id);
    }

    @Override
    public ShoppingCart getShoppingCartInProgressByBuyer(UserModel buyer) {
        return this.shoppingCartRepository.findByBuyerAndCartState(buyer, CartState.INPROGRESS).orElseThrow(() -> new HttpException("Cart in progress not found", HttpStatus.NOT_FOUND));
    }
    
}
