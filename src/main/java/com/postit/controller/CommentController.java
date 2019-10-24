package com.postit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.entity.Comment;
import com.postit.exception.EntityNotFoundException;
import com.postit.service.CommentService;
import com.postit.service.UserService;

@RestController
@RequestMapping("/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("/{postId}")
  public Comment createComment(Authentication auth, @Valid @RequestBody Comment comment,
      @PathVariable Long postId) {

    String username = auth.getName();
    return commentService.createComment(username, comment, postId);
  }

  @DeleteMapping("/{commentId}")
  public Long deleteComment(Authentication auth, @PathVariable Long commentId)
      throws EntityNotFoundException {

    String username = auth.getName();
    System.out.println(username);
    return commentService.deleteComment(username, commentId);
  }

}
