package org.prosigliere.blogpost.controller.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.model.record.CommentRequest;
import org.prosigliere.blogpost.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerRestAssuredTest {

    @LocalServerPort
    private int port;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        commentRepository.deleteAll();
    }

    @Test
    public void shouldUpdateComment() {
        Comment comment = commentRepository.save(new Comment("Comment"));

        CommentRequest expectedComment = new CommentRequest("Updated Comment");

        given()
                .contentType(ContentType.JSON)
                .body(expectedComment)
                .when()
                .put("/api/v1/comments/" + comment.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("content", equalTo(expectedComment.content()));
    }

    @Test
    public void shouldDeleteComment() {
        Comment comment = commentRepository.save(new Comment("Comment"));

        given()
                .when()
                .delete("/api/v1/comments/" + comment.getId())
                .then()
                .statusCode(HttpStatus.OK.value());

        assertTrue(commentRepository.findById(comment.getId()).isEmpty());
    }

}