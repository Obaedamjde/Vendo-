# Final Graduation Project – Spring Boot Backend

## Overview
ِA University graduation project developed using Spring Boot.  
The project implements a complete backend system for an e-commerce–style platform, providing RESTful APIs for managing users, sellers, products, categories, orders, and favorites with secure authentication and authorization.

## Features
- User registration and management
- Seller profiles and management
- Product and category management
- Order processing system
- Favorites functionality
- RESTful API architecture
- Authentication and authorization using Spring Security
- Data persistence using Spring Data JPA

## Project Architecture
The project follows a layered architecture:
- **Controller Layer**: Handles HTTP requests and REST APIs
- **Service Layer**: Business logic and application rules
- **Repository Layer**: Data access using Spring Data JPA
- **Entity & DTO Layer**: Database entities and data transfer objects
- **Security Layer**: Authentication and authorization configuration

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Maven
- Hibernate
- RESTful APIs
- MapStruct

## Project Structure
The project is organized following standard Spring Boot best practices to ensure scalability, maintainability, and clear separation of concerns.

```text
src/main/java/com/aabu/finalproject
├── controller
│   └── REST controllers handling HTTP requests and responses
├── service
│   └── Business logic and application services
├── repository
│   └── Data access layer using Spring Data JPA
├── model
│   ├── entity
│   │   └── JPA entities representing database tables
│   ├── dto
│   │   ├── request
│   │   │   └── DTOs used for incoming API requests (Create / Update)
│   │   ├── response
│   │   │   └── DTOs used for outgoing API responses
│   │   ├── update
│   │   │   └── DTOs dedicated for partial or full update operations
│   │   └── common
│   │       ├── ApiError
│   │       ├── PageReqDTO
│   │       └── PageResponse
│   ├── enums
│   │   └── Fixed application constants and states
│   └── exception
│       └── Custom application exceptions and error handling logic
├── utility
│   ├── PageUtil
│   └── UserSession
├── config
│   ├── AuthService
│   ├── CustomUserDetails
│   ├── CustomUserDetailsService
│   ├── GlobalExceptionHandler
│   └── SecurityConfig
└── FinalProjectApplication.java
```

## How to Run
1. Clone or download the repository
2. Make sure Java and Maven are installed
3. Configure database settings in `application.properties`
4. Run the project using:




5. Access the APIs using a tool such as Postman

## Notes
- This project was developed for academic purposes.
- The system demonstrates backend development concepts using Spring Boot.
- Frontend is not included in this repository.

## Author
- Student: (Your Name)
- University: Al-Albayt University
- Faculty: Information Technology
- Year: 2025

## License
This project is intended for educational and academic use only.

