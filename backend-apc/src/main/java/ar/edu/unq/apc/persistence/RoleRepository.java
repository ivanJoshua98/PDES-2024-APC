package ar.edu.unq.apc.persistence;

import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{

    Optional<Role> findByName(String name);

    Boolean existsByName(String name);
    
}
