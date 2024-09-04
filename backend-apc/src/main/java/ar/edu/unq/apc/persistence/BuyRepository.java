package ar.edu.unq.apc.persistence;

import ar.edu.unq.apc.model.Buy;
import ar.edu.unq.apc.model.BuyerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyRepository extends JpaRepository<Buy, Long> {
    List<Buy> findByBuyer(BuyerUser buyer);

}
