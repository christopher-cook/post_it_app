package com.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.UserRole;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.SignUpException;
import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private UserRoleDao userRoleDao;

  @Override
  public User signup(User user) throws SignUpException {

    String roleName = "ROLE_USER";
    UserRole userRole = userRoleDao.getRole(roleName);

    Session session = sessionFactory.getCurrentSession();

    try {
      session.beginTransaction();

      user.setUserRole(userRole);
      session.save(user);

      session.getTransaction().commit();
    }
    catch (Exception e) {
      throw new SignUpException(e.getMessage());
    } finally {
      session.close();
    }

    return user;
  }

  @Override
  public User login(User user) throws EntityNotFoundException {

    User savedUser = null;

    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();

      savedUser = (User) session
          .createQuery("FROM User u WHERE u.email = '" + user.getEmail() + "'").getSingleResult();
    } catch (Exception e) {
      throw new EntityNotFoundException("login error: entity not found");
    } finally {
      session.close();
    }

    return savedUser;
  }

  @Override
  public List<User> listUsers() {

    List<User> allUsers = null; // init list
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

    User user = null;

    Session session = sessionFactory.getCurrentSession();

    try {
      session.beginTransaction();

      user = (User) session.createQuery("FROM User u WHERE u.userId = '" + userId + "'")
          .uniqueResult();
    } finally {
      session.close();
    }
    return user;
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

  @Override
  public Long deleteUser(Long userId) {

    Session session = sessionFactory.getCurrentSession();
    User user = null;

    try {
      session.beginTransaction();

      user = session.get(User.class, userId);
      session.delete(user);

      session.getTransaction().commit();
    } finally {
      session.close();
    }
    return userId;
  }

  @Override
  public List<Post> getPostsByUser(String username) {

    List<Post> postList = null;
    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();
      User user = (User) session.createQuery("FROM User u WHERE u.username = '" + username + "'")
          .uniqueResult();
      postList = user.getPostList();
    } finally {
      session.close();
    }
    return postList;
  }

  @Override
  public List<Comment> getCommentsByUser(String username) {

    List<Comment> commentList = null;

    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();
      User user = (User) session.createQuery("FROM User u WHERE u.username = '" + username + "'")
          .uniqueResult();
      commentList = user.getCommentList();

    } finally {
      session.close();
    }
    return commentList;
  }

  @Override
  public User getUserByUsernameForUserDetails(String username) {
    return this.getUserByUsername(username);
  }

}
