package com.postit.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.LoginException;
import com.postit.exception.SignUpException;

public interface UserService extends UserDetailsService {

  public String signup(User user) throws SignUpException, EmptyFieldException;

  public String login(User user)
      throws LoginException, EntityNotFoundException, EmptyFieldException;

  public List<User> listUsers();

  public User getUserByUsername(String username);

  public User getUserByUserId(Long userId);

  public User getUserByEmail(String email);

  public List<Post> getPostsByUser(String username);

  public List<Comment> getCommentsByUser(String username);
}
