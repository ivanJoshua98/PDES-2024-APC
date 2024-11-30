package ar.edu.unq.apc.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.FavoriteProductInTop;
import ar.edu.unq.apc.model.PurchasedProductInTop;
import ar.edu.unq.apc.model.UserWithMostPurchases;
import ar.edu.unq.apc.service.SystemReportService;
import ar.edu.unq.apc.webService.dto.FavoriteProductInTopDTO;
import ar.edu.unq.apc.webService.dto.PurchasedProductInTopDTO;
import ar.edu.unq.apc.webService.dto.UserWithMostPurchasesDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "System reports services", description = "Manage reports of the application")
@RestController
@RequestMapping("/apc/admin")
public class SystemReportController {

    @Autowired
    private SystemReportService systemReportService;

    
    @Operation(summary = "Get top five users with most purchases by shopping carts")
    @GetMapping("/reports/users-with-most-purchases-by-shopping-carts")
    public ResponseEntity<List<UserWithMostPurchasesDTO>> getTopFiveUsersWithMostPurchases(){
        List<UserWithMostPurchases> topFive = this.systemReportService.getUsersWithMostPurchases();

        return ResponseEntity.ok().body(topFive.stream().map(this::convertUserEntityToUserDTO).toList());
    }

    @Operation(summary = "Get top five users with most purchases by products amount")
    @GetMapping("/reports/users-with-most-purchases-by-products-amount")
    public ResponseEntity<List<UserWithMostPurchasesDTO>> getTopFiveUsersWithMostPurchasedProducts(){
        List<UserWithMostPurchases> topFive = this.systemReportService.getUsersWithMostPurchasedProducts();

        return ResponseEntity.ok().body(topFive.stream().map(this::convertUserEntityToUserDTO).toList());
    }

    @Operation(summary = "Get top five products most times chosen favorite")
    @GetMapping("/reports/products-most-times-chosen-favorite")
    public ResponseEntity<List<FavoriteProductInTopDTO>> getTopFiveFavoriteProducts(){
        List<FavoriteProductInTop> topFive = this.systemReportService.getFavoriteProductsTopFive();

        return ResponseEntity.ok().body(topFive.stream().map(this::convertProductEntityToProductDTO).toList());
        
    }

    @Operation(summary = "Get top five most purchased products")
    @GetMapping("/reports/most-purchased-products")
    public ResponseEntity<List<PurchasedProductInTopDTO>> getMostPurchasedProducts(){
        List<PurchasedProductInTop> topFive = this.systemReportService.getMostPurchasedProducts();

        return ResponseEntity.ok().body(topFive.stream().map(this::convertPurchasedEntityToPurchasedDTO).toList());
        
    }

    private UserWithMostPurchasesDTO convertUserEntityToUserDTO(UserWithMostPurchases user){
        return new UserWithMostPurchasesDTO(user.getId(), 
                                            user.getUserName(), 
                                            user.getEmail(), 
                                            user.getPurchasesCount());
    }

    private FavoriteProductInTopDTO convertProductEntityToProductDTO(FavoriteProductInTop product){
        return new FavoriteProductInTopDTO(product.getProductId(), product.getTimesChosenFavorite());
    }

    private PurchasedProductInTopDTO convertPurchasedEntityToPurchasedDTO(PurchasedProductInTop product){
        return new PurchasedProductInTopDTO(product.getMercadoLibreId(), product.getPurchasesCount());
    }
    
}
