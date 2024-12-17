package org.prosigliere.blogpost.repository;

import org.prosigliere.blogpost.model.entity.BlogPost;
import org.springframework.data.repository.ListCrudRepository;

public interface BlogPostRepository extends ListCrudRepository<BlogPost, Long> {
}
