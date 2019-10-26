package com.postit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.postit.config.JwtUtil;
import com.postit.dao.UserDao;
import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.LoginException;
import com.postit.exception.SignUpException;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserDao userDao;

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  @Qualifier("encoder")
  PasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userDao.getUserByUsernameForUserDetails(username);

    if (user == null)
      throw new UsernameNotFoundException("Unknown user: " + username);

    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        bCryptPasswordEncoder.encode(user.getPassword()), true, true, true, true,
        getGrantedAuthorities(user));
  }

  private List<GrantedAuthority> getGrantedAuthorities(User user) {

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(user.getUserRole().getName()));
    return authorities;
  }

  @Override
  public String signup(User user) throws SignUpException, EmptyFieldException {

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    if (userDao.getUserByUsername(user.getUsername()) != null) {
      throw new SignUpException("duplicate username");
    }
    if (userDao.getUserByEmail(user.getEmail()) != null) {
      throw new SignUpException("duplicate email");
    }
    if (userDao.signup(user).getUserId() != null) {
      UserDetails userDetails = loadUserByUsername(user.getUsername());
      return jwtUtil.generateToken(userDetails);
    } else {
      throw new SignUpException("signup failed");
    }
  }

  @Override
  public String login(User user)
      throws LoginException, EntityNotFoundException, EmptyFieldException {

    if (user.getEmail() == null || user.getEmail().length() == 0) {
      throw new EmptyFieldException("empty/missing email");
    }
    if (user.getPassword() == null || user.getPassword().length() == 0) {
      throw new EmptyFieldException("empty/missing password");
    }
    User foundUser = userDao.login(user);
    if (foundUser != null && foundUser.getUserId() != null
        && bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
      UserDetails userDetails = loadUserByUsername(foundUser.getUsername());
      return jwtUtil.generateToken(userDetails);
    }
    System.out.println("username/password invalid");
    throw new LoginException("username/password invalid");
  }

  @Override
  public List<User> listUsers() {

    return userDao.listUsers();
  }

  @Override
  public User getUserByUsername(String username) {

    return userDao.getUserByUsername(username);
  }

  @Override
  public User getUserByUserId(Long userId) {

    return userDao.getUserByUserId(userId);
  }

  @Override
  public User getUserByEmail(String email) {

    return userDao.getUserByEmail(email);
  }

  @Override
  public List<Post> getPostsByUser(String username) {

    return userDao.getPostsByUser(username);
  }

  @Override
  public List<Comment> getCommentsByUser(String username) {

    return userDao.getCommentsByUser(username);
  }
}
