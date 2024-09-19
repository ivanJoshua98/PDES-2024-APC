package ar.edu.unq.apc.webService;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.model.exceptions.IncorrectPasswordException;
import ar.edu.unq.apc.model.exceptions.UserAlreadyExistsException;
import ar.edu.unq.apc.model.exceptions.UserNotFoundException;
import ar.edu.unq.apc.service.impl.BuyerUserService;
import ar.edu.unq.apc.webService.dto.BuyerDTO;
import ar.edu.unq.apc.webService.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Buyer user services", description = "Manage buyer users of the application")
@RestController
@RequestMapping("/buyer-users")
public class BuyerUserController {

    @Autowired
    private BuyerUserService userService;


    @Operation(summary = "Register a new user")
    @PostMapping("/register")  //funciona
    public ResponseEntity<String> register(@RequestBody BuyerDTO userDto) {
        try {
            User newUser = userService.registerUser(userDto.getName(), userDto.getEmail(), userDto.getPassword());
            return ResponseEntity.ok("Registered successfully");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: User with this email already exists");
        }
    }


    @Operation(summary = "Login a user")
    @PostMapping("/login")  //funciona
    public ResponseEntity<String> login(@RequestBody LoginDTO userDto) {
        try {
            User user = userService.login(userDto.getEmail(), userDto.getPassword());
            String welcomeMessage = "¡Welcome " + user.getName() + "!";
            return ResponseEntity.ok(welcomeMessage);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Email not found");
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Incorrect password");
        }
    }

    @Operation(summary = "A user adds a new product in their favourites product list")
    @PostMapping("/{buyerUserId}/favorite-product/{productId}")
    public ResponseEntity<Void> addFavoriteProduct(
            @Parameter(description = "The product id that needs to be fetched", required = true)
            @PathVariable Long buyerUserId,
            @PathVariable String productId) {
        userService.addFavoriteProduct(buyerUserId, productId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "A user deletes a product in their favourites product list")
    @DeleteMapping("/{buyerUserId}/favorite-product/{productId}")
    public ResponseEntity<Void> deleteFavoriteProduct(
            @Parameter(description = "The product id that needs to be fetched", required = true)
            @PathVariable Long buyerUserId,
            @PathVariable String productId) {
        userService.deleteFavoriteProduct(buyerUserId, productId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "A user adds a new purchase in their history")
    @PostMapping("/{buyerUserId}/purchase")
    public ResponseEntity<Void> addPurchase(
            @Parameter(description = "The buyer user id that needs to be fetched", required = true)
            @PathVariable Long buyerUserId,
            @RequestBody Buy buy) {
        userService.addPurchase(buyerUserId, buy);
        return ResponseEntity.ok().build();
    }
}

