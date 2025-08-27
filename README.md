# Slot Booker API

This project is a RESTful API for a slot booking system, built with **Java** and the **Spring Boot** framework. It provides a robust backend for managing users and handling future slot booking functionalities. The application uses a **PostgreSQL** database for data persistence and includes **Liquibase** for database migration management.

## 🚀 Technologies Used

- **Java 17+**
    
- **Spring Boot 3.x:** The core framework for building the application.
    
- **Spring Data JPA:** For database interactions and repository management.
    
- **Spring Security:** For authentication and authorization using JWT.
    
- **PostgreSQL:** The relational database.
    
- **Liquibase:** For managing database schema changes.
    
- **Maven:** For dependency management and project building.
    
- **Lombok:** To reduce boilerplate code.
    
- **`jsonwebtoken-api`**: For handling JWTs.
    
- **Jakarta Validation**: For request body validation.
    

## 📦 Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher
    
- Maven 3.6.3 or higher
    
- PostgreSQL database instance
    

### Setup

1. **Clone the repository:**
    
    ```
    git clone https://github.com/your-username/your-repository-name.git
    cd slot-booker
    ```
    
2. **Configure the database:**
    
    - Create a new PostgreSQL database.
        
    - Open `src/main/resources/application.properties` and update the database connection properties.
        
    
    <!-- end list -->
    
    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```
    
3. **Build the project:**
    
    ```
    mvn clean install
    ```
    
4. **Run the application:**
    
    ```
    mvn spring-boot:run
    ```
    

The application will start on `http://localhost:8080`.

## 🌐 API Endpoints

All API endpoints are prefixed with `/api/v1`.

|Endpoint|Method|Description|
|---|---|---|
|`/api/v1/auth/login`|`POST`|Authenticates a user and returns a JWT token.|
|`/api/v1/users`|`POST`|Registers a new user.|
|`/api/v1/users`|`GET`|Retrieves a list of all users.|
|`/api/v1/users/{userId}`|`GET`|Retrieves a user by their ID.|
|`/api/v1/users/{userId}`|`PUT`|Updates an existing user.|
|`/api/v1/users/{userId}`|`DELETE`|Deletes a user.|

For detailed API documentation, use a tool like Swagger or Postman once the application is running.

## 🤝 Contributing

We welcome contributions! Please see our [CONTRIBUTING.md](https://www.google.com/search?q=CONTRIBUTING.md "null") for guidelines on how to get started.
