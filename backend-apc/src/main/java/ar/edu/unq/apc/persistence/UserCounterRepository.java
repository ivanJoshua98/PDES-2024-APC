package ar.edu.unq.apc.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.UserCounter;

public interface UserCounterRepository extends JpaRepository<UserCounter, UUID> {


    @Query(nativeQuery = true, value = 
            "SELECT id AS user_id, email, user_name, purchase_counter FROM user_model " + 
            "INNER JOIN (SELECT buyer_id, COUNT(buyer_id) AS purchase_counter " + 
                        "FROM shopping_cart " +
                        "WHERE cart_state = ?1 " +
                        "GROUP BY buyer_id HAVING COUNT (buyer_id)>=1 " +
                        "ORDER BY purchase_counter DESC " +
                        "LIMIT ?2) AS ranking " + 
            "ON user_model.id=ranking.buyer_id " +
            "ORDER BY purchase_counter DESC")
    List<UserCounter> getUsersWithMostPurchases(CartState state, Integer limit);


    @Query(nativeQuery = true, value = 
            "SELECT user_counter.user_id, user_counter.email, user_counter.user_name, purchase_counter FROM user_model " + 
            "INNER JOIN user_counter " +
            "ON user_model.id=user_counter.user_id " +
            "ORDER BY purchase_counter DESC "+
            "LIMIT ?1")
    List<UserCounter> getUsersWithMostPurchasesByProducts(Integer limit);

    
}
