package com.postit.service;

import java.util.List;

import com.postit.entity.Comment;

public interface CommentService {
	public List<Comment> listComments();

	public Comment createComment(Comment comment);

//	public Comment getComment(Comment comment);

	public Long deleteComment(Long commentId);
	
}
