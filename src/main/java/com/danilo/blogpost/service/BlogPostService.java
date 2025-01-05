package com.danilo.blogpost.service;

import jakarta.transaction.Transactional;
import com.danilo.blogpost.exception.InvalidCommentException;
import com.danilo.blogpost.exception.RecordNotFoundException;
import com.danilo.blogpost.model.entity.BlogPost;
import com.danilo.blogpost.model.entity.Comment;
import com.danilo.blogpost.repository.BlogPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private static final int MAX_LENGTH = 255;
    private final BlogPostRepository blogPostRepository;
    private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> findAllPosts() {
        logger.info("finding all posts");
        return blogPostRepository.findAll();
    }

    @Transactional
    public BlogPost createPost(BlogPost post) {
        logger.info("Creating post: {}", post);
        return blogPostRepository.save(post);
    }

    public BlogPost findPostById(Long id) throws RecordNotFoundException {
        logger.info("finding post: {}", id);
        return blogPostRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Post not found"));
    }

    @Transactional
    public void deletePost(Long id) {
        logger.info("deleting post: {}", id);
        blogPostRepository.deleteById(id);
    }

    @Transactional
    public BlogPost addComment(Long id, String commentContent) throws RecordNotFoundException, InvalidCommentException {
        logger.info("adding comment {} to post{}", commentContent, id);
        if(commentContent == null || commentContent.isBlank() || commentContent.length() > MAX_LENGTH) {
            throw new InvalidCommentException("Comment content cannot be empty or exceed 255 characters");
        }
        BlogPost post = findPostById(id);
        post.addComment(new Comment(commentContent));
        return blogPostRepository.save(post);
    }
}
