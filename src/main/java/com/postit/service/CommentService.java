package com.postit.service;

import com.postit.entity.Comment;
import com.postit.exception.EntityNotFoundException;

public interface CommentService {
  public Comment createComment(String username, Comment comment, Long postId);

  public Long deleteComment(String username, Long commentId) throws EntityNotFoundException;

}
