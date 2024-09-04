package ar.edu.unq.apc.persistence;

import ar.edu.unq.apc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
