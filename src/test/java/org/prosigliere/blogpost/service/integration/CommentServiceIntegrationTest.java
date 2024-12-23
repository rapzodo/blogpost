package org.prosigliere.blogpost.service.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.repository.CommentRepository;
import org.prosigliere.blogpost.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext
class CommentServiceIntegrationTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        commentRepository.deleteAll();
    }

    @Test
    void shouldFindCommentById() throws RecordNotFoundException {
        Comment comment = createSomeComment();

        Comment foundComment = commentService.findCommentById(comment.getId());
        assertNotNull(foundComment);
        assertEquals(comment.getId(), foundComment.getId());
    }

    @Test
    @Transactional
    void shouldUpdateComment() {
        Comment comment = createSomeComment();

        comment.setContent("Updated Content");
        commentService.updateComment(comment);
        commentRepository.flush();
        assertNotNull(comment);
        assertEquals("Updated Content", comment.getContent());
        assertNotNull(comment.getUpdatedAt());
    }

    private Comment createSomeComment() {
        Comment comment = new Comment();
        comment.setContent("Test Content");
        commentRepository.save(comment);
        return comment;
    }

    @Test
    void shouldDeleteComment() {
        Comment comment = createSomeComment();

        commentService.deleteComment(comment.getId());

        Optional<Comment> deletedComment = commentRepository.findById(comment.getId());
        assertTrue(deletedComment.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenCommentNotFound() {
        assertThrows(RecordNotFoundException.class, () -> commentService.findCommentById(1L));
    }
}