package com.postit.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.postit.dao.UserProfileDao;
import com.postit.entity.UserProfile;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;

public class UserProfileServiceTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @InjectMocks
  UserProfileServiceImpl userProfileService;

  @InjectMocks
  UserProfile userProfile;

  @Mock
  UserProfileDao userProfileDao;

  @Before
  public void init() {

    userProfile.setProfileId(1L);
    userProfile.setAdditionalEmail("email1@email.com");
    userProfile.setMobile("111111");
    userProfile.setAddress("amazon");

    // user.setUserId(1L);
    // user.setUsername("user1");
    // userProfile.setUser(user);
  }

  @Test
  public void createUserProfile_UserProfile_Success() throws EmptyFieldException {

    userProfile.setProfileId(null);
    userProfile.setUser(null);

    when(userProfileDao.createProfile(anyString(), any())).thenReturn(userProfile);

    UserProfile actualUserProfile = userProfileService.createProfile("user1", userProfile);

    assertEquals(userProfile.getMobile(), actualUserProfile.getMobile());
  }

  @Test(expected = EmptyFieldException.class)
  public void createUserProfile_UserProfile_NoEmail() throws EmptyFieldException {
    userProfile.setAdditionalEmail(null);
    userProfileService.createProfile("user1", userProfile);
  }
  
  @Test(expected = EmptyFieldException.class)
  public void createUserProfile_UserProfile_EmptyEmail() throws EmptyFieldException {
    userProfile.setAdditionalEmail("");
    userProfileService.createProfile("user1", userProfile);
  }
  
  @Test(expected = EmptyFieldException.class)
  public void createUserProfile_UserProfile_NoMobile() throws EmptyFieldException {
    userProfile.setMobile(null);
    userProfileService.createProfile("user1", userProfile);
  }
  
  @Test(expected = EmptyFieldException.class)
  public void createUserProfile_UserProfile_EmptyMobile() throws EmptyFieldException {
    userProfile.setMobile("");
    userProfileService.createProfile("user1", userProfile);
  }
  
  @Test(expected = EmptyFieldException.class)
  public void createUserProfile_UserProfile_NoAddress() throws EmptyFieldException {
    userProfile.setAddress(null);
    userProfileService.createProfile("user1", userProfile);
  }
  
  @Test(expected = EmptyFieldException.class)
  public void createUserProfile_UserProfile_EmptyAddress() throws EmptyFieldException {
    userProfile.setAddress("");
    userProfileService.createProfile("user1", userProfile);
  }

  @Test
  public void updateUserProfile_UserProfile_Success() throws EmptyFieldException {

    when(userProfileDao.updateProfile(anyString(), any())).thenReturn(userProfile);

    UserProfile actualUserProfile = userProfileService.updateProfile("user1", userProfile);

    assertEquals(userProfile.getMobile(), actualUserProfile.getMobile());
  }

  @Test(expected = EmptyFieldException.class)
  public void updateUserProfile_UserProfile_EmptyFieldException() throws EmptyFieldException {

    UserProfile emptyUserProfile = new UserProfile();
    userProfileService.updateProfile("user1", emptyUserProfile);
  }

  @Test
  public void getUserProfile_UserProfile_Success() throws EntityNotFoundException {

    when(userProfileDao.getUserProfile(anyString())).thenReturn(userProfile);
    UserProfile actualUserProfile = userProfileService.getUserProfile("user1");
    assertNotNull(actualUserProfile);
    assertEquals(userProfile.getProfileId(), actualUserProfile.getProfileId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void getUserProfile_UserProfile_EntityNotFoundException() throws EntityNotFoundException {

    when(userProfileDao.getUserProfile(anyString())).thenThrow(EntityNotFoundException.class);
    userProfileService.getUserProfile("user1");

  }
}
