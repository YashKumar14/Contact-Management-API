package com.example.ContactManagementApi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * The ContactWithDuplicates class represents an entity for storing contact
 * information that may have duplicate entries. It contains fields for first
 * name, last name, email, phone number and address, along with validation rules
 * for each field.
 * 
 * This entity is mapped to a database table by the JPA framework using
 * the @Entity annotation. Fields are validated using Jakarta Bean Validation
 * annotations.
 */
@Entity
public class ContactWithDuplicates {

	/**
	 * The unique identifier for each contact entry. It is auto-generated by the
	 * database using the IDENTITY strategy.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * The first name of the contact. This field is mandatory and must contain only
	 * alphabetic characters.
	 */
	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabetic characters")
	private String firstName;

	/**
	 * The last name of the contact. This field is mandatory and must contain only
	 * alphabetic characters.
	 */
	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only alphabetic characters")
	private String lastName;

	/**
	 * The email address of the contact. This field is mandatory, must be in a valid
	 * email format, and is subject to duplication checks in the application logic.
	 */
	@NotBlank(message = "Email is required")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z]+\\.[A-Za-z]{2,}$", message = "Email format is invalid")
	private String email;

	/**
	 * The phone number of the contact. This field is mandatory, must follow a valid
	 * phone number format starting with a plus sign and country code.
	 */
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^\\+[0-9]{2}[0-9]{10}$", message = "Invalid phone number format. It should start with + and countrycode")
	private String phoneNumber;

	/**
	 * The address of the contact. This field is optional but must follow a valid
	 * format if provided.
	 */
	@Pattern(regexp = "^[A-Za-z0-9\\s,]*$", message = "Invalid address format")
	private String address;

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
