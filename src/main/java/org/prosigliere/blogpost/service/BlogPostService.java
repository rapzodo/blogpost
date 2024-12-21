package org.prosigliere.blogpost.service;

import jakarta.transaction.Transactional;
import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.BlogPost;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.repository.BlogPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

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
    public BlogPost addComment(Long id, String commentContent) throws RecordNotFoundException {
        logger.info("adding comment {} to post{}", commentContent, id);
        BlogPost post = findPostById(id);
        post.getComments().add(new Comment(commentContent, post));
        return blogPostRepository.save(post);
    }
}
