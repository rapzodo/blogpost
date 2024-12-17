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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
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
        Comment comment = new Comment();
        comment.setContent("Test Content");
        commentRepository.save(comment);

        Comment foundComment = commentService.findCommentById(comment.getId());
        assertNotNull(foundComment);
        assertEquals(comment.getId(), foundComment.getId());
    }

    @Test
    void shouldUpdateComment() {
        Comment comment = new Comment();
        comment.setContent("Test Content");
        commentRepository.save(comment);

        comment.setContent("Updated Content");
        commentService.updateComment(comment);

        Comment updatedComment = commentRepository.findById(comment.getId()).orElse(null);
        assertNotNull(updatedComment);
        assertEquals("Updated Content", updatedComment.getContent());
    }

    @Test
    void shouldDeleteComment() {
        Comment comment = new Comment();
        comment.setContent("Test Content");
        commentRepository.save(comment);

        commentService.deleteComment(comment.getId());

        Optional<Comment> deletedComment = commentRepository.findById(comment.getId());
        assertTrue(deletedComment.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenCommentNotFound() {
        assertThrows(RecordNotFoundException.class, () -> commentService.findCommentById(1L));
    }
}