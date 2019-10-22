package com.postit.dao;

import java.util.List;

import com.postit.entity.Post;

public interface PostDao {
  public Post createPost(String username, Post post);
  public Long deletePostByPostId(String username, Long postId);
  public List<Post> listPosts();
//  public listPostsByUser();
}
