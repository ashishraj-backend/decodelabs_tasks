# Student Management REST API

## Project Overview

Student Management REST API is a production-quality Java Spring Boot application built for the DecodeLabs Internship Project 2. It provides a clean, layered CRUD API for managing student records with validation, exception handling, search, sorting, pagination, and Swagger documentation.

## Features

- Create, read, update, and delete student records
- Search by course, email, and semester
- Sort students by CGPA
- Pagination support
- Validation for email, phone, name, semester, and CGPA
- Global exception handling with meaningful error responses
- H2 in-memory database with sample data loaded on startup
- Swagger UI documentation

## Technology Stack

- Java 21
- Spring Boot 3.x
- Maven
- Spring Web
- Spring Validation
- Lombok
- Spring Data JPA
- H2 Database
- Swagger OpenAPI

## Folder Structure

```
src/main/java/com/decodelabs/studentmanagement
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
├── util
src/main/resources
├── application.properties
```

## API Endpoints

- `GET /api/v1/students` — list students with optional filters and pagination
- `GET /api/v1/students/{id}` — get a student by ID
- `POST /api/v1/students` — create a new student
- `PUT /api/v1/students/{id}` — update existing student
- `DELETE /api/v1/students/{id}` — delete a student

### Query Parameters

- `course` — filter by course name
- `email` — filter by email address
- `semester` — filter by semester
- `page` — zero-based page index (default `0`)
- `size` — page size (default `10`)
- `sortByCgpa` — sort by CGPA descending when `true`

## Installation

1. Clone or download the project repository.
2. Open the project in IntelliJ IDEA.
3. Ensure Maven is configured for the workspace.

## Run Instructions

1. Use IntelliJ to run `com.decodelabs.studentmanagement.StudentManagementApiApplication`.
2. Alternatively, execute:

```bash
mvn spring-boot:run
```

## Swagger URL

After the application starts, open:

- `http://localhost:8080/swagger-ui/index.html`

## Example Requests

### Create Student

POST `http://localhost:8080/api/v1/students`

Request body:

```json
{
  "firstName": "Ava",
  "lastName": "Patel",
  "email": "ava.patel@example.com",
  "phone": "9876543210",
  "course": "Computer Science",
  "semester": 5,
  "cgpa": 9.1
}
```

### Get Student

GET `http://localhost:8080/api/v1/students/1`

### Search by Course

GET `http://localhost:8080/api/v1/students?course=computer&page=0&size=5`

### Sort by CGPA

GET `http://localhost:8080/api/v1/students?sortByCgpa=true`

## Example Responses

### Successful student creation

```json
{
  "id": 6,
  "firstName": "Ava",
  "lastName": "Patel",
  "email": "ava.patel@example.com",
  "phone": "9876543210",
  "course": "Computer Science",
  "semester": 5,
  "cgpa": 9.1
}
```

### Validation error response

```json
{
  "timestamp": "2026-07-14T15:00:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Student data failed validation",
  "path": "/api/v1/students",
  "validationErrors": {
    "email": "must be a well-formed email address",
    "phone": "Phone number must contain 10 digits"
  }
}
```

## Screenshots

Add screenshots of the Swagger UI or Postman workspace here if desired.

## Future Improvements

- Add authentication and authorization
- Add unit and integration tests
- Add student course enrollment and grade history
- Add export/import CSV or Excel support
- Migrate to PostgreSQL for production use
