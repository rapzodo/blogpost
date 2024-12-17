package org.prosigliere.blogpost.model.record;

import org.prosigliere.blogpost.model.entity.BlogPost;

public record BlogPostRequest(String title, String content) {
    public BlogPost toBlogPost() {
        return new BlogPost(title, content);
    }
}
