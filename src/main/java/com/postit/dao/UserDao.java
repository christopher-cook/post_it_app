package com.postit.dao;

import java.util.List;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;

public interface UserDao {

  public User signup(User user);

  public User login(User user);

  public Long deleteUser(Long userId);

  public List<User> listUsers();

  public User getUserByUsername(String username);

  public User getUserByUserId(Long userId);
  
  public User getUserByEmail(String email);

  public List<Post> getPostsByUser(String username);

  public List<Comment> getCommentsByUser(String username);
}
