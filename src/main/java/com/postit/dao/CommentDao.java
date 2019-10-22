package com.postit.dao;

import java.util.List;

import com.postit.entity.Comment;

public interface CommentDao {
	
		public List<Comment> listComments();

		public Comment createComment(Comment comment);
		
		public Long deleteComment(Long commentId);


}
