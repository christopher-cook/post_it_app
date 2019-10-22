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
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.entity.UserRole;

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

    User user = userDao.getUserByUsername(username);

    if (user == null)
      throw new UsernameNotFoundException("Unkknown user: " + username);

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
  public String signup(User user) {

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    if (userDao.signup(user).getUserId() != null) {
      UserDetails userDetails = loadUserByUsername(user.getUsername());

      return jwtUtil.generateToken(userDetails);
    }

    return null;
  }

  @Override
  public String login(User user) {

    User foundUser = userDao.login(user);
    if (foundUser != null && foundUser.getUserId() != null
        && bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
      UserDetails userDetails = loadUserByUsername(foundUser.getUsername());

      return jwtUtil.generateToken(userDetails);
    }

    return null;
  }

  @Override
  public Long deleteUser(Long userId) {

    return userDao.deleteUser(userId);
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

}
