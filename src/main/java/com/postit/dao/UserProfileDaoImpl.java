package com.postit.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.User;
import com.postit.entity.UserProfile;
import com.postit.exception.EntityNotFoundException;

@Repository
public class UserProfileDaoImpl implements UserProfileDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private UserDao userDao;

  @Override
  public UserProfile createProfile(String username, UserProfile userProfile) {

    User user = userDao.getUserByUsername(username);

    Session session = sessionFactory.getCurrentSession();

    try {
      session.beginTransaction();
      session.save(userProfile);
      user.setUserProfile(userProfile);
      session.update(user);

      session.getTransaction().commit();
    } finally {
      session.close();
    }

    return userProfile;
  }

  @Override
  public UserProfile getUserProfile(String username) throws EntityNotFoundException {

    User user = userDao.getUserByUsername(username);
    UserProfile userProfile = user.getUserProfile();
    if(userProfile == null){
      throw new EntityNotFoundException("the profile for this user doesn't exist");
    }
    userProfile.setUser(user);
    
    return userProfile;
  }

  @Override
  public UserProfile updateProfile(String username, UserProfile userProfile) {

    User user = userDao.getUserByUsername(username);
    Session session = sessionFactory.getCurrentSession();
    // only mobile is for update, so pre-set email and address
    UserProfile previousProfile = user.getUserProfile();
    previousProfile.setMobile(userProfile.getMobile());
    userProfile = previousProfile;
    
    try {
      session.beginTransaction();
      session.update(userProfile);
      user.setUserProfile(userProfile);
      session.update(user);

      session.getTransaction().commit();
    } finally {
      session.close();
    }

    return userProfile;
  }

}
