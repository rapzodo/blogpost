package org.prosigliere.blogpost.repository;

import org.prosigliere.blogpost.model.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
