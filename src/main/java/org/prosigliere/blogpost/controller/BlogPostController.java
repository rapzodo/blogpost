package org.prosigliere.blogpost.controller;

import org.prosigliere.blogpost.exception.InvalidCommentException;
import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.BlogPost;
import org.prosigliere.blogpost.model.record.BlogPostRequest;
import org.prosigliere.blogpost.model.record.BlogPostResponse;
import org.prosigliere.blogpost.model.record.CommentRequest;
import org.prosigliere.blogpost.service.BlogPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public List<BlogPostResponse> getPosts() {
        return blogPostService.findAllPosts()
                .stream()
                .map(BlogPost::toBlogPostResponse).toList();
    }

    @PostMapping
    public BlogPostResponse createPost(@RequestBody BlogPostRequest blogPostRequest) {
        return blogPostService.createPost(blogPostRequest.toBlogPost()).toBlogPostResponse();
    }

    @PutMapping("/{id}/comments")
    public BlogPostResponse addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) throws RecordNotFoundException, InvalidCommentException {
        return blogPostService.addComment(id, commentRequest.content()).toBlogPostResponse();
    }
}
