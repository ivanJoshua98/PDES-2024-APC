package ar.edu.unq.apc.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.Purchase;
import ar.edu.unq.apc.model.UserModel;

@Repository
public interface PurcharseRepository extends JpaRepository<Purchase, UUID> {

    Optional<Purchase> findByBuyer(UserModel buyer);
    
}
