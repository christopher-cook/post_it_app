package com.postit.service;

import java.util.List;

import com.postit.entity.Comment;

public interface CommentService {
	public List<Comment> listComments();

	public Comment createComment(String username, Comment comment, Long postId);

	public Long deleteComment(String username, Long commentId);
	
}
