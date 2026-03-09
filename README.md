# CyShield Automation Framework

A scalable **UI + API automation framework** designed to validate web applications and backend services using modern automation best practices.

This project demonstrates the implementation of a **production-ready automation framework** including:

- Web UI Automation
- API Automation
- CI/CD integration
- Dockerized execution
- Parallel execution
- Advanced reporting
- Logging and debugging
- Visual testing foundation

---

# Table of Contents

- Project Overview
- Tech Stack
- Framework Architecture
- Project Structure
- Test Scenarios
- Running Tests
- Running Test Suites
- Running Tests with Groups
- Running with Docker
- CI/CD Pipeline
- Reporting
- Advanced Features
- Design Decisions

---

# Project Overview

This automation framework validates both **UI workflows and backend APIs** while following **clean architecture principles**.

The framework demonstrates:

- Web UI automation using Selenium
- API automation using RestAssured
- Integration with CI/CD pipelines
- Docker containerized test execution
- Detailed reporting using Allure

The framework emphasizes:

- Maintainability
- Reusability
- Clean code practices
- Scalable test execution

---

# Tech Stack

| Technology | Purpose |
|------------|--------|
| Java | Programming language |
| Selenium WebDriver | UI automation |
| RestAssured | API automation |
| TestNG | Test framework |
| Maven | Build and dependency management |
| Allure | Test reporting |
| Docker | Containerized execution |
| GitHub Actions | CI/CD pipeline |
| SLF4J + Logback | Logging |

---

# Framework Architecture

The framework follows **clean architecture principles** and **separation of concerns**.

### Base Layer

Handles test setup and teardown.

Responsibilities:

- Driver initialization
- Environment configuration
- Test lifecycle management

Example:

```
BaseTests
```

---

### Page Object Model (POM)

UI interactions are implemented using **Page Object Model**.

Benefits:

- Separation of UI logic from test logic
- Reusable UI components
- Easier maintenance
- Improved readability of tests

Page classes handle:

- Element locators
- Page actions
- UI validations

---

### API Client Layer

API requests are abstracted using a reusable **ApiClient** and dedicated **API service classes**.

This design separates request handling from test logic and keeps tests clean and maintainable.

Benefits:

- Cleaner test classes
- Centralized API request logic
- Reusable API operations
- Easier API maintenance

Example:

```
AuthService
PostsService
```

---

### Utilities Layer

Contains reusable helper functions used across the framework.

Examples:

- Screenshot capture
- Allure attachments
- Logging utilities
- Retry logic

---

# Project Structure

```text
CyShield
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── apis
│   │   │   │   ├── api
│   │   │   │   ├── models
│   │   │   │   └── services
│   │   │   ├── com
│   │   │   │   └── sauceDemo
│   │   │   │       └── pages
│   │   │   └── utils
│   │   └── resources
│   │
│   └── test
│       ├── java
│       │   ├── api
│       │   │   ├── base
│       │   │   └── tests
│       │   ├── com
│       │   │   ├── dataProviders
│       │   │   └── sauceDemo
│       │   │          └── base
│       │   │          └── tests
│       │   ├── listeners
│       │   └── utils
│       └── resources
│               ├── config
│               ├── schemas
│               ├── testData
│               └── visualBaseline
│       
├── suites
│      ├── regression
│      └── smoke
├── target
├── test-output
│       ├── logs
│       ├── screenshots
│       └── visual

```



---

# Test Scenarios

## UI Automation

Application under test:

```
https://www.saucedemo.com
```

### Login Tests

Automated scenarios:

- Valid login
- Invalid login
- Locked user login


---

### Checkout Tests

Automated scenarios:

- Add to cart
- Successful checkout
- Empty checkout (Data Drive)
- Invalid checkout


---

### Negative Testing

Tests include:

- Empty input fields
- Invalid inputs
- Proper error handling

---

## API Automation

API under test:

```
https://dummyjson.com
```

### Authentication API

Endpoint:

```
POST /auth/login
```

Validations:

- Status code
- Token generation
- Response time
- Response structure

---

### Posts API

Endpoint:

```
GET /posts
```

Validations:

- Status code
- Response data validation
- JSON schema validation
- Response time

---

### Negative API Testing

Scenarios:

- Invalid payload

---

# Running Tests

Run all tests:

```bash
mvn clean test
```

---

# Running Test Suites

### Run Smoke Suite

```bash
mvn test -DsuiteXmlFile=src/test/suites/smoke/Smoke_Suite_API.xml
mvn test -DsuiteXmlFile=src/test/suites/smoke/Smoke_Suite_COM.xml
```

### Run Regression Suite

```bash
mvn test -DsuiteXmlFile=src/test/suites/regression/Regression_Suite.xml
```

### Run Master Suite

```bash
mvn test -DsuiteXmlFile=src/test/suites/smoke/Master_Suite.xml
```

---


---

# Running with Docker

Build Docker image:

```bash
docker build --no-cache --platform linux/amd64 -t cyshield-tests .
```

Run tests inside Docker container:

```bash
docker run --platform linux/amd64 --rm --name cyshield-container cyshield-tests
```


---

# CI/CD Pipeline

The project integrates with **GitHub Actions**.

The pipeline automatically:

- Runs on pull requests
- Builds the project
- Executes automation tests
- Generates test reports
- Fails the pipeline if tests fail

This ensures unstable code cannot be merged into the main branch.

---

# Reporting

The framework integrates **Allure reporting**.

Generate report locally:

```bash
allure serve allure-results
```

Report includes:

- Test steps
- Execution results
- Failure details
- Screenshots
- Execution timeline

---

# Advanced Features

The framework includes several advanced automation features:

- Page Object Model
- API client abstraction
- API data models for request/response serialization and deserialization
- Parallel test execution
- Cross-browser support
- Retry mechanism for flaky tests
- Automatic failure screenshots
- Structured logging
- JSON schema validation
- Visual testing foundation
- Dockerized test execution
- CI/CD integration
- Test tagging (smoke / regression)

---

# Design Decisions

### Page Object Model

Used to separate UI logic from test logic, improving maintainability and readability.

### API Client Abstraction

Centralizes API requests and simplifies API test maintenance.

### Docker Support

Ensures consistent execution environments across machines and CI pipelines.

### CI/CD Integration

Allows automatic test execution and prevents unstable code from being merged into the main branch.

---

# Conclusion

This project demonstrates the design and implementation of a **production-ready automation framework** combining:

- UI automation
- API automation
- DevOps integration
- scalable architecture

The framework follows modern **automation engineering and SDET best practices**, focusing on reliability, maintainability, and scalability.