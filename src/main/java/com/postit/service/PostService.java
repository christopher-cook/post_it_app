package com.postit.service;

import java.util.List;

import com.postit.entity.Post;

public interface PostService {

  public Post createPost(String username, Post post);

  public Long deletePostByPostId(String username, Long postId);
  
  public List<Post> listPosts();
}
