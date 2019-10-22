package com.postit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.entity.UserProfile;
import com.postit.service.UserProfileService;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

  @Autowired
  private UserProfileService userProfileService;

  @PostMapping("")
  public UserProfile createOrUpdateProfile(Authentication auth,
      @RequestBody UserProfile userProfile) {

    if (auth == null) {
      return new UserProfile();
    }
    String username = auth.getName();
    UserProfile checkedProfile = userProfileService.getUserProfile(username);
    if (checkedProfile == null) {
      System.out.println("creating profile");
      return userProfileService.createProfile(username, userProfile);
    } else {
      System.out.println("updating profile");
      return userProfileService.updateProfile(username, userProfile);
    }

  }

  @GetMapping("")
  public UserProfile createOrUpdateProfile(Authentication auth) {

    if (auth == null) {
      return new UserProfile();
    }
    String username = auth.getName();
    return userProfileService.getUserProfile(username);
  }
}
