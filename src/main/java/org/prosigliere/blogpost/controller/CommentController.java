package org.prosigliere.blogpost.controller;

import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.model.record.CommentRequest;
import org.prosigliere.blogpost.model.record.CommentResponse;
import org.prosigliere.blogpost.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) throws RecordNotFoundException {
        // update comment
        Comment comment = commentService.findCommentById(id);
        comment.setContent(commentRequest.content());
        commentService.updateComment(comment);
        return comment.toCommentResponse();
    }
}
