package ar.edu.unq.apc.persistence;

import ar.edu.unq.apc.model.BuyerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerUserRepository extends JpaRepository<BuyerUser, Long> {
    Optional<BuyerUser> findByEmail(String email);
}
