package org.prosigliere.blogpost.model.record;

import java.util.List;

public record BlogPostResponse(Long id, String title, String content, List<CommentResponse> comments) {
}
