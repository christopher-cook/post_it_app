package com.postit.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.postit.entity.User;
import com.postit.entity.UserProfile;
import com.postit.exception.EntityNotFoundException;

public class UserProfileDaoTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @InjectMocks
  User user;

  @InjectMocks
  UserProfile userProfile;

  @InjectMocks
  UserProfileDaoImpl userProfileDao;

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private UserDao userDao;

  @Before
  public void init() {

    userProfile.setProfileId(1L);
    userProfile.setAdditionalEmail("email1@email.com");
    userProfile.setMobile("111111");
    userProfile.setAddress("amazon");

    user.setUserId(1L);
    user.setUsername("user1");
    user.setUserProfile(userProfile);
    userProfile.setUser(user);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getTransaction()).thenReturn(transaction);
    when(userDao.getUserByUsername(anyString())).thenReturn(user);
  }

  @Test
  public void createProfile_UserProfile_Success() {

    UserProfile actualUserProfile = userProfileDao.createProfile("user1", userProfile);

    assertEquals(userProfile, actualUserProfile);
  }

  @Test
  public void getUserProfile_UserProfile_Success() throws EntityNotFoundException {

    UserProfile actualUserProfile = userProfileDao.getUserProfile("user1");
    assertEquals("user1", actualUserProfile.getUser().getUsername());
  }

  @Test
  public void getUserProfile_UserProfile_EntityNotFound() throws EntityNotFoundException {

    user.setUserProfile(null);
    UserProfile actualUserProfile = userProfileDao.getUserProfile("user1");
    assertNull(actualUserProfile);
  }

  @Test
  public void updateUserProfile_UserProfile_Success() {

    userProfile.setMobile("222222");
    UserProfile actualUserProfile = userProfileDao.updateProfile("user1", userProfile);

    assertEquals(userProfile.getMobile(), actualUserProfile.getMobile());
  }
}
