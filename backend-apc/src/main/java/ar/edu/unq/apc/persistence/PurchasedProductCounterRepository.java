package ar.edu.unq.apc.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.PurchasedProductCounter;

@Repository
public interface PurchasedProductCounterRepository extends JpaRepository<PurchasedProductCounter, String> {
    
}
