package com.postit.dao;

import java.util.List;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.SignUpException;

public interface UserDao {

  public User signup(User user) throws SignUpException;

  public User login(User user) throws EntityNotFoundException;

  public List<User> listUsers();

  public User getUserByUsername(String username);

  public User getUserByUserId(Long userId);
  
  public User getUserByEmail(String email);

  public List<Post> getPostsByUser(String username);

  public List<Comment> getCommentsByUser(String username);

  public User getUserByUsernameForUserDetails(String username);
}
