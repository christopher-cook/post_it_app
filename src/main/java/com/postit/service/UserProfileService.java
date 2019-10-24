package com.postit.service;

import com.postit.entity.UserProfile;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;

public interface UserProfileService {

  public UserProfile createProfile(String username, UserProfile userProfile) throws EmptyFieldException;

  public UserProfile updateProfile(String username, UserProfile userProfile) throws EmptyFieldException;

  public UserProfile getUserProfile(String username) throws EntityNotFoundException;
}
