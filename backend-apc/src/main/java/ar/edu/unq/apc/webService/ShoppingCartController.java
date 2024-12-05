package ar.edu.unq.apc.webService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.ProductInCart;
import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.security.JWTProvider;
import ar.edu.unq.apc.service.ProductInCartService;
import ar.edu.unq.apc.service.ShoppingCartService;
import ar.edu.unq.apc.service.UserService;
import ar.edu.unq.apc.webService.dto.NewProductInCartDTO;
import ar.edu.unq.apc.webService.dto.NewShoppingCartDTO;
import ar.edu.unq.apc.webService.dto.ProductInCartDTO;
import ar.edu.unq.apc.webService.dto.ShoppingCartDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Purchase service", description = "Manage purchases of the application")
@RequestMapping("/apc")
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductInCartService productInCartService;

    @Autowired
    private JWTProvider jwtProvider;
    

    @Operation(summary = "Create a new shopping cart in the application")
    @PostMapping("/shopping-cart/new-shopping-cart")
    public ResponseEntity<ShoppingCartDTO> newShoppingCart(@RequestBody NewShoppingCartDTO cart){
        UserModel buyer = this.userService.getUserById(cart.getBuyerId());
        List<ProductInCart> productsInCart = cart.getProductsInCart().stream().map(this::convertNewProductInCartDTOToProductInCartEntity).toList();
        Double totalAmountPurchase = 0.0;
        for (ProductInCart product:productsInCart){
            totalAmountPurchase = totalAmountPurchase + (product.getAmount() * product.getPrice());
        }
        ShoppingCart newShoppingCart = new ShoppingCart(productsInCart, totalAmountPurchase, buyer);

        productsInCart.forEach(
            product -> this.productInCartService.saveProduct(product)
        );

        this.shoppingCartService.saveShoppingCart(newShoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(newShoppingCart));
    }


    @Operation(summary = "Get the shopping cart by id")
    @GetMapping("/admin/shopping-cart/{shoppingCartId}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable String shoppingCartId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));

        return ResponseEntity.ok().body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }


    @Operation(summary = "Get the shopping cart by id that belongs to logged-in user")
    @GetMapping("/shopping-cart/{shoppingCartId}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartByIdAndLoggedUser(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String shoppingCartId){
        UserModel user = getUserFromToken(authToken);
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));

        if(user.getId() == shoppingCart.getBuyer().getId()){
            return ResponseEntity.ok().body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
        } else {
            throw new HttpException("Cart not found by id: " + shoppingCart.getId(),
                                    HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Get shopping cart in progress of logged-in user")
    @GetMapping("/shopping-cart/inprogress")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartInProgress(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken){
        ShoppingCart shoppingCart = getShoppingCartInProgressFromToken(authToken);
        return ResponseEntity.ok().body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }



    @Operation(summary = "Get all shopping carts in the application")
    @GetMapping("/admin/shopping-cart/all-shopping-carts")
    public ResponseEntity<List<ShoppingCartDTO>> allPurchases(){
        List<ShoppingCart> shoppingCarts = this.shoppingCartService.getAllShoppingCart();
        return ResponseEntity.ok().body(shoppingCarts.stream()
                                                 .map(this::convertShoppingCartEntityToShoppingCartDTO)
                                                 .toList());
    }


    @Operation(summary = "Add a product to the shopping cart in progress")
    @PutMapping("/shopping-cart/inprogress/add-product")
    public ResponseEntity<ShoppingCartDTO> addProduct(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @RequestBody NewProductInCartDTO product){
        ShoppingCart shoppingCart = getShoppingCartInProgressFromToken(authToken);
        ProductInCart productInCart = convertNewProductInCartDTOToProductInCartEntity(product);
        
        shoppingCart.addProduct(productInCart);
        this.productInCartService.saveProduct(productInCart);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }


    @Operation(summary = "Add a product one time to the shopping cart in progress")
    @PutMapping("/shopping-cart/inprogress/add-product-one-time/{productId}")
    public ResponseEntity<ShoppingCartDTO> addProductOneTime(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String productId){
        ShoppingCart shoppingCart = getShoppingCartInProgressFromToken(authToken);
        ProductInCart productInCart = this.productInCartService.getProductById(UUID.fromString(productId));
        
        shoppingCart.addProductOneTime(productInCart);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }

    @Operation(summary = "Remove a product to the shopping cart in progress")
    @PutMapping("/shopping-cart/inprogress/remove-product/{productId}")
    public ResponseEntity<ShoppingCartDTO> removeProduct(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String productId){
        ShoppingCart shoppingCart = getShoppingCartInProgressFromToken(authToken);
        ProductInCart productInCart = this.productInCartService.getProductById(UUID.fromString(productId));
        
        shoppingCart.removeProduct(productInCart);
        this.productInCartService.deleteProductById(productInCart.getId());
        if (shoppingCart.getCart().isEmpty()){
            this.shoppingCartService.deleteShoppingCartById(shoppingCart.getId());
        } else {
            this.shoppingCartService.updateShoppingCart(shoppingCart);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }


    @Operation(summary = "Subtract a product one time to the shopping cart in progress")
    @PutMapping("/shopping-cart/inprogress/subtract-product-one-time/{productId}")
    public ResponseEntity<ShoppingCartDTO> subtractProductOneTime(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String productId){
        ShoppingCart shoppingCart = getShoppingCartInProgressFromToken(authToken);
        ProductInCart productInCart = this.productInCartService.getProductById(UUID.fromString(productId));
        
        shoppingCart.subtractProductOneTime(productInCart);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }


    @Operation(summary = "Finish purchase, set the shopping cart in progress state to sold")
    @PutMapping("/shopping-cart/inprogress/finish-purchase")
    public ResponseEntity<ShoppingCartDTO> finishPurchase(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken){
        ShoppingCart shoppingCart = getShoppingCartInProgressFromToken(authToken);

        shoppingCart.setCartState(CartState.SOLD);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }

    
    @Operation(summary = "Delete a shopping cart by id")
    @DeleteMapping("/admin/shopping-cart/{shoppingCartId}/delete-shopping-cart")
    public ResponseEntity<String> deleteShoppingCart(@PathVariable String shoppingCartId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));

        this.shoppingCartService.deleteShoppingCartById(UUID.fromString(shoppingCartId));

        shoppingCart.getCart().forEach( element -> this.productInCartService.deleteProductById(element.getId()));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(shoppingCartId.toString());
    }


    @Operation(summary = "Delete a shopping cart in progress")
    @DeleteMapping("/shopping-cart/inprogress/delete-shopping-cart")
    public ResponseEntity<String> deleteShoppingCartInProgress(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken){
        ShoppingCart shoppingCart = getShoppingCartInProgressFromToken(authToken);

        this.shoppingCartService.deleteShoppingCartById(shoppingCart.getId());

        shoppingCart.getCart().forEach( element -> this.productInCartService.deleteProductById(element.getId()));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(shoppingCart.getId().toString());
    }


    @Operation(summary = "Get all purchased shopping carts by user")
    @GetMapping("/admin/shopping-cart/all-purchases/{userId}")
    public ResponseEntity<List<ShoppingCartDTO>> getAllPurchasesByUser(@PathVariable String userId){
        UserModel buyer = this.userService.getUserById(UUID.fromString(userId));
        List<ShoppingCart> purchasedCarts = this.shoppingCartService.getAllPurchasedShoppingCartsByUser(buyer);

        return ResponseEntity.ok().body(purchasedCarts.stream().map(this::convertShoppingCartEntityToShoppingCartDTO).toList());
    }

    @Operation(summary = "Get all purchased shopping carts of logged-in user")
    @GetMapping("/shopping-cart/all-purchases")
    public ResponseEntity<List<ShoppingCartDTO>> getAllPurchases(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken){
        UserModel buyer = getUserFromToken(authToken);
        List<ShoppingCart> purchasedCarts = this.shoppingCartService.getAllPurchasedShoppingCartsByUser(buyer);

        return ResponseEntity.ok().body(purchasedCarts.stream().map(this::convertShoppingCartEntityToShoppingCartDTO).toList());
    }

    
    private ShoppingCartDTO convertShoppingCartEntityToShoppingCartDTO(ShoppingCart shoppingCart){
        ShoppingCartDTO dto = new ShoppingCartDTO(  shoppingCart.getId(), 
                                                    shoppingCart.getTotalAmountPurchase(),
                                                    shoppingCart.getCart().stream().map(this::convertProductInCartEntityToProductInCartDTO).toList(), 
                                                    shoppingCart.getBuyer().getId(), 
                                                    shoppingCart.getCartState());
        return dto;
    }


    private ProductInCartDTO convertProductInCartEntityToProductInCartDTO(ProductInCart product){
        return new ProductInCartDTO(product.getId(),
                                    product.getMercadoLibreId(),
                                    product.getAmount(),
                                    product.getPicture(), 
                                    product.getLink(), 
                                    product.getTitle(), 
                                    product.getCategoryId(), 
                                    product.getPrice(), 
                                    product.getCondition());
    }
    
    private ProductInCart convertNewProductInCartDTOToProductInCartEntity(NewProductInCartDTO product){
        return new ProductInCart(   product.getLink(), 
                                    product.getTitle(), 
                                    product.getCategoryId(), 
                                    product.getPrice(), 
                                    product.getCondition(), 
                                    product.getMercadoLibreId(), 
                                    product.getAmount(), 
                                    product.getPicture());
    }

    private UserModel getUserFromToken(String authToken){
        return userService.getUserByEmail(jwtProvider.getUserNameFromJWT(jwtProvider.getTokenWithoutBearerSuffix(authToken)));
    }

    private ShoppingCart getShoppingCartInProgressFromToken(String authToken){
        return shoppingCartService.getShoppingCartInProgressByBuyer(getUserFromToken(authToken));
    }

}
