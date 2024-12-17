package org.prosigliere.blogpost.model.record;

import org.prosigliere.blogpost.model.entity.Comment;

public record CommentRequest(String content) {
    public Comment toComment() {
        return new Comment(content);
    }
}