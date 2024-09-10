package ar.edu.unq.apc.webService;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.BuyerUser;
import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.service.impl.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = adminUserService.consultUser(userId);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<BuyerUser>> getAllUsers() {
        List<BuyerUser> users = adminUserService.consultUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        Product product = adminUserService.consultProduct(productId);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buys/{buyId}")
    public ResponseEntity<Buy> getBuyById(@PathVariable Long buyId) {
        Buy buy = adminUserService.consultBuy(buyId);
        return (buy != null) ? ResponseEntity.ok(buy) : ResponseEntity.notFound().build();
    }

    @GetMapping("/products/saved")
    public ResponseEntity<List<Product>> viewSavedProducts(@RequestBody List<String> productIds) {
        List<Product> products = adminUserService.viewSavedProducts(productIds);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{userId}/buys")
    public ResponseEntity<List<Buy>> getBuysByUser(@PathVariable Long userId) {
        List<Buy> buys = adminUserService.consultBuysByUser(userId);
        return ResponseEntity.ok(buys);
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Product>> getFavoriteProductsByUser(@PathVariable Long userId) {
        List<Product> favoriteProducts = adminUserService.consultFavoriteProductsByUser(userId);
        return ResponseEntity.ok(favoriteProducts);
    }

    @GetMapping("/buys")
    public ResponseEntity<List<Buy>> getAllBuys() {
        List<Buy> buys = adminUserService.consultAllBuys();
        return ResponseEntity.ok(buys);
    }
}

