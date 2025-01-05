package com.danilo.blogpost.model.record;

import com.danilo.blogpost.model.entity.BlogPost;

public record BlogPostRequest(String title, String content) {
    public BlogPost toBlogPost() {
        return new BlogPost(title, content);
    }
}
