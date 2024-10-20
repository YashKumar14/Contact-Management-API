package com.example.ContactManagementApi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ContactManagementApi.Entity.Role;
import com.example.ContactManagementApi.Enum.RoleEnum;

/**
 * The RoleRepository interface provides the mechanism for CRUD operations on
 * the Role entity. It extends JpaRepository to leverage its built-in methods.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	// Finds a Role by its name.
	Role findByName(RoleEnum name);

}
