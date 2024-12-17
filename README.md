# BlogPost API

## Overview

This project is a simple blog post API built with Java, Spring Boot, and Gradle. It allows users to create blog posts and add comments to them.

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 7.0 or higher

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/blogpost-api.git
    cd blogpost-api
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

3. Run the application:
    ```sh
    ./gradlew bootRun
    ```

### Accessing Swagger UI

Swagger UI is available to visualize and interact with the API's resources. After running the application, you can access Swagger UI at the following URL:
http://localhost:8080/swagger-ui.html

Make sure to replace `8080` with the actual port number if your application runs on a different port.

### API Endpoints

- `POST /api/v1/posts`: Create a new blog post.
- `GET /api/v1/posts`: Retrieve a list of all blog posts.
- `PUT /api/v1/posts/{id}/comments`: Add a comment to a specific blog post.
- `PUT /api/v1/comments/{id}`: Update a specific comment.
- `DELETE /api/v1/comments/{id}`: Delete a specific comment.

## Running Tests

To run the tests, use the following command:
```sh
./gradlew test
```

## What's next?
### features :
   - include feature to react to a post or comment (like/dislike)
   - include feature to delete or edit a post

### performance
    - include caching to improve performance (post would be a good candidate as they don't change often)

### security
   - include access control to restrict access to certain endpoints
   - api gateway to manage access to the api

### monitoring
   - include monitoring to track the performance of the api
   - increase logging to track the usage of the api
   - include alerting to notify when the api is down