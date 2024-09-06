package ar.edu.unq.apc.service.impl;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.BuyerUser;
import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.persistence.BuyerUserRepository;
import ar.edu.unq.apc.persistence.ProductRepository;
import ar.edu.unq.apc.persistence.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserService {

    @Autowired
    private BuyerUserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyRepository buyRepository;

    public User consultUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }

    public List<BuyerUser> consultUsers() {
        return userRepository.findAll();
    }

    public Product consultProduct(String productId){
        return productRepository.findById(productId).orElse(null);
    }

    public Buy consultBuy(Long buyId){
        return buyRepository.findById(buyId).orElse(null);
    }

    public List<Product> viewSavedProducts(List<String> productIds) {
        return productRepository.findAllById(productIds);
    }

    public List<Buy> consultBuysByUser(String userId) {
        return buyRepository.findByBuyer_Id(userId);
    }

    public List<Product> consultFavoriteProductsByUser(String userId) {
        BuyerUser user = userRepository.findById(userId).orElse(null);
        return (user != null) ? user.getFavoriteProducts() : new ArrayList<>();
    }

    public List<Buy> consultAllBuys() {
        return buyRepository.findAll();
    }
}


