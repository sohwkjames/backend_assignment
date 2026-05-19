The application is currently deployed to AWS elastic beanstalk. GET http://book-api-env.eba-gam7exmu.ap-southeast-1.elasticbeanstalk.com/api/books for a health check.

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books/{id}` | Get a book by ID |
| POST | `/api/books` | Add a new book |
| DELETE | `/api/books/{id}` | Delete a book by ID |

Data is loaded from a JSON file (`books.json`) into memory on application startup — no database is used.

---

--- 

## Request Logging

All incoming HTTP requests and responses are logged.

---

## Request Validation

Incoming requests for `POST` and `DELETE` are validated before any processing occurs.

For `POST`, the following fields are required:
- `title` — must not be blank
- `author` — must not be blank
- `genre` — must not be blank
- `publishedDate` — must not be null and cannot be a future date

For `DELETE`, the provided ID is checked against the in-memory list. If no matching book is found, a `404` is returned.

Validation is handled by a dedicated `BookValidatorImpl` service

---

---

## Unit Tests

Unit tests for the endpoints are in place as well. Run the unit tests with `./mvnw test`

---

## Dependency Injection

This project demonstrates dependency injection through two mechanisms:

**1. BookValidator injection**

`BookValidator` is injected into `BookService` via constructor injection. 

**2. Strategy pattern**

`BookProcessor` is an interface with multiple implementations (`SanitisingBookProcessor`, `EnrichingBookProcessor`). The caller specifies which processor to apply per request via a `processorType` query parameter, defaulting to `sanitising`. This demonstrates runtime strategy selection driven by the incoming request, with all processor implementations remaining active simultaneously.

--- 


## Running Locally

```bash
# build and run with Maven wrapper
./mvnw spring-boot:run

# or build and run with Docker
docker build -t book-api .
docker run -p 8080:8080 book-api

---

## Branching Strategy
The pipeline to build and test is fired when a PR is made from a branch to main. Tests need to pass before branches can be merged to main.

