package com.postit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.postit.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody User user) {

    if (user.getEmail() == null || user.getUsername() == null || user.getPassword() == null) {
      return ResponseEntity.badRequest().body("invalid arguments");
    }
    User nameSearch = userService.getUserByUsername(user.getUsername());
    if (nameSearch != null) {
      return ResponseEntity.badRequest().body("username already exists");
    }
    User emailSearch = userService.getUserByEmail(user.getEmail());
    if (emailSearch != null) {
      return ResponseEntity.badRequest().body("email already exists");
    }
    return ResponseEntity.ok(new JwtResponse(userService.signup(user), user.getUsername()));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {

    String token = userService.login(user);
    if (token == null) {
      return ResponseEntity.badRequest().body("username/password invalid");
    }
    return ResponseEntity.ok(new JwtResponse(token, user.getUsername()));
  }

  @GetMapping("/list")
  public List<User> listUsers() {

    return userService.listUsers();
  }

  @GetMapping("/{userId}")
  public User getUserByUserId(@PathVariable Long userId) {

    return userService.getUserByUserId(userId);
  }

  @DeleteMapping("/{userId}")
  public Long deleteUser(@PathVariable Long userId) {

    return userService.deleteUser(userId);
  }

  @GetMapping("/post")
  public List<Post> getPostsByUser(Authentication auth) {

    if (auth == null) {
      return null;
    }
    String username = auth.getName();
    return userService.getPostsByUser(username);
  }
  
  @GetMapping("/comment")
  public List<Comment> getCommentsByUser(Authentication auth) {
	  
	  if(auth == null) {
		  return null;
	  }
	  String username = auth.getName();
	  return userService.getCommentsByUser(username);
  }
}



