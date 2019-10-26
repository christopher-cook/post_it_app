package com.postit.dao;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.postit.entity.User;
import com.postit.entity.UserRole;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleDaoTest {
  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private Query<UserRole> query;

  @InjectMocks
  UserRole userRole;

  @InjectMocks
  UserRoleDaoImpl userRoleDao;

  @InjectMocks
  User user;

  List<User> users;

  @Before
  public void init() {

    users = new ArrayList<User>();

    userRole.setName("ROLE_ADMIN");
    user.setUserId(1L);
    user.setUsername("user1");
    user.setUserRole(userRole);
    users.add(user);
    userRole.setUsers(users);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
  }

  @Test
  public void getRole_ByName_Success() {

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.uniqueResult()).thenReturn(userRole);

    UserRole actualUserRole = userRoleDao.getRole("testRole");

    assertEquals(userRole.getRoleId(), actualUserRole.getRoleId());
    assertEquals(userRole.getName(), actualUserRole.getName());
    assertEquals(userRole.getUsers().get(0).getUserId(),
        actualUserRole.getUsers().get(0).getUserId());
  }
}
