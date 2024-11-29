package ar.edu.unq.apc.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.PurchasedProductCounter;
import ar.edu.unq.apc.model.PurchasedProductInTop;

@Repository
public interface PurchasedProductCounterRepository extends JpaRepository<PurchasedProductCounter, String> {


    @Query(nativeQuery = true, value = 
        "SELECT * FROM purchased_product_counter " + 
        "ORDER BY purchases_count DESC " + 
        "LIMIT 5")
    List<PurchasedProductInTop> getMostPurchasedProducts();
    
}
