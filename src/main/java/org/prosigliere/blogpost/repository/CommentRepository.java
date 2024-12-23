package org.prosigliere.blogpost.repository;

import org.prosigliere.blogpost.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
