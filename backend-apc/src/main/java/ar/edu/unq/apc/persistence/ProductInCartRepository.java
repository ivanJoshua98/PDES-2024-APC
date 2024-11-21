package ar.edu.unq.apc.persistence;

import ar.edu.unq.apc.model.ProductInCart;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, UUID> {

}
