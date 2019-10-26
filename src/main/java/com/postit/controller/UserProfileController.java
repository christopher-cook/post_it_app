package com.postit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.entity.UserProfile;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;
import com.postit.service.UserProfileService;
import com.postit.utils.SecurityUtils;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

  private UserProfileService userProfileService;

  @Autowired
  public void setUserProfileService(UserProfileService userProfileService) {

    this.userProfileService = userProfileService;
  }

  @Autowired
  private SecurityUtils securityUtils;

  @PostMapping("")
  public UserProfile createOrUpdateProfile(@RequestBody UserProfile userProfile)
      throws EmptyFieldException, EntityNotFoundException {

    String username = securityUtils.getAuthenticatedUsername();
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
  public UserProfile getProfile() throws EntityNotFoundException {

    String username = securityUtils.getAuthenticatedUsername();
    return userProfileService.getUserProfile(username);
  }
}
