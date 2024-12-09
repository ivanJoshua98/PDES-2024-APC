package ar.edu.unq.apc.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unq.apc.model.ProductCounter;

public interface ProductCounterRepository extends JpaRepository<ProductCounter, String> {


    @Query(nativeQuery = true, value = 
        "SELECT * FROM product_counter " + 
        "ORDER BY purchase_counter DESC " + 
        "LIMIT ?1")
    List<ProductCounter> getMostPurchasedProducts(Integer limit);


    @Query(nativeQuery = true, value = 
        "SELECT * FROM product_counter " + 
        "ORDER BY favorite_counter DESC " + 
        "LIMIT ?1")
    List<ProductCounter> getMostFavorites(Integer limit);
    
}
