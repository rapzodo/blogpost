package org.prosigliere.blogpost.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.repository.CommentRepository;
import org.prosigliere.blogpost.service.CommentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindCommentById() throws RecordNotFoundException {
        Comment comment = new Comment();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.findCommentById(1L);
        assertNotNull(foundComment);
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.findCommentById(1L));
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateComment() {
        Comment comment = new Comment();
        comment.setContent("Test Content");

        commentService.updateComment(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void shouldDeleteComment() {
        doNothing().when(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}