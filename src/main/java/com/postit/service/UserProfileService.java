package com.postit.service;

import com.postit.entity.UserProfile;

public interface UserProfileService {

  public UserProfile createProfile(String username, UserProfile userProfile);

  public UserProfile updateProfile(String username, UserProfile userProfile);

  public UserProfile getUserProfile(String username);
}
