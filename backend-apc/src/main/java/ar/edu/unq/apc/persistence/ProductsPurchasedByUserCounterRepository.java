package ar.edu.unq.apc.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.ProductsPurchasedByUserCounter;
import ar.edu.unq.apc.model.UserWithMostPurchases;

@Repository
public interface ProductsPurchasedByUserCounterRepository extends JpaRepository<ProductsPurchasedByUserCounter, UUID>{

    @Query(nativeQuery = true, value = 
            "SELECT id, email, user_name, products_purchases_count AS purchases_count FROM user_model " + 
            "INNER JOIN  products_purchased_by_user_counter " +
            "ON user_model.id=products_purchased_by_user_counter.user_id " +
            "ORDER BY products_purchases_count DESC "+
            "LIMIT 5")
    List<UserWithMostPurchases> getUsersWithMostPurchasedProducts();
    
}
