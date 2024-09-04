package ar.edu.unq.apc.persistence;

import ar.edu.unq.apc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<User, String> {

}
