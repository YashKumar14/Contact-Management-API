package com.example.ContactManagementApi.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ContactManagementApi.Entity.Contact;

/**
 * The ContactRepository interface provides the mechanism for CRUD operations on
 * the Contact entity. It extends JpaRepository to leverage its built-in
 * methods.
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	// Finds a contact by its email address.
	Optional<Contact> findByEmail(String email);

	// Finds a Contact by its phone number.
	Optional<Contact> findByPhoneNumber(String PhoneNo);

}
