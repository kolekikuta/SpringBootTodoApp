# SpringBootTodoApp

A simple Todo List REST API built to learn Spring Boot and MySQL.

## 🚀 Features
- CRUD operations on Tasks
  - Create new tasks
  - Get all tasks / completed / incomplete
  - Update tasks
  - Delete tasks
- REST endpoints with proper HTTP status codes (201 for create, 204 or 200 for delete/update, etc.)
- Use of Spring Data JPA for persistence
- Exception handling for cases when tasks are not found
- Uses Lombok to reduce boilerplate in model and service classes (getters/setters, constructors)

## 🛠️ Technologies
- Java
- Spring Boot
- Spring Data JPA
- MySQL (as the database)
- Lombok
- Maven

## ⚙️ Setup & Installation

1. Clone the repository
```bash
git clone https://github.com/kolekikuta/SpringBootTodoApp.git
cd SpringBootTodoApp
```

2. Create a MySQL database
```sql
CREATE DATABASE springboottodo_db;
```

3. Configure application properties
Open src/main/resources/application.properties and set:
```
spring.datasource.url=jdbc:mysql://localhost:3306/springboottodo_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD (or leave blank if none)
```

4. Build & run
```bash
mvn clean install
mvn spring-boot:run
```

The app will start on http://localhost:8080/.

## 📦 REST API Endpoints
| Method | Path                     | Description                          | Response        | Status                  |
|--------|--------------------------|--------------------------------------|-----------------|-------------------------|
| POST   | /api/v1/tasks/           | Create a new Task                    | Task object     | 201 Created             |
| GET    | /api/v1/tasks/           | Get all tasks                        | List of tasks   | 200 OK                  |
| GET    | /api/v1/tasks/completed  | Get all tasks that are completed     | List of tasks   | 200 OK                  |
| GET    | /api/v1/tasks/incomplete | Get all tasks that are not completed | List of tasks   | 200 OK                  |
| PUT    | /api/v1/tasks/{id}       | Update a task by ID                  | Task object     | 200 OK or 404 Not Found |
| DELETE | /api/v1/tasks/{id}       | Delete a task by ID                  | None            | 204 No Content or 404 Not Found |

## 🧪 Testing

- Uses Spring’s testing framework and Mockito.
- Controller tests mock out the service layer.
- Tests check behavior of each endpoint (create, update, get, delete), including error scenarios.

To run tests:
```bash
mvn test
```
## ❓ Error Handling
- If you try to update or delete a task that doesn’t exist, the service will throw a TaskNotFoundException, which is translated to a 404 Not Found HTTP response.

## 👍 Improvements & TODOs
- [] Add user authentication / authorization
- [] Input validation (e.g. checking required fields)
- [] Paging and sorting for large lists of tasks
- [] Better error messages / response bodies with error codes
- [] Add integration tests (with an in-memory database)

## 👤 Author
Kole Kikuta
