package com.postit.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;

public interface UserService extends UserDetailsService {

  public String signup(User user);

  public String login(User user);

  public Long deleteUser(Long userId);

  public List<User> listUsers();

  public User getUserByUsername(String username);

  public User getUserByUserId(Long userId);

  public User getUserByEmail(String email);

  public List<Post> getPostsByUser(String username);
}
