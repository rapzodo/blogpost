package com.danilo.blogpost.repository;

import com.danilo.blogpost.model.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
