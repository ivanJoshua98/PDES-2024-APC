package ar.edu.unq.apc.webService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.model.Purchase;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.service.MercadoLibreProxyService;
import ar.edu.unq.apc.service.ProductService;
import ar.edu.unq.apc.service.PurchaseService;
import ar.edu.unq.apc.service.UserService;
import ar.edu.unq.apc.webService.dto.PurchaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Purchase service", description = "Manage purchases of the application")
@RequestMapping("/apc/purchase")
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MercadoLibreProxyService mercadoLibre;

    @Autowired
    private ProductService productService;
    

    @Operation(summary = "Buy products in the application")
    @PostMapping("/buy")
    public ResponseEntity<PurchaseDTO> buy(@RequestBody PurchaseDTO purchase){
        UserModel buyer = this.userService.getUserById(purchase.getBuyerId());
        //List<Product> soldProducts = this.mercadoLibre.getProductsByIds(purchase.getSoldProductsIds());
        List<Product> soldProducts = purchase.getSoldProductsIds().stream().map(id -> this.mercadoLibre.getProductById(id)).toList();
        Purchase newPurchase = new Purchase(purchase.getSalePrice(), soldProducts, buyer);

        soldProducts.stream().distinct().collect(Collectors.toList()).forEach(
            product -> this.productService.saveProduct(product)
        );

        this.purchaseService.savePurchase(newPurchase);

        return ResponseEntity.status(HttpStatus.CREATED).body(purchase);
    }

    @Operation(summary = "Get all purchases in the application")
    @GetMapping("/allPurchases")
    public ResponseEntity<List<PurchaseDTO>> allPurchases(){
        List<Purchase> purchases = this.purchaseService.getAllPurchases();
        return ResponseEntity.ok().body(purchases.stream()
                                                 .map(this::convertPurchaseEntityToPurchaseDTO)
                                                 .toList());
    }
    
    private PurchaseDTO convertPurchaseEntityToPurchaseDTO(Purchase purchase){
        return new PurchaseDTO( purchase.getSalePrice(),
                                purchase.getSoldProducts().stream().map(product -> product.getId()).collect(Collectors.toList()),
                                purchase.getBuyer().getId());
    }

    
}
