QA Automation CI/CD Framework

This is a production-style QA automation project that demonstrates how automated UI tests are executed independently from application code using CI/CD best practices.

This repository contains test automation only.
The application under test is deployed externally (QA or Staging environment) and is owned and maintained by a separate team.

What this project is

This project demonstrates:

A Spring Boot application (external) used as a system under test

A Selenium UI automation framework written in Java

Selenium Grid for scalable browser execution

Docker Compose for local infrastructure setup

GitHub Actions for continuous integration

The purpose of this project is to show how QA automation is integrated into a real CI/CD pipeline, not just how automated tests are written.

Tech Stack

Java 17

Maven

Selenium 4

JUnit 5

Selenium Grid (Hub + Chrome Nodes)

Docker and Docker Compose

GitHub Actions

Allure Reports

Project Structure
qa-automation-framework/
├── src/test/java
│   ├── core        # driver, configuration, base test setup
│   ├── pages       # Page Object Model
│   └── tests       # UI tests (smoke, regression)
├── src/test/resources
│   └── config.properties
├── docker-compose.yml
├── pom.xml
└── .github/workflows
    └── ui-tests.yml

How to run locally
Prerequisites

Make sure the following tools are installed:

Java 17

Maven 3.9 or newer

Docker

Docker Compose

Verify installation:

java -version
mvn -version
docker --version
docker compose version

Start Selenium Grid using Docker
docker compose up -d


This starts:

Selenium Hub

Chrome browser node

Optional VNC support for live browser viewing

Selenium Grid UI will be available at:

http://localhost:4444

Configure test environment

Edit the configuration file:

src/test/resources/config.properties


Example configuration:

baseUrl=https://your-qa-environment.com
browser=chrome
headless=false

How to run tests

Run all tests locally:

mvn test -DbaseUrl=https://your-qa-environment.com


Run tests in headless mode:

mvn test -DbaseUrl=https://your-qa-environment.com -Dheadless=true


Generate and view the Allure report:

mvn allure:serve

CI Pipeline (GitHub Actions)

The CI pipeline runs automatically in the following cases:

On push to the main branch

On manual trigger

On scheduled smoke test runs

CI workflow steps

Checkout the automation repository

Set up Java 17

Install Google Chrome

Execute Selenium UI tests

Upload test results as Allure artifacts

Application environment URLs are provided via GitHub Actions secrets:

BASE_URL=https://staging.example.com


This approach keeps environment configuration secure and flexible.

Screenshots

Add screenshots here after the first successful runs.

Recommended screenshots:

Successful GitHub Actions pipeline run

Selenium Grid dashboard

VNC browser session during test execution

Example markdown:

![CI Success]c="https://github.com/user-attachments/assets/10192b44-7483-438e-9f08-c3f5fc19dd06"


![Selenium Grid](screenshots/selenium-grid.png)

Why this project matters

This project demonstrates:

Clear separation between test automation and application code

Real-world CI/CD automation practices

Scalable UI test execution using Selenium Grid

Clean Page Object Model architecture

Environment-agnostic test execution across QA and Staging environments

This reflects how modern QA automation is implemented in real production teams.

Author

Alona Oleksiienko
Automation Quality Assurance Engineer

Core skills: Java, Selenium, CI/CD, Docker

Planned Improvements

API test layer using RestAssured

Test tagging (smoke, regression)

Parallel execution optimization

Notifications (Slack or email)

BrowserStack or cloud grid integration
