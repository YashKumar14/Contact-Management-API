package com.example.ContactManagementApi.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ContactManagementApi.Entity.ContactWithDuplicates;

/**
 * The ContactWithDuplicatesRepository interface provides the mechanism for CRUD
 * operations on the ContactWithDuplicates entity. It extends JpaRepository to
 * leverage its built-in methods.
 */
@Repository
public interface ContactWithDuplicatesRepository extends JpaRepository<ContactWithDuplicates, Long> {

	// Finds a ContactWithDuplicates by its email address.
	Optional<ContactWithDuplicates> findByEmail(String email);

	// Finds a ContactWithDuplicates by its phone number.
	Optional<ContactWithDuplicates> findByPhoneNumber(String PhoneNo);

}
