package ar.edu.unq.apc.webService;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.service.impl.BuyerUserService;
import ar.edu.unq.apc.webService.dto.BuyerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer-users")
public class BuyerUserController {

    @Autowired
    private BuyerUserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody BuyerDTO userDto) {
        User newUser = userService.registerUser(userDto.getName(), userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody BuyerDTO userDto) {
        User user = userService.login(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{buyerUserId}/favorite-product/{productId}")
    public ResponseEntity<Void> addFavoriteProduct(@PathVariable Long buyerUserId,
                                                   @PathVariable String productId) {
        userService.addFavoriteProduct(buyerUserId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{buyerUserId}/favorite-product/{productId}")
    public ResponseEntity<Void> deleteFavoriteProduct(@PathVariable Long buyerUserId,
                                                      @PathVariable String productId) {
        userService.deleteFavoriteProduct(buyerUserId, productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{buyerUserId}/purchase")
    public ResponseEntity<Void> addPurchase(@PathVariable Long buyerUserId,
                                            @RequestBody Buy buy) {
        userService.addPurchase(buyerUserId, buy);
        return ResponseEntity.ok().build();
    }
}

