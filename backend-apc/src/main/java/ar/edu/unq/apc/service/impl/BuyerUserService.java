package ar.edu.unq.apc.service.impl;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.BuyerUser;
import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.model.exceptions.IncorrectPasswordException;
import ar.edu.unq.apc.model.exceptions.UserAlreadyExistsException;
import ar.edu.unq.apc.model.exceptions.UserNotFoundException;
import ar.edu.unq.apc.persistence.BuyRepository;
import ar.edu.unq.apc.persistence.BuyerUserRepository;
import ar.edu.unq.apc.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<BuyerUser> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        BuyerUser newUser = new BuyerUser(name, email, password);
        return userRepository.save(newUser);
    }

    public BuyerUser login(String email, String password) {
        Optional<BuyerUser> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException("Email not found");
        }

        BuyerUser user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
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
