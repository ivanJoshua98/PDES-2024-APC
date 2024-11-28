package ar.edu.unq.apc.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.model.UserWithMostPurchases;

@Repository
public interface UserRepository  extends JpaRepository<UserModel, UUID> {
    
    Optional<UserModel> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String userName);

    Optional<UserModel> findByUserName(String userName);

    @Query(nativeQuery = true, value = "SELECT id, email, user_name, purchases_count FROM user_model " + 
            "INNER JOIN (SELECT buyer_id, COUNT(buyer_id) AS purchases_count " + 
                        "FROM shopping_cart " +
                        "WHERE cart_state = ?1 " +
                        "GROUP BY buyer_id HAVING COUNT (buyer_id)>=1 " +
                        "ORDER BY purchases_count DESC " +
                        "LIMIT 5) AS top_five " + 
            "ON user_model.id=top_five.buyer_id " +
            "ORDER BY purchases_count DESC")
    List<UserWithMostPurchases> findUsersWithMostPurchases(CartState state);


}
