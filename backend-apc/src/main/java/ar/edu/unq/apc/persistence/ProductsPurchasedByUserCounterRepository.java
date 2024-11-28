package ar.edu.unq.apc.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.ProductsPurchasedByUserCounter;

@Repository
public interface ProductsPurchasedByUserCounterRepository extends JpaRepository<ProductsPurchasedByUserCounter, String>{
    
}
