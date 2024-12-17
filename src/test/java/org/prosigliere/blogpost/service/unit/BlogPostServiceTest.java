package org.prosigliere.blogpost.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.BlogPost;
import org.prosigliere.blogpost.repository.BlogPostRepository;
import org.prosigliere.blogpost.service.BlogPostService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BlogPostServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPosts() {
        BlogPost post1 = new BlogPost();
        BlogPost post2 = new BlogPost();
        when(blogPostRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<BlogPost> result = blogPostService.findAllPosts();

        assertEquals(2, result.size());
        verify(blogPostRepository, times(1)).findAll();
    }

    @Test
    void shouldCreatePostAndReturnIt() {
        BlogPost post = new BlogPost();
        when(blogPostRepository.save(post)).thenReturn(post);

        BlogPost result = blogPostService.createPost(post);

        assertNotNull(result);
        verify(blogPostRepository, times(1)).save(post);
    }

    @Test
    void shouldReturnPostWhenPostExists() throws RecordNotFoundException {
        BlogPost post = new BlogPost();
        when(blogPostRepository.findById(1L)).thenReturn(Optional.of(post));

        BlogPost result = blogPostService.findPostById(1L);

        assertNotNull(result);
        verify(blogPostRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowsNotFoundExceptionWhenPostDoesNotExist() {
        when(blogPostRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> blogPostService.findPostById(1L));
        verify(blogPostRepository, times(1)).findById(1L);
    }

    @Test
    void deletePost_deletesPost() {
        doNothing().when(blogPostRepository).deleteById(1L);

        blogPostService.deletePost(1L);

        verify(blogPostRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldSaveAndReturnUpdatedPost() {
        {
            BlogPost post = new BlogPost();
            when(blogPostRepository.save(post)).thenReturn(post);

            BlogPost result = blogPostService.updatePost(post);

            assertNotNull(result);
            verify(blogPostRepository, times(1)).save(post);
        }
    }
}