# User Manager

## Overview

Simple reactive user management application.

## Usage

### Environment Configuration

**Copy the `.env.test` file to `.env` and set the necessary environment variables, or leave them as default:**
```shell
cp .env.example .env
```

### Building and Running the Application

**Using Docker:**
```shell
docker-compose up --build -d
```

## Features

### Implemented Features

> **Note:** I didn't keep the `ExternalProject` entity within the `User` domain because, in the future, these projects may exist independently. Additionally, multiple users may eventually be associated with a single project.

- Get JWT token:
  - Authenticate a user and get a JWT token (`POST /api/auth/login`)

- User Management:
  - Create a new user (`POST /api/users`)
  - Retrieve user information (`GET /api/users/{userId}`)
  - Update user information (`PUT /api/users/{userId}`)
  - Delete a user (`DELETE /api/users/{userId}`)

- External Projects:
  - Add external project to a user (`POST /api/external-projects`)
  - Retrieve external projects from a user (`GET /api/external-projects`)

### Known Missing Features

- All endpoints need E2E tests using Testcontainers. It is not yet implemented. Currently, only one endpoint for creating a user is partially covered by unit test.
- More tests are needed for full coverage of all cases.
- Logging is only added in [GlobalErrorHandler](src/main/java/engineer/dima/manager/error/GlobalErrorHandler.java). More logging is needed throughout the application.
- Only two custom metrics have been added ([CreatedUserCounter](src/main/java/engineer/dima/manager/user/CreatedUserCounter.java) and [CreatedExternalProjectCounter](src/main/java/engineer/dima/manager/externalproject/CreatedExternalProjectCounter.java)), but more metrics could be useful.
- The `/actuator/prometheus` endpoint is open. In production, this must be secured (e.g. with a certificate).
- Swagger documentation is currently missing.
- Implementing a token refresh mechanism to authenticate once and refresh tokens as needed.
- Adding more information in the token, such as user roles.
- Currently, users can only access their own projects. Adding roles could extend this functionality.
- In the future, multiple users could work on a single project. This could be implemented using a Many-to-Many relationship through a junction table.
- Adding timestamp fields to entities and implementing soft delete.
- Database operations are not wrapped in transactions due to the simplicity of the logic. This could be improved.
- Localization of messages (optional).