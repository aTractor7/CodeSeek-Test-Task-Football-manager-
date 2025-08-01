# Football Manager Application

This project is a REST API for managing football teams and players with support for CRUD operations and player transfers.

---

## Functionality

- CRUD operations for teams and players (REST API)
- Player transfer between teams with cost calculation, commission and balance update
- Input validation and error handling
- Initial database filling with teams and players

---

## Technical stack

- Java 17
- Spring Boot
- Spring Data JPA + Hibernate
- MySQL (in Docker) or H2 (locally)
- Maven
- Docker, Docker Compose

---

## Launching the project

### Option 1. Via Docker Compose (MySQL)

1. Open a terminal in the root of the project.

2. If necessary, configure the environment files in the `docker_compose/` directory:
   - `env_db_compose.txt` — settings for MySQL
   - `env_compose.txt` — settings for the application

3. Run the command:
    ```bash
    docker compose up --build

4. The application will be available at:
http://localhost:8080

5. MySQL listens on port 3307, the database is created automatically with initial data.

### Option 2. Local launch via Maven + H2 (built-in DB)

1. The settings in src/main/resources/application.properties for h2 are used.

2. Run the command:
    ```bash
    `mvn spring-boot:run`:

3. The application is available at:
http://localhost:8080

4. The H2 console is available at:
http://localhost:8080/h2-console
- User: sa
- password: (empty)

## Running Tests

- To run tests locally via Maven:
    ```bash
    mvn test
    ```
- Note: Tests are configured to run with an in-memory database and clean state.

## REST API Structure

| Path | Description |
|------------------------------------|-----------------------------------|
| `/teams` | CRUD for teams |
| `/players` | CRUD for players |
| `/players/{playerId}/transfer/{targetTeamId}` | Transfer a player to another team |

## Postman Collection

The collection with all the requests is available at:
[Postman Football Manager Collection](https://web.postman.co/workspace/My-Workspace~c9cf9a7a-3fc5-4c22-8366-9af4580dbec1/collection/28166767-d983f681-44da-4f51-b2e1-f391961971b0?action=share&source=copy-link&creator=28166767)

---

## Validation and errors

- Validate input with clear messages (HTTP 400)
- Server returns detailed error messages

---