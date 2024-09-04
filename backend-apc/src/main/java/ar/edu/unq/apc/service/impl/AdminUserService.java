package ar.edu.unq.apc.service.impl;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.BuyerUser;
import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.persistence.AdminUserRepository;
import ar.edu.unq.apc.persistence.ProductRepository;
import ar.edu.unq.apc.persistence.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyRepository buyRepository;

    public User consultUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }

    public Product consultProduct(Long productId){
        return productRepository.findById(productId).orElse(null);
    }

    public Buy consultBuy(Long buyId){
        return buyRepository.findById(buyId).orElse(null);
    }

    public List<User> consultUsers() {
        return userRepository.findAll();
    }

    public List<Product> viewSavedProducts(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

}

