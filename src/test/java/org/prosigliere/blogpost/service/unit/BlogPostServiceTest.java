package org.prosigliere.blogpost.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.prosigliere.blogpost.exception.InvalidCommentException;
import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.BlogPost;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.repository.BlogPostRepository;
import org.prosigliere.blogpost.service.BlogPostService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void shouldAddCommentToPost() throws RecordNotFoundException, InvalidCommentException {
        {
            BlogPost post = new BlogPost();
            post.setComments(Stream.of(new Comment(), new Comment())
                    .collect(Collectors.toCollection(ArrayList::new)));
            when(blogPostRepository.findById(post.getId())).thenReturn(Optional.of(post));
            when(blogPostRepository.save(post)).thenReturn(post);

            BlogPost result = blogPostService.addComment(post.getId(), "Test Comment");

            assertNotNull(result);
            assertEquals(3, result.getComments().size());
            verify(blogPostRepository, times(1)).save(post);
        }
    }

    @Test
    void shouldThrowExceptionWhenPostDoesNotExist() {
        when(blogPostRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> blogPostService.addComment(1L, "Test Comment"));
        verify(blogPostRepository, times(1)).findById(1L);
    }

    @Test
    void shouldAddMultipleComments() throws RecordNotFoundException, InvalidCommentException {
        BlogPost post = new BlogPost();
        post.setComments(Stream.of(new Comment(), new Comment())
                .collect(Collectors.toCollection(ArrayList::new)));
        when(blogPostRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(blogPostRepository.save(post)).thenReturn(post);

        blogPostService.addComment(post.getId(), "Test Comment 1");
        blogPostService.addComment(post.getId(), "Test Comment 2");

        assertNotNull(post);
        assertEquals(4, post.getComments().size());
        verify(blogPostRepository, times(2)).save(post);
    }

    @Test
    void shouldThrowExceptionWhenCommentContentIsInvalid() {
        BlogPost post = new BlogPost();
        post.setComments(Stream.of(new Comment(), new Comment())
                .collect(Collectors.toCollection(ArrayList::new)));
        when(blogPostRepository.findById(post.getId())).thenReturn(Optional.of(post));

        assertThrows(InvalidCommentException.class, () -> blogPostService.addComment(post.getId(), null));
        assertThrows(InvalidCommentException.class, () -> blogPostService.addComment(post.getId(), ""));
        assertThrows(InvalidCommentException.class, () -> blogPostService.addComment(post.getId(), "a".repeat(256)));
    }
}