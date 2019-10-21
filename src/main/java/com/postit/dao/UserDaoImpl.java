package com.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.UserRole;
import com.postit.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private UserRoleDao userRoleDao;

  @Override
  public User signup(User user) {

    String roleName = "ROLE_USER";
    UserRole userRole = userRoleDao.getRole(roleName);

    Session session = sessionFactory.getCurrentSession();

    try {
      session.beginTransaction();

      user.setUserRole(userRole);
      session.save(user);

      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("signup bad request");
    } finally {
      session.close();
    }

    return user;
  }

  @Override
  public User login(User user) {

    User savedUser = null;

    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();

      savedUser =
          (User) session.createQuery("FROM User u WHERE u.username = '" + user.getUsername() + "'")
              .getSingleResult();
    } finally {
      session.close();
    }

    return savedUser;
  }

  @Override
  public String deleteUser(String username) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> listUsers() {

    List<User> allUsers = null;	//init list
    Session session = sessionFactory.getCurrentSession();
    
    try {
    	session.beginTransaction();
    	
    	allUsers = session.createQuery("FROM User").getResultList();
    } finally {
    	session.close();
    }
    return allUsers;
  }

  @Override
  public User getUserByUsername(String username) {

    User user = null;

    Session session = sessionFactory.getCurrentSession();

    try {
      session.beginTransaction();

      user = (User) session.createQuery("FROM User u WHERE u.username = '" + username + "'")
          .uniqueResult();
    } finally {
      session.close();
    }

    return user;
  }

  @Override
  public User getUserByUserId(Long userId) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getUserByEmail(String email) {

    User user = null;

    Session session = sessionFactory.getCurrentSession();

    try {
      session.beginTransaction();

      user =
          (User) session.createQuery("FROM User u WHERE u.email = '" + email + "'").uniqueResult();
    } finally {
      session.close();
    }

    return user;
  }

}
