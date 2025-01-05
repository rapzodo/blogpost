package com.danilo.blogpost.service.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.danilo.blogpost.exception.InvalidCommentException;
import com.danilo.blogpost.exception.RecordNotFoundException;
import com.danilo.blogpost.model.entity.BlogPost;
import com.danilo.blogpost.repository.BlogPostRepository;
import com.danilo.blogpost.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext
public class BlogPostServiceIntegrationTest {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @BeforeEach
    public void setUp() {
        blogPostRepository.deleteAll();
    }

    @Test
    public void shouldFindAllPosts() {
        BlogPost expectedPost = new BlogPost("Test Title", "Test Content");
        blogPostService.createPost(expectedPost);
        List<BlogPost> posts = blogPostService.findAllPosts();
        assertEquals(1, posts.size());
        assertEquals(expectedPost.getTitle(), posts.getFirst().getTitle());
    }

    @Test
    public void shouldCreatePost() {
        BlogPost post = new BlogPost();
        post.setTitle("Test Title");
        post.setContent("Test Content");

        BlogPost createdPost = blogPostService.createPost(post);
        assertNotNull(createdPost);
        assertNotNull(createdPost.getId());
    }

    @Test
    public void testFindPostById() throws RecordNotFoundException {
        BlogPost post = new BlogPost();
        post.setTitle("Test Title");
        post.setContent("Test Content");

        BlogPost createdPost = blogPostService.createPost(post);
        BlogPost foundPost = blogPostService.findPostById(createdPost.getId());
        assertNotNull(foundPost);
        assertEquals(createdPost.getId(), foundPost.getId());
    }

    @Test
    public void postNotFound() {
        assertThrows(RecordNotFoundException.class, () -> blogPostService.findPostById(1L));
    }

    @Test
    public void shouldDeletePost() {
        BlogPost post = new BlogPost("Test Title", "Test Content");

        BlogPost createdPost = blogPostService.createPost(post);
        blogPostService.deletePost(createdPost.getId());

        Optional<BlogPost> deletedPost = blogPostRepository.findById(createdPost.getId());
        assertTrue(deletedPost.isEmpty());
    }

    @Test
    public void shouldAddComment() throws RecordNotFoundException, InvalidCommentException {
        BlogPost expectedPost = new BlogPost("Test Title", "Test Content");
        expectedPost = blogPostService.createPost(expectedPost);

        BlogPost updatedPost = blogPostService.addComment(expectedPost.getId(), "Test Comment");
        assertNotNull(updatedPost);
        assertEquals(1, updatedPost.getComments().size());
        assertEquals(expectedPost.getTitle(), updatedPost.getTitle());
        assertEquals(expectedPost.getContent(), updatedPost.getContent());
    }

}