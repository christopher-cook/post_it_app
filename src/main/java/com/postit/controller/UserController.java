package com.postit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.entity.JwtResponse;
import com.postit.entity.User;
import com.postit.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody User user) {
	  User nameSearch = userService.getUserByUsername(user.getUsername());
	  if(nameSearch != null) {
		  return ResponseEntity.badRequest().body("user already exists");
	  }
    return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
  }
  
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
	  String token = userService.login(user);
	  if(token == null) {
      return ResponseEntity.badRequest().body("username/password invalid");
  }
	  return ResponseEntity.ok(new JwtResponse(token));
  }
 
}
