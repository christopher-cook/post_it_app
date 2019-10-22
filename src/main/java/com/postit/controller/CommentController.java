package com.postit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.entity.Comment;
import com.postit.service.CommentService;
import com.postit.service.UserService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/{postId}")
	public Comment createComment(Authentication auth, @RequestBody Comment comment, @PathVariable Long postId) {
		if(auth == null) {
			return new Comment();
		}
		String username = auth.getName();
		return commentService.createComment(username, comment, postId);
	}
	
	
	@DeleteMapping("/{commentId}")
	public Long deleteComment(Authentication auth, @PathVariable Long commentId) {
		if(auth == null) {
			return 0L;
		}
		String username = auth.getName();
		return commentService.deleteComment(username, commentId);
	}
	
}
