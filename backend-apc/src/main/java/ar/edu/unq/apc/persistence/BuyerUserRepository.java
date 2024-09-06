package ar.edu.unq.apc.persistence;

import ar.edu.unq.apc.model.BuyerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerUserRepository extends JpaRepository<BuyerUser, String> {
}
