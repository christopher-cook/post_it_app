package com.postit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.dao.UserProfileDao;
import com.postit.entity.UserProfile;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  @Autowired
  private UserProfileDao userProfileDao;
  
  @Override
  public UserProfile createProfile(String username, UserProfile userProfile) {

    return userProfileDao.createProfile(username, userProfile);
  }
  
  @Override
  public UserProfile updateProfile(String username, UserProfile userProfile) {

    return userProfileDao.updateProfile(username, userProfile);
  }

  @Override
  public UserProfile getUserProfile(String username) {

    return userProfileDao.getUserProfile(username);
  }

}
