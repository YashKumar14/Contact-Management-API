package com.example.ContactManagementApi.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ContactManagementApi.Entity.ContactWithDuplicates;
import com.example.ContactManagementApi.Repository.ContactWithDuplicatesRepository;

/**
 * ContactWithDuplicatesService is a service class that provides business logic
 * for managing contacts that may have duplicates in the application. It
 * interacts with the ContactWithDuplicatesRepository to perform CRUD (Create,
 * Read, Update, Delete) operations on contact entities, as well as merging
 * duplicate contacts. This class is annotated with @Service, indicating that it
 * is a service component in the Spring framework.
 */
@Service
public class ContactWithDuplicatesService {

	// Injecting the ContactWithDuplicatesRepository to perform database operations.
	@Autowired
	private ContactWithDuplicatesRepository contactWithDuplicatesRepository;

	// Method to create a new contact and saves it to the repository.
	public ContactWithDuplicates createContact(ContactWithDuplicates contact) {
		return contactWithDuplicatesRepository.save(contact);
	}

	// Method to retrieve all contacts from the repository.
	public List<ContactWithDuplicates> getAllContacts() {
		return contactWithDuplicatesRepository.findAll();
	}

	// Method to retrieve a specific contact by its ID.
	public Optional<ContactWithDuplicates> getContactById(Long id) {
		return contactWithDuplicatesRepository.findById(id);
	}

	// Method to update the details of an existing contact.
	public ContactWithDuplicates updateContact(Long id, ContactWithDuplicates contactDetails) {

		// Finds the contact by ID; throws an exception if not found
		ContactWithDuplicates contact = contactWithDuplicatesRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("contact not found"));

		// Updates the contact's fields with new values from the provided contactDetails
		// object
		contact.setFirstName(contactDetails.getFirstName());
		contact.setLastName(contactDetails.getLastName());
		contact.setEmail(contactDetails.getEmail());
		contact.setPhoneNumber(contactDetails.getPhoneNumber());
		contact.setAddress(contactDetails.getAddress());

		// Saves the updated contact back to the repository and returns the updated
		// entity
		return contactWithDuplicatesRepository.save(contact);
	}

	// Method to delete a contact by its ID
	public void deleteContact(Long id) {

		// Deletes the contact with the specified ID from the database
		contactWithDuplicatesRepository.deleteById(id);
	}

	/**
	 * Merges duplicate contacts in the repository based on email and phone number.
	 * This method combines the details of contacts that share the same email or
	 * phone number, preserving the most recent information. It deletes the
	 * duplicate entries after merging.
	 *
	 * @return a success message indicating that contacts have been merged and
	 *         duplicates deleted
	 */
	public String mergeDuplicateContacts() {

		// Fetch all contacts from the repository
		List<ContactWithDuplicates> allContacts = contactWithDuplicatesRepository.findAll();

		// Map to keep track of merged contacts by their email and phone numbers
		Map<String, ContactWithDuplicates> mergedContacts = new HashMap<>();

		// Set to keep track of IDs of contacts that need to be deleted
		Set<Long> idsToDelete = new HashSet<>();

		// Iterate through all contacts to identify duplicates
		for (ContactWithDuplicates contact : allContacts) {
			// Generate keys based on email and phone number for merging
			String emailKey = contact.getEmail() != null ? contact.getEmail() : "";
			String phoneKey = contact.getPhoneNumber() != null ? contact.getPhoneNumber() : "";

			// Check if a contact already exists with the same email or phone number
			ContactWithDuplicates existingContactByEmail = mergedContacts.get(emailKey);
			ContactWithDuplicates existingContactByPhone = mergedContacts.get(phoneKey);

			if (existingContactByEmail != null || existingContactByPhone != null) {
				// If a duplicate is found, choose one contact to keep (first found)
				ContactWithDuplicates existingContact = existingContactByEmail != null ? existingContactByEmail
						: existingContactByPhone;

				// Update the existing contact with non-null values from the current contact
				if (contact.getFirstName() != null) {
					existingContact.setFirstName(contact.getFirstName());
				}
				if (contact.getLastName() != null) {
					existingContact.setLastName(contact.getLastName());
				}
				if (contact.getPhoneNumber() != null) {
					existingContact.setPhoneNumber(contact.getPhoneNumber());
				}
				if (contact.getEmail() != null) {
					existingContact.setEmail(contact.getEmail());
				}
				if (contact.getAddress() != null) {
					existingContact.setAddress(contact.getAddress());
				}

				// Mark the current contact's ID for deletion
				idsToDelete.add(contact.getId());
			} else {
				// If no duplicate is found, add the contact to the merged map
				if (!emailKey.isEmpty()) {
					mergedContacts.put(emailKey, contact);
				}
				if (!phoneKey.isEmpty()) {
					mergedContacts.put(phoneKey, contact);
				}
			}
		}

		// Save all merged contacts back to the repository
		contactWithDuplicatesRepository.saveAll(mergedContacts.values());

		// Delete all marked duplicate contacts from the repository
		if (!idsToDelete.isEmpty()) {
			contactWithDuplicatesRepository.deleteAllById(idsToDelete);
		}

		// Return a success message after merging and deletion
		return "Contacts merged and duplicates deleted successfully.";
	}

}
