package com.example.ContactManagementApi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ContactManagementApi.Entity.Contact;
import com.example.ContactManagementApi.Service.ContactService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * The ContactController class manages the contact-related CRUD operations for
 * users and admins.
 * 
 * This controller provides endpoints for creating, retrieving, updating and
 * deleting contacts. The operations are secured using role-based access control
 * via Spring Security annotations.
 * 
 * Base path for all endpoints in this controller is /api/contacts.
 */
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

	// Injects the ContactService to handle business logic for contacts.
	@Autowired
	private ContactService contactService;

	
	/**
	 * Registers a new contact in the system. Accessible by users with either
	 * 'ADMIN' or 'USER' roles.
	 */
	@PostMapping("/register")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Allows both 'ADMIN' and 'USER' roles to create contacts.
	public ResponseEntity<Contact> createContact(@Validated @RequestBody Contact contact) {
		// Calls the service layer to create and return the new contact.
		Contact registerContact = contactService.createContact(contact);
		return ResponseEntity.ok(registerContact); // Returns a 200 OK response with the created contact.
	}

	
	/**
	 * Retrieves a list of all contacts. Only accessible by users with the 'ADMIN'
	 * role.
	 */
	@GetMapping("/retrieve")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<List<Contact>> getAllContacts() {
		// Calls the service layer to retrieve the list of all contacts.
		List<Contact> getContacts = contactService.getAllContacts();
		return ResponseEntity.ok(getContacts); // Returns a 200 OK response with the list of contacts.
	}

	
	/**
	 * Retrieves a specific contact by its ID. Only accessible by users with the
	 * 'ADMIN' role.
	 */
	@GetMapping("/retrieve/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
		// Calls the service layer to find the contact by ID, throws exception if not
		// found.
		Contact getContact = contactService.getContactById(id)
				.orElseThrow(() -> new RuntimeException("contact not found"));
		return ResponseEntity.ok(getContact); // Returns a 200 OK response with the found contact.
	}

	
	/**
	 * Updates an existing contact by its ID. Only accessible by users with the
	 * 'ADMIN' role.
	 */
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Validated @RequestBody Contact contact) {
		// Calls the service layer to update the contact by ID and return the updated
		// details.
		Contact updateContactById = contactService.updateContact(id, contact);
		return ResponseEntity.ok(updateContactById); // Returns a 200 OK response with the updated contact.
	}

	
	/**
	 * Deletes a contact by its ID. Only accessible by users with the 'ADMIN' role.
	 */
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
		// Calls the service layer to delete the contact by ID.
		contactService.deleteContact(id);
		return ResponseEntity.noContent().build(); // Returns a 204 No Content response on successful deletion.
		// return ResponseEntity.ok("Contact Deleted successfully");
	}

}
