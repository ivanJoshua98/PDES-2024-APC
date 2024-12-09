package ar.edu.unq.apc.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.ProductCounter;
import ar.edu.unq.apc.model.UserCounter;
import ar.edu.unq.apc.service.SystemReportService;
import ar.edu.unq.apc.webService.dto.ProductFromTopDTO;
import ar.edu.unq.apc.webService.dto.UserFromTopDTO;
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
    public ResponseEntity<List<UserFromTopDTO>> getTopFiveUsersWithMostPurchases(){
        List<UserCounter> topFive = systemReportService.getUsersWithMostPurchases(5);

        return ResponseEntity.ok().body(topFive.stream().map(this::convertUserEntityToUserDTO).toList());
    }

    @Operation(summary = "Get top five users with most purchases by products amount")
    @GetMapping("/reports/users-with-most-purchases-by-products-amount")
    public ResponseEntity<List<UserFromTopDTO>> getTopFiveUsersWithMostPurchasedProducts(){
        List<UserCounter> topFive = systemReportService.getUsersWithMostPurchasesByProducts(5);

        return ResponseEntity.ok().body(topFive.stream().map(this::convertUserEntityToUserDTO).toList());
    }

    @Operation(summary = "Get top five products most times chosen favorite")
    @GetMapping("/reports/products-most-times-chosen-favorite")
    public ResponseEntity<List<ProductFromTopDTO>> getTopFiveFavoriteProducts(){
        List<ProductCounter> topFive = this.systemReportService.getMostFavorites(5);
        //COMPLETAAAAR
        return ResponseEntity.ok().body(topFive.stream().map(this::convertProductEntityToProductDTO).toList());
        
    }

    @Operation(summary = "Get top five most purchased products")
    @GetMapping("/reports/most-purchased-products")
    public ResponseEntity<List<ProductFromTopDTO>> getMostPurchasedProducts(){
        List<ProductCounter> topFive = systemReportService.getMostPurchasedProducts(5);

        return ResponseEntity.ok().body(topFive.stream().map(this::convertProductEntityToProductDTO).toList());
        
    }

    private UserFromTopDTO convertUserEntityToUserDTO(UserCounter counter){
        return new UserFromTopDTO(counter.getUserId(), 
                                            counter.getUserName(), 
                                            counter.getEmail(), 
                                            counter.getPurchaseCounter());
    }

    private ProductFromTopDTO convertProductEntityToProductDTO(ProductCounter counter){
        return new ProductFromTopDTO(   counter.getProductId(),
                                        counter.getPurchaseCounter(),
                                        counter.getTitle(),
                                        counter.getPicture(),
                                        counter.getFavoriteCounter());
    }
    
}
