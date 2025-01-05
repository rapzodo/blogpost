package com.danilo.blogpost.model.entity;

import com.danilo.blogpost.model.record.BlogPostResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @NotNull(message = "Title cannot be null or empty")
    private String title;
    @NotNull(message = "Content cannot be null or empty")
    private String content;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_post_id")
//    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    public BlogPost() {
    }

    public BlogPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public BlogPostResponse toBlogPostResponse() {
        return new BlogPostResponse(id, title, content, comments.stream().map(Comment::toCommentResponse).toList());
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    Long getVersion(){
        return version;
    }

    void setVersion(Long version){
        this.version = version;
    }
}