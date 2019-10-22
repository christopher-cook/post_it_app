package com.postit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping("/{commentId}")
	public Comment createComment(@RequestBody Comment comment) {
		return commentService.createComment(comment);
	}
	
//	@GetMapping("/{commentId}")
//	public Comment getComment(@RequestBody Comment comment) {
//		return commentService.getComment(comment);
//	}
	
	@DeleteMapping("/{commentId}")
	public Long deleteComment(@RequestBody Long commentId) {
		return commentService.deleteComment(commentId);
	}
	
}
