package org.prosigliere.blogpost.service;

import jakarta.transaction.Transactional;
import org.prosigliere.blogpost.exception.RecordNotFoundException;
import org.prosigliere.blogpost.model.entity.Comment;
import org.prosigliere.blogpost.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findCommentById(Long id) throws RecordNotFoundException {
        logger.info("finding comment: {}", id);
        return commentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Comment not found"));
    }

    @Transactional
    public void updateComment(Comment comment) {
        logger.info("updating comment: {}", comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        logger.info("deleting comment: {}", id);
        commentRepository.deleteById(id);
    }
}
