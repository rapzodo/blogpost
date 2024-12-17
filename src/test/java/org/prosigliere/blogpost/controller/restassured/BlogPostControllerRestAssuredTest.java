package org.prosigliere.blogpost.controller.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prosigliere.blogpost.model.entity.BlogPost;
import org.prosigliere.blogpost.model.record.BlogPostRequest;
import org.prosigliere.blogpost.model.record.CommentRequest;
import org.prosigliere.blogpost.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogPostControllerRestAssuredTest {

    @LocalServerPort
    private int port;
    @Autowired
    private BlogPostRepository blogPostRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        blogPostRepository.deleteAll();
    }

    @Test
    public void shouldGetAllPosts() {
        BlogPost post = new BlogPost("title", "content");
        blogPostRepository.save(post);

        given()
                .when()
                .get("/api/v1/posts")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("$", hasSize(1))
                .body("[0].title", equalTo("title"));
    }

    @Test
    public void shouldCreatePost() {
        BlogPostRequest blogPostRequest = new BlogPostRequest("New Post", "New Content");

        given()
                .contentType(ContentType.JSON)
                .body(blogPostRequest)
                .when()
                .post("/api/v1/posts")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("title", equalTo("New Post"));
    }

    @Test
    public void shouldAddComment() {
        CommentRequest comment = new CommentRequest("New Comment");
        BlogPost post = blogPostRepository.save(new BlogPost("title", "content"));

        given()
                .contentType(ContentType.JSON)
                .body(comment)
                .when()
                .put("/api/v1/posts/" + post.getId() + "/comments")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("comments", hasSize(1));
    }

    @Test
    public void shouldReturn404IfPostDontExist() {
        CommentRequest comment = new CommentRequest("New Comment");

        given()
                .contentType(ContentType.JSON)
                .body(comment)
                .when()
                .put("/api/v1/posts/1/comments")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}