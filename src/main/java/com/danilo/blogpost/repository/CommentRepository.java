package com.danilo.blogpost.repository;

import com.danilo.blogpost.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
