# Contact Management API
A Contact Management API is a programming interface that allows developers to integrate, manage, and manipulate contact-related data within applications. This kind of API typically provides endpoints for creating, reading, updating and deleting (CRUD) contact records, which may include information such as names, phone numbers, email addresses and other relevant details.

This repository showcases a project that implements a Contact Management API using Spring Boot 3 and Spring Security 6 with JSON Web Tokens (JWT) for secure authentication and Role-Based Access Control (RBAC). The project provides fine-grained control over user permissions, enabling different access levels (e.g., admin and user roles) for managing contacts, securing endpoints and performing role-specific operations.
## Project Overview
The project includes the following functionalities:
- **User Authentication**: Secure user registration and login mechanisms with JWT for token-based authentication.
- **Contact Management**: Create, read, update and delete (CRUD) operations for managing contact information.
- **Field Validation**: Ensures that contact details meet specific criteria (e.g., non-empty fields, valid formats,unique) before processing requests.
- **Contact Merging**: Automatically merges contacts based on matching email addresses or phone numbers to eliminate duplicates, ensuring a clean contact list.
- **Role-Based Access Control**: Differentiate between user roles (e.g., ROLE_USER and ROLE_ADMIN) to restrict access to certain endpoints.
- **Error Handling**: Graceful handling of exceptions and validation errors to provide meaningful feedback to users.
- **API Documentation**: Comprehensive API documentation detailing endpoints, request/response formats and usage examples.
- **Testing**: Integration with Postman for API testing and validation of functionalities.

## Technologies Used
- **Java**: The primary programming language used for developing the API.
- **Spring Boot**: A powerful framework for building Java applications quickly and efficiently. It simplifies the setup and configuration process with its auto-configuration feature.
- **Spring Security**: Provides a robust security framework for authentication and authorization, ensuring that only authorized users can access protected resources.
- **MySQL**: A popular relational database management system used to store application data. It is chosen for its reliability and performance, making it suitable for managing user information and application state.
- **Hibernate**: An ORM framework for seamless database interactions.
- **Maven**: For dependency management and project build.
- **JWT (JSON Web Tokens)**: Used for stateless authentication, allowing secure communication between the client and server without maintaining session state on the server side.
- **Spring Tool Suite (STS)**: The IDE used for developing and managing the Spring Boot application.
- **Postman**: A powerful tool used for API testing. It allows developers to easily send requests to the application's endpoints and analyze the responses, making it an invaluable resource during development and debugging.
- **Git**: For version control and collaboration.

## Setup Instructions
### Prerequisites
- Ensure you have Java JDK (version 11 or above) installed.
- Install MySQL and set up a database named contact_management.
- Install Postman for testing the API endpoints.
- Install Git to clone the repository and manage versioin control.
- Install Maven for dependency management and project builds, even though STS automates some aspects of this.

### Steps
- Clone the repository: git clone https://github.com/YashKumar14/Contact-Management-API.git
- Open the project in the Spring Tool Suite.
- Make sure necessary dependencies are provided in pom.xml.
- Set up your database using MySQL and named contact_management.
- Update application.properties file which is used to store configuration properties for the application such as MySQL database,Hibernate configuration and JWT security configuration.
- Build and run the application.
- Open Postman and use the provided API endpoints to test the application.

## API Documentation
### Base URL 
- Authentication  	- http://localhost:8080/api

Below endpoints require JWT-based authentication. Obtain a JWT token by sending a POST request to the /api/auth/login/admin or /api/auth/login/user endpoints. 
- User Requests		- http://localhost:8080/users
- Contact Requests 	- http://localhost:8080/api/contacts
- Merged Contacts	- http://localhost:8080/api/duplicateContacts

### Authentication

**Endpoint**|**Method**|**Description**
:-----|:-----:|:-----|
|`/api/auth/signup/admin`|POST|Admin Registration|
|`/api/auth/signup/user`|POST|User Registration|
|`/api/auth/login/admin`|POST|Admin Login and Token Generation|
|`/api/auth/login/user`|POST|User Login and Token Generation|

#### User Requests

|Endpoint|Method|Description|
|:---|:---:|:---|
|`/users/current`|GET|User Registration|
|`/users/all`|GET|Admin Login and Token Generation|
|`/users/{id}`| PUT|Admin Registration|
|`/users/{id}`|DELETE|User Registration|

#### Contact Requests

|Endpoint|Method|Description|
|:---|:---|:---|
|`/api/contacts/register`|POST| Register Contacts|
|`/api/contacts/retrieve`|GET| Retrieve All Contacts|
|`/api/contacts/retrieve/{id}`|GET| Retrieve Contact By Id|
|`/api/contacts/update/{id}`|PUT| Update Contact By Id|
|`/api/contacts/delete/{id}`|DELETE|Delete Contact By Id|

#### ContactWithDuplicates Requests

|Endpoint|Method|Description|
|:---|:---:|:---|
|`/api/duplicateContacts/register`|POST|Register Contacts|
|`/api/duplicateContacts/retrieve`|GET|Retrieve All Contacts|
|`/api/duplicateContacts/retrieve/{id}`|GET| Retrieve Contact By Id|
|`/api/duplicateContacts/update/{id}`|PUT|Update Contact By Id|
|`/api/duplicateContacts/delete/{id}`|DELETE|Delete Contact By Id|
|`/api/duplicateContacts/mergeDuplicates`|POST|Merge Contacts based on email or phoneNo|

#### Images
-  Request body for Admin Login
  ![Request body for Admin Login](https://github.com/YashKumar14/Contact-Management-API/blob/master/Request%20body%20for%20admin%20login.png?raw=true)

- Response body for admin signup
  ![Response body for admin signup](https://github.com/YashKumar14/Contact-Management-API/blob/master/Response%20body%20for%20admin%20signup.png?raw=true)

-  Request body for user signup
  ![Request body for user signup](https://github.com/YashKumar14/Contact-Management-API/blob/master/Request%20body%20for%20user%20signup.png?raw=true)

-  Response body for admin login
 ![Response body for admin login](https://github.com/YashKumar14/Contact-Management-API/blob/master/Response%20body%20for%20admin%20login.png?raw=true)

## Design Choices
### Spring Boot for Rapid Development:
- Spring Boot was chosen for this application to simplify the setup and development process. Its auto-configuration feature reduces the amount of boilerplate code, allowing developers to focus more on building features rather than configuration. This also leads to quicker iteration cycles during development.

#### JWT for Authentication:
- Using JWT (JSON Web Tokens) allows for stateless authentication, which means the server does not need to keep track of session information. This enhances scalability and performance as each request contains all the necessary information for authentication.

#### Role-Based Access Control:
- The application implements role-based access control (RBAC) to restrict access to certain endpoints. This approach enhances security by ensuring that only authorized users can access sensitive information and perform certain actions.

#### Exception Handling:
- Global exception handling is implemented to provide meaningful error responses to clients, ensuring that any issues during request processing are communicated clearly and effectively.

#### Spring Security:
- Spring Security is integrated to provide a robust authentication and authorization mechanism. This framework offers a comprehensive security model that is highly configurable and extends easily as the application grows.

#### Field Validation: 
- Ensured that inputs like email and phone number are validated using annotations such as @NotBlank, @Email, and @Pattern to prevent invalid data from being processed.

#### Data Handling: 
- The application merges duplicate contacts based on email or phone number to ensure unique entries, reducing redundancy in data.

#### Database:
- MySql the realational database used to store contacts and users data.
