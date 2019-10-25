package com.postit.dao;

import java.util.List;

import com.postit.entity.Comment;
import com.postit.exception.EntityNotFoundException;

public interface CommentDao {

		public Comment createComment(String username, Comment comment, Long postId);
		
		public Long deleteComment(String username, Long commentId) throws EntityNotFoundException;


}
