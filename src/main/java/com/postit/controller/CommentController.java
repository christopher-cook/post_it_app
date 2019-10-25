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
import com.postit.utils.SecurityUtils;

@RestController
@RequestMapping("/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;
  
  @Autowired
  private SecurityUtils securityUtils;

  @PostMapping("/{postId}")
  public Comment createComment(@Valid @RequestBody Comment comment,
      @PathVariable Long postId) {
    String username = securityUtils.getAuthenticatedUsername();
    return commentService.createComment(username, comment, postId);
  }

  @DeleteMapping("/{commentId}")
  public Long deleteComment(@PathVariable Long commentId)
      throws EntityNotFoundException {
    String username = securityUtils.getAuthenticatedUsername();
    System.out.println(username);
    return commentService.deleteComment(username, commentId);
  }

}
