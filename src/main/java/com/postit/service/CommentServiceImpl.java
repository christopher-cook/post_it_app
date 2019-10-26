package com.postit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.dao.CommentDao;
import com.postit.entity.Comment;
import com.postit.exception.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentDao commentDao;

  @Override
  public Comment createComment(String username, Comment comment, Long postId) {

    return commentDao.createComment(username, comment, postId);
  }

  @Override
  public Long deleteComment(String username, Long commentId) throws EntityNotFoundException {

    return commentDao.deleteComment(username, commentId);
  }
}
