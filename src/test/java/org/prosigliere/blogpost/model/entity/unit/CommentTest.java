package org.prosigliere.blogpost.model.entity.unit;

import org.junit.jupiter.api.Test;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.model.record.CommentResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest {

    @Test
    void toCommentResponse() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Content");

        CommentResponse response = comment.toCommentResponse();

        assertEquals(comment.getId(), response.id());
        assertEquals(comment.getContent(), response.content());
    }
}