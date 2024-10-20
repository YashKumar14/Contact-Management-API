package com.example.ContactManagementApi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ContactManagementApi.Entity.ContactWithDuplicates;
import com.example.ContactManagementApi.Service.ContactWithDuplicatesService;

@RestController
@RequestMapping("/api/duplicateContacts")
public class ContactWithDuplicatesController {

	// Injects the service layer to handle contact operations with duplicates.
	@Autowired
	private ContactWithDuplicatesService contactWithDuplicatesService;

	
	/**
	 * Registers a new contact. This endpoint allows users with either 'ADMIN' or
	 * 'USER' roles to create a new contact.
	 */
	@PostMapping("/register")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Authorizes both 'ADMIN' and 'USER' roles.
	public ResponseEntity<ContactWithDuplicates> createContact(@Validated @RequestBody ContactWithDuplicates contact) {
		// Calls the service layer to create a new contact and return it.
		ContactWithDuplicates registerContact = contactWithDuplicatesService.createContact(contact);
		return ResponseEntity.ok(registerContact); // Returns a 200 OK response with the registered contact.
	}

	
	/**
	 * Retrieves a list of all contacts. Only users with the 'ADMIN' role can access
	 * this endpoint.
	 */
	@GetMapping("/retrieve")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<List<ContactWithDuplicates>> getAllContacts() {
		// Calls the service layer to get the list of all contacts.
		List<ContactWithDuplicates> getContacts = contactWithDuplicatesService.getAllContacts();
		return ResponseEntity.ok(getContacts); // Returns a 200 OK response with the registered contact.
	}

	
	/**
	 * Retrieves a contact by its ID. Only users with the 'ADMIN' role can access
	 * this endpoint.
	 */
	@GetMapping("/retrieve/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<ContactWithDuplicates> getContactById(@PathVariable Long id) {
		// Calls the service layer to retrieve a contact by ID, throws an exception if
		// not found.
		ContactWithDuplicates getContact = contactWithDuplicatesService.getContactById(id)
				.orElseThrow(() -> new RuntimeException("contact not found"));
		return ResponseEntity.ok(getContact); // Returns a 200 OK response with the registered contact.
	}

	
	/**
	 * Updates an existing contact by its ID. Only users with the 'ADMIN' role can
	 * access this endpoint.
	 */
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<ContactWithDuplicates> updateContact(@PathVariable Long id,
			@Validated @RequestBody ContactWithDuplicates contact) {
		// Calls the service layer to update the contact details and return the updated
		// entity.
		ContactWithDuplicates updateContactById = contactWithDuplicatesService.updateContact(id, contact);
		return ResponseEntity.ok(updateContactById); // Returns a 200 OK response with the registered contact.
	}

	
	/**
	 * Deletes a contact by its ID. Only users with the 'ADMIN' role can access this
	 * endpoint.
	 */
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
		// Calls the service layer to delete the contact by ID.
		contactWithDuplicatesService.deleteContact(id);
		return ResponseEntity.noContent().build(); // Returns a 204 No Content response indicating successful deletion.
		// return ResponseEntity.ok("Deleted successfully");
	}

	
	/**
	 * Merges duplicate contacts in the system. Only users with the 'ADMIN' role can
	 * access this endpoint.
	 */
	@PostMapping("/mergeDuplicates")
	@PreAuthorize("hasRole('ADMIN')") // Restricts access to 'ADMIN' role only.
	public ResponseEntity<String> mergeDuplicates() {
		// Calls the service layer to merge duplicate contacts and return a response
		// message.
		String responseMessage = contactWithDuplicatesService.mergeDuplicateContacts();
		return ResponseEntity.ok(responseMessage); // Returns a 200 OK response with the merge result message.
	}

}
