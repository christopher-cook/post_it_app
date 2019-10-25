package com.postit.controller;

import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.entity.Comment;
import com.postit.entity.JwtResponse;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.LoginException;
import com.postit.exception.SignUpException;
import com.postit.service.UserService;
import com.postit.utils.SecurityUtils;

@RestController
@RequestMapping("/user")
public class UserController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private SecurityUtils securityUtils;
  
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody User user) throws SignUpException, EmptyFieldException {
    return ResponseEntity.ok(new JwtResponse(userService.signup(user), user.getUsername()));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) throws LoginException, EntityNotFoundException, EmptyFieldException {

    String token = userService.login(user);
    String username = userService.getUserByEmail(user.getEmail()).getUsername();
    System.out.println("token is: " + token);
    return ResponseEntity.ok(new JwtResponse(token, username));
  }

  @GetMapping("/list")
  public List<User> listUsers() {

    return userService.listUsers();
  }

//  @GetMapping("/{userId}")
//  public User getUserByUserId(@PathVariable Long userId) {
//
//    return userService.getUserByUserId(userId);
//  }
//
//  @DeleteMapping("/{userId}")
//  public Long deleteUser(@PathVariable Long userId) {
//
//    return userService.deleteUser(userId);
//  }

  @GetMapping("/post")
  public List<Post> getPostsByUser() {
    String username = securityUtils.getAuthenticatedUsername();
    return userService.getPostsByUser(username);
  }
  
  @GetMapping("/comment")
  public List<Comment> getCommentsByUser() {
	  String username = securityUtils.getAuthenticatedUsername();
	  return userService.getCommentsByUser(username);
  }
}



