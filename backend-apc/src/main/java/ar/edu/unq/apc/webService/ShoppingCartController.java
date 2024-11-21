package ar.edu.unq.apc.webService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.ProductInCart;
import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.service.ProductInCartService;
import ar.edu.unq.apc.service.ShoppingCartService;
import ar.edu.unq.apc.service.UserService;
import ar.edu.unq.apc.webService.dto.NewProductInCartDTO;
import ar.edu.unq.apc.webService.dto.NewShoppingCartDTO;
import ar.edu.unq.apc.webService.dto.ProductInCartDTO;
import ar.edu.unq.apc.webService.dto.ShoppingCartDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Purchase service", description = "Manage purchases of the application")
@RequestMapping("/apc/shoppingCart")
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductInCartService productInCartService;
    

    @Operation(summary = "Create a new shopping cart in the application")
    @PostMapping("/newShoppingCart")
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
    @GetMapping("/{shoppingCartId}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable String shoppingCartId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));

        return ResponseEntity.ok().body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }



    @Operation(summary = "Get shopping cart in progress by user")
    @GetMapping("inprogress/{userId}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartInProgress(@PathVariable String userId){
        UserModel user = this.userService.getUserById(UUID.fromString(userId));
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartInProgressByBuyer(user);

        return ResponseEntity.ok().body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }



    @Operation(summary = "Get all shopping carts in the application")
    @GetMapping("/allShoppingCarts")
    public ResponseEntity<List<ShoppingCartDTO>> allPurchases(){
        List<ShoppingCart> shoppingCarts = this.shoppingCartService.getAllShoppingCart();
        return ResponseEntity.ok().body(shoppingCarts.stream()
                                                 .map(this::convertShoppingCartEntityToShoppingCartDTO)
                                                 .toList());
    }


    @Operation(summary = "Add a product in shopping cart")
    @PutMapping("/{shoppingCartId}/addProduct")
    public ResponseEntity<ShoppingCartDTO> addProduct(@PathVariable String shoppingCartId, @RequestBody NewProductInCartDTO product){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));
        ProductInCart productInCart = convertNewProductInCartDTOToProductInCartEntity(product);
        
        shoppingCart.addProduct(productInCart);
        this.productInCartService.saveProduct(productInCart);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }


    @Operation(summary = "Add a product one time in shopping cart")
    @PutMapping("/{shoppingCartId}/addProductOneTime/{productId}")
    public ResponseEntity<ShoppingCartDTO> addProductOneTime(@PathVariable String shoppingCartId, @PathVariable String productId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));
        ProductInCart productInCart = this.productInCartService.getProductById(UUID.fromString(productId));
        
        shoppingCart.addProductOneTime(productInCart);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }

    @Operation(summary = "Remove a product in shopping cart")
    @PutMapping("/{shoppingCartId}/removeProduct/{productId}")
    public ResponseEntity<ShoppingCartDTO> removeProduct(@PathVariable String shoppingCartId, @PathVariable String productId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));
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


    @Operation(summary = "Subtract a product one time in shopping cart")
    @PutMapping("/{shoppingCartId}/subtractProductOneTime/{productId}")
    public ResponseEntity<ShoppingCartDTO> subtractProductOneTime(@PathVariable String shoppingCartId, @PathVariable String productId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));
        ProductInCart productInCart = this.productInCartService.getProductById(UUID.fromString(productId));
        
        shoppingCart.subtractProductOneTime(productInCart);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }


    @Operation(summary = "Finish purchase, set the shopping cart state to sold")
    @PutMapping("/{shoppinCartId}/finishPurchase")
    public ResponseEntity<ShoppingCartDTO> finishPurchase(@PathVariable String shoppinCartId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppinCartId));

        shoppingCart.setCartState(CartState.SOLD);
        this.shoppingCartService.updateShoppingCart(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertShoppingCartEntityToShoppingCartDTO(shoppingCart));
    }

    
    @Operation(summary = "Delete a shopping cart")
    @DeleteMapping("/{shoppingCartId}/deleteShoppingCart")
    public ResponseEntity<String> deleteShoppingCart(@PathVariable String shoppingCartId){
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCartById(UUID.fromString(shoppingCartId));

        this.shoppingCartService.deleteShoppingCartById(UUID.fromString(shoppingCartId));

        shoppingCart.getCart().forEach( element -> this.productInCartService.deleteProductById(element.getId()));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(shoppingCartId.toString());
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

}
