package com.postit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.exception.EntityNotFoundException;
import com.postit.service.PostService;
import com.postit.utils.SecurityUtils;

@RestController
@RequestMapping("/post")
public class PostController {

  private PostService postService;

  @Autowired
  public void setPostService(PostService postService) {

    this.postService = postService;
  }

  @Autowired
  private SecurityUtils securityUtils;

  @PostMapping("")
  public Post createPost(@Valid @RequestBody Post post) {

    String username = securityUtils.getAuthenticatedUsername();
    return postService.createPost(username, post);
  }

  @DeleteMapping("/{postId}")
  public Long deletePost(@PathVariable Long postId) throws EntityNotFoundException {

    String username = securityUtils.getAuthenticatedUsername();
    return postService.deletePostByPostId(username, postId);
  }

  @GetMapping("/list")
  public List<Post> listPosts() {

    return postService.listPosts();
  }

  @GetMapping("/{postId}/comment")
  public List<Comment> getCommentsByPostId(@PathVariable Long postId)
      throws EntityNotFoundException {

    return postService.getCommentsByPostId(postId);
  }

}
