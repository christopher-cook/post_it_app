package com.postit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;
import com.postit.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

  @Autowired
  private PostService postService;

  @PostMapping("")
  public Post createPost(Authentication auth,@Valid @RequestBody Post post) {
    String username = auth.getName();
    return postService.createPost(username, post);
  }

  @DeleteMapping("/{postId}")
  public Long deletePost(Authentication auth, @PathVariable Long postId) throws EntityNotFoundException{
    String username = auth.getName();
    return postService.deletePostByPostId(username, postId);
  }
  
  @GetMapping("/list")
  public List<Post> listPosts() {
    return postService.listPosts();
  }
  
  @GetMapping("/{postId}/comment")
  public List<Comment> getCommentsByPostId(@PathVariable Long postId) throws EntityNotFoundException {
	return postService.getCommentsByPostId(postId);
  }
  
}
