# Repository Guidelines
This workshop is a Maven multi-module repo for generating API server and client from a common OpenAPI spec. Use this guide to keep contributions consistent and reproducible.

## Project Structure & Module Organization
- Root `pom.xml` aggregates `webapi` (Spring Boot server) and `client` (Java REST client) modules.
- The OpenAPI contract lives in `openapi/openapi.yaml`; generator output lands in each module's `target/generated-sources` and should not be committed.
- Application code sits under `webapi/src/main/java`, client libraries under `client/src/main/java`, and corresponding tests in `src/test/java`.
- Static-analysis configuration is in `/spotbugs` and Maven enforces it via shared pluginManagement.

## Build, Test, and Development Commands
- `mvn test` (root) regenerates sources, compiles both modules, runs unit/integration tests, SpotBugs, PMD, Checkstyle, and JaCoCo.
- `mvn -pl webapi spring-boot:run` starts the API locally using the generated controllers.
- `mvn -pl client -am package` builds the client JAR and refreshes HTTP client stubs for downstream use.
- `mvn -pl webapi -am verify -DskipTests` quickly validates code style and static analysis when tests are expensive.

## Coding Style & Naming Conventions
- Java 21, Google Checkstyle, and SpotBugs are mandatory; run `mvn test` before pushing to surface violations.
- Prefer 4-space indentation, `UpperCamelCase` for classes, `lowerCamelCase` for methods/fields, and `SCREAMING_SNAKE_CASE` for constants.
- Generated interfaces are under `dev.sobue.workshop.*`; place custom implementations in parallel packages to keep merges clean.

## Testing Guidelines
- Primary framework: JUnit 5 with Spring Boot test support; integration tests may rely on Testcontainers MySQL.
- Name test classes `*Test` and align package with the code under test.
- JaCoCo XML reports are produced automatically; maintain or improve coverage in touched areas and document any justified gaps in PRs.

## Commit & Pull Request Guidelines
- Follow existing history: one-line, imperative commit subjects (e.g., “Update API billing schema”), optional `scope: message` when helpful.
- Each PR should describe the change, reference related issues, and call out schema or contract updates.
- Include verification steps (`mvn test`, manual endpoint checks) and note any follow-up work or configuration required for reviewers.

## OpenAPI Workflow
- Update `openapi/openapi.yaml` first when changing endpoints, then rerun `mvn test` to regenerate artifacts.
- Avoid editing generated sources; instead, extend behavior in handwritten classes or adjust generator options in module `pom.xml`.
