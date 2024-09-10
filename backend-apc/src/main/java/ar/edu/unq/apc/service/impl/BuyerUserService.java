package ar.edu.unq.apc.service.impl;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.BuyerUser;
import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.persistence.BuyRepository;
import ar.edu.unq.apc.persistence.BuyerUserRepository;
import ar.edu.unq.apc.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BuyerUserService {
    @Autowired
    private BuyerUserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public BuyerUser registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        String encodedPassword = passwordEncoder.encode(password);
        BuyerUser user = new BuyerUser(name, email, encodedPassword);

        return userRepository.save(user);
    }

    public BuyerUser login(String email, String password) {
        BuyerUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return user;
    }

    public void addFavoriteProduct(Long buyerUserId, String productId) {
        BuyerUser buyerUser = (BuyerUser) userRepository.findById(buyerUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        buyerUser.addFavoriteProduct(product);
        userRepository.save(buyerUser);
    }

    public void deleteFavoriteProduct(Long buyerUserId, String productId) {
        BuyerUser buyerUser = userRepository.findById(buyerUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        buyerUser.deleteFavoriteProduct(product);
        userRepository.save(buyerUser);
    }

    public void addPurchase(Long buyerUserId, Buy buy) {
        BuyerUser buyerUser = userRepository.findById(buyerUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        buyerUser.addBuy(buy);
        buyRepository.save(buy);
    }
}
