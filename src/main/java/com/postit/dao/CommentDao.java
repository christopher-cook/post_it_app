package com.postit.dao;

import java.util.List;

import com.postit.entity.Comment;

public interface CommentDao {
	
		public List<Comment> listComments();

		public Comment createComment(String username, Comment comment, Long postId);
		
		public Long deleteComment(String username, Long commentId);


}
