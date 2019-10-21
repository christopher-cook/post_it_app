package com.postit.dao;

import java.util.List;

import com.postit.entity.User;

public interface UserDao {

  public User signup(User user);

  public User login(User user);

  public String deleteUser(String username);

  public List<User> listUsers();

  public User getUserByUsername(String username);

  public User getUserByUserId(Long userId);
  
  public User getUserByEmail(String email);
}
