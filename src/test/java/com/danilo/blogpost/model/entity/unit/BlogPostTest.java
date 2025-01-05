package com.danilo.blogpost.model.entity.unit;

import org.junit.jupiter.api.Test;
import com.danilo.blogpost.model.entity.BlogPost;
import com.danilo.blogpost.model.entity.Comment;
import com.danilo.blogpost.model.record.BlogPostResponse;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlogPostTest {

    @Test
    public void shouldMapToBlogPostResponse() {
        // given
        BlogPost blogPost = new BlogPost("Test Title", "Test Content");
        blogPost.setId(1L);
        blogPost.setComments(Stream.of(new Comment("some content")).toList());

        // when
        BlogPostResponse response = blogPost.toBlogPostResponse();

        // then
        assertEquals(blogPost.getId(), response.id());
        assertEquals(blogPost.getTitle(), response.title());
        assertEquals(blogPost.getContent(), response.content());
        assertEquals(blogPost.getComments().size(), response.comments().size());
    }
}