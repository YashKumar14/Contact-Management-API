package com.example.ContactManagementApi.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ContactManagementApi.Entity.User;

/**
 * The UserRepository interface provides the mechanism for CRUD operations on
 * the User entity. It extends JpaRepository to utilize built-in JPA
 * functionalities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// Retrieves a User by their username.
	Optional<User> findByUsername(String username);

}
