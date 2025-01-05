package com.danilo.blogpost.controller;

import com.danilo.blogpost.exception.RecordNotFoundException;
import com.danilo.blogpost.model.entity.Comment;
import com.danilo.blogpost.model.record.CommentRequest;
import com.danilo.blogpost.model.record.CommentResponse;
import com.danilo.blogpost.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @PatchMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) throws RecordNotFoundException {
        // update comment
        Comment comment = commentService.findCommentById(id);
        comment.setContent(commentRequest.content());
        commentService.updateComment(comment);
        return comment.toCommentResponse();
    }
}
