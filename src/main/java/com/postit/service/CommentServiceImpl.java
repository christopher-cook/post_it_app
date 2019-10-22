package com.postit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.dao.CommentDao;
import com.postit.entity.Comment;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<Comment> listComments() {
		return commentDao.listComments();
	}

	@Override
	public Comment createComment(String username, Comment comment, Long postId) {
		return commentDao.createComment(username, comment, postId);
	}

	@Override
	public Long deleteComment(Long commentId) {
		return commentDao.deleteComment(commentId);
	}

}
