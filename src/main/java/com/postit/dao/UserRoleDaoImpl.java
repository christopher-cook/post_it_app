package com.postit.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.UserRole;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public UserRole getRole(String userRoleName) {

    UserRole userRole = null;

    Session session = sessionFactory.getCurrentSession();

    try {
      session.beginTransaction();

      userRole = (UserRole) session
          .createQuery("FROM UserRole r WHERE r.name = '" + userRoleName + "'").uniqueResult();
    } finally {
      session.close();
    }

    return userRole;
  }

}
