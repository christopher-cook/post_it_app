package com.postit.dao;

import java.util.List;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.exception.EntityNotFoundException;

public interface PostDao {
  public Post createPost(String username, Post post);
  public Long deletePostByPostId(String username, Long postId) throws EntityNotFoundException;
  public List<Post> listPosts();
  public List<Comment> getCommentsByPostId(Long postId) throws EntityNotFoundException;
}
