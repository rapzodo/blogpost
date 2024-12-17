package org.prosigliere.blogpost.repository;

import org.prosigliere.blogpost.model.entity.Comment;
import org.springframework.data.repository.ListCrudRepository;

public interface CommentRepository extends ListCrudRepository<Comment, Long> {
}
