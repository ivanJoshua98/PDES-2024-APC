package ar.edu.unq.apc.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserModel;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID>{
    
    Optional<ShoppingCart> findByBuyer(UserModel buyer);

    Optional<ShoppingCart> findByBuyerAndCartState(UserModel buyer, CartState inprogress);
    
}
