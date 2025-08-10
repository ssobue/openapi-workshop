# OpenAPI Development Workshop

This repository contains a simple multi-module Maven project that demonstrates how to build a Web API server and a Java client from a shared OpenAPI specification.

## Project structure

- `openapi` - OpenAPI YAML documents used to generate server and client code.
- `webapi` - Spring Boot application that exposes the API described in the OpenAPI specification.
- `client` - Java client library for interacting with the Web API.

## Prerequisites

- Java 21
- Maven 3.9+

## Building and testing

Run the following command to generate sources and execute all checks and tests:

```bash
mvn test
```

This command uses the OpenAPI Generator to create code in both modules, compiles the project, and runs unit tests and static analysis tools.

