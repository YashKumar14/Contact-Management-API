package com.example.ContactManagementApi.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ContactManagementApi.Entity.Contact;
import com.example.ContactManagementApi.Repository.ContactRepository;

/**
 * ContactService is a service class that provides business logic for managing
 * contacts in the application. It interacts with the ContactRepository to
 * perform CRUD (Create, Read, Update, Delete) operations on contact entities.
 * This class is annotated with @Service, indicating that it is a service
 * component in the Spring framework.
 */
@Service
public class ContactService {

	// Automatically injects the ContactRepository bean
	@Autowired
	private ContactRepository contactRepository;

	// Method to create a new contact in the repository
	public Contact createContact(Contact contact) {
		return contactRepository.save(contact);
	}

	// Method to retrieve all contacts from the repository
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	// Method to retrieve a specific contact by its ID
	public Optional<Contact> getContactById(Long id) {
		return contactRepository.findById(id);
	}

	// Method to update an existing contact's details
	public Contact updateContact(Long id, Contact contactDetails) {
		
		// Finds the contact by ID; throws an exception if not found
		Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("contact not found"));

		// Updates the contact's fields with new values from the provided contactDetails object
		contact.setFirstName(contactDetails.getFirstName());
		contact.setLastName(contactDetails.getLastName());
		contact.setEmail(contactDetails.getEmail());
		contact.setPhoneNumber(contactDetails.getPhoneNumber());
		contact.setAddress(contactDetails.getAddress());

		// Saves the updated contact back to the repository and returns the updated entity
		return contactRepository.save(contact);
	}

	// Method to delete a contact by its ID
	public void deleteContact(Long id) {
		
		// Deletes the contact with the specified ID from the database
		contactRepository.deleteById(id);
	}

}
