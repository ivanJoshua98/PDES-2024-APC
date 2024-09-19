package ar.edu.unq.apc.persistence;

import ar.edu.unq.apc.model.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyRepository extends JpaRepository<Buy, Long> {
    List<Buy> findByBuyer_Id(Long userId);

}
