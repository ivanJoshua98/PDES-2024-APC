package ar.edu.unq.apc.webService;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.BuyerUser;
import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.service.impl.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin user services", description = "Manage admin users of the application")
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Operation(summary = "Get a user from their id")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "The user id that needs to be fetched", required = true)
            @PathVariable Long userId) {
        User user = adminUserService.consultUser(userId);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all registered users")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<BuyerUser>> getAllUsers() {
        List<BuyerUser> users = adminUserService.consultUsers();
        return ResponseEntity.ok(users);
    }


    /*

    ---------------- PRODUCT CONTROLLER ----------------------------------------------------------------------

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        Product product = adminUserService.consultProduct(productId);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping("/products/saved")
    public ResponseEntity<List<Product>> viewSavedProducts(@RequestBody List<String> productIds) {
        List<Product> products = adminUserService.viewSavedProducts(productIds);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Product>> getFavoriteProductsByUser(@PathVariable Long userId) {
        List<Product> favoriteProducts = adminUserService.consultFavoriteProductsByUser(userId);
        return ResponseEntity.ok(favoriteProducts);
    }


    SAVE PRODUCTS AND FAVORITES IS SAME?

    ---------------- BUY CONTROLLER ----------------------------------------------------------------------
    @GetMapping("/buys/{buyId}")
    public ResponseEntity<Buy> getBuyById(@PathVariable Long buyId) {
        Buy buy = adminUserService.consultBuy(buyId);
        return (buy != null) ? ResponseEntity.ok(buy) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/buys")
    public ResponseEntity<List<Buy>> getBuysByUser(@PathVariable Long userId) {
        List<Buy> buys = adminUserService.consultBuysByUser(userId);
        return ResponseEntity.ok(buys);
    }

    
     */

}

