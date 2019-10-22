package com.postit.dao;

import com.postit.entity.UserProfile;

public interface UserProfileDao {

  public UserProfile createProfile(String username, UserProfile userProfile);
  public UserProfile updateProfile(String username, UserProfile userProfile);

  public UserProfile getUserProfile(String username);

}
