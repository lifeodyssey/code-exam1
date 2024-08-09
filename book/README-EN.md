# Book Service

## Pre-requisites

Please make sure you have installed the following tools:

- gradle
- Java 17
- Docker

## How to run it locally

1. Create a `.env` file in the root directory of the project follow the `.env.example` file.
2. Run the following commands:

```bash
export $(cat .env | xargs)
./book/gradlew -p book clean build
docker-compose -f docker-compose-local.yml up --build -d
```

## How to test it locally

1. Get all the books

```bash
curl -X GET http://localhost:8081/books -H "Accept: application/json"
```

Note: There are four books in the response. Details can be see in
the ``code-exam1/book/src/main/resources/db/migration/V1__Create_books_table_and_insert_initial_data.sql``

2. Create a book

```bash
curl -X POST http://localhost:8081/books \
     -H "Content-Type: application/json" \
     -v \
     -d '{
           "title": "Clean Code: A Handbook of Agile Software Craftsmanship",
           "author": "Robert C. Martin",
           "publisher": "Pearson",
           "price": 6430
         }'
        
```

3. Create a book with an empty title(error case)

```bash

```bash
curl -X POST http://localhost:8081/books \
     -H "Content-Type: application/json" \
     -v \
     -d '{
           "title": "Clean Code: A Handbook of Agile Software Craftsmanship",
           "author": "Robert C. Martin",
           "publisher": "Pearson",
           "price": 6430
         }'
        
```

## The high-level architecture

This is a simple book service that allows you to create and get books. The service is built using the following
technologies:

- Java 17
- Spring Boot 3
- Docker
- PostgreSQL
- Flyway
- JUnit 5
- Mockito
- Testcontainers
- Gradle

The service is composed of three layers:Controller, Service, and Repository.

I didn't use complicated DDD to follow the `` Keep It Simple and Stupid`` rule.

## Highlight

- Comprehensive build step to ensure the code quality.The build step includes the following steps:
    - Test
    - Checkstyle: use spotless
    - Spotbugs
    - Jacoco: test coverage is higher than 90% (actually 100% until now). This can be checked by running the following
      command:
      ```bash
      ./book/gradlew -p book clean build jacocoTestReport
      ```
      And then open the ``Book/build/jacocoHtml/index.html`` file in the browser.

- Test : I implemented the test follow the Test Driven Devlopment(TDD) approach. The tests include the following types:
    - Unit test: For the service layer, the unit test is implemented using JUnit 5 and Mockito. The controller layer is
      tested using ``@WebMvcTest`` with mockMVC.
    - Integration test: The repository layer is tested using Testcontainers and JUnit 5.
    - Application test(or API test): The whole Spring application is tested using Testcontainers
- Configuration: The configuration is externalized using the `.env` file. As a result, the docker image could be
  deployed into different environments without changing the code.
- Database: The database is managed using Flyway. The schema is versioned and the migration is done automatically when
  the application starts.
- Clean Code: I followed some clean code practices. For example, I extracted a ``ApplicationTestBase`` for common test
  code; SOLID principle like ``BookService`` interface and ``BookServiceImpl`` implementation; the builder pattern for
  the ``Book`` entity.
- Global Exception Handling: I implemented a global exception handler to handle the exception in the application level.

## Improvement

- Logging: The logging is not implemented yet, which is crucial for the production environment.
- Security: There are no authentication for these two APIs. In the real environment, the authentication can be
  implemented in the infrastructure level or the application level. So I leave it blank.
- Commit Hooks: This is important in the team development.
- Further refactor: The code can be further refactored. For example, the error message and global exception can be
  refactored with enum or constant instead of using hardcoded string.Further refactor can be done with more code commit.
- CI/CD: The CI/CD pipeline is not implemented yet. My current containerization and gradle configuration can make it
  onboard to CI/CD pipeline easily.
- API Documentation: The API documentation is not wrote yet. I add swagger for it now, can be accessed
  via``http://localhost:8081/swagger-ui/index.html``.
