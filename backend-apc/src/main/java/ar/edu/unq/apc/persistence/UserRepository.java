package ar.edu.unq.apc.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.apc.model.UserModel;

@Repository
public interface UserRepository  extends JpaRepository<UserModel, UUID> {
    
    Optional<UserModel> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String userName);


}
