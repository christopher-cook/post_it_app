package com.postit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.dao.UserProfileDao;
import com.postit.entity.UserProfile;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  @Autowired
  private UserProfileDao userProfileDao;

  @Override
  public UserProfile createProfile(String username, UserProfile userProfile)
      throws EmptyFieldException {

    if (userProfile.getAdditionalEmail() == null
        || userProfile.getAdditionalEmail().length() == 0) {
      throw new EmptyFieldException("empty/missing email");
    } else if (userProfile.getMobile() == null || userProfile.getMobile().length() == 0) {
      throw new EmptyFieldException("empty/missing mobile");
    } else if (userProfile.getAddress() == null || userProfile.getAddress().length() == 0) {
      throw new EmptyFieldException("empty/missing address");
    }
    return userProfileDao.createProfile(username, userProfile);
  }

  @Override
  public UserProfile updateProfile(String username, UserProfile userProfile)
      throws EmptyFieldException {

    if (userProfile.getMobile() == null || userProfile.getMobile().length() == 0) {
      throw new EmptyFieldException("empty/missing mobile");
    }
    return userProfileDao.updateProfile(username, userProfile);
  }

  @Override
  public UserProfile getUserProfile(String username) throws EntityNotFoundException {

    return userProfileDao.getUserProfile(username);
  }

}
