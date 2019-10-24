package com.postit.dao;

import com.postit.entity.UserProfile;
import com.postit.exception.EntityNotFoundException;

public interface UserProfileDao {

  public UserProfile createProfile(String username, UserProfile userProfile);
  public UserProfile updateProfile(String username, UserProfile userProfile);

  public UserProfile getUserProfile(String username) throws EntityNotFoundException;

}
