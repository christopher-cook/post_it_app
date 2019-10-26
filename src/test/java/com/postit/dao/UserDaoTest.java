package com.postit.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

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

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.entity.UserRole;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.SignUpException;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

  @InjectMocks
  private User user;

  @InjectMocks
  private Post post;

  @InjectMocks
  private Comment comment;

  @InjectMocks
  private UserRole userRole;

  @Mock
  private UserRoleDao userRoleDao;

  @InjectMocks
  private UserDaoImpl userDao;

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private Query<User> query;

  private List<User> userList;
  private List<Post> postList;
  private List<Comment> commentList;

  @Before
  public void initSession() {

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getTransaction()).thenReturn(transaction);
  }

  @Before
  public void initTestUser() {

    userList = new ArrayList<User>();
    postList = new ArrayList<Post>();
    commentList = new ArrayList<Comment>();

    userRole.setRoleId(1);
    userRole.setName("ROLE_ADMIN");
    user.setUserId(1L);
    user.setUsername("chris");
    user.setEmail("testEmail");
    user.setPassword("testPass");
    user.setUserRole(userRole);

    post.setPostId(1L);
    post.setTitle("title");
    post.setDescription("content");

    comment.setCommentId(1L);
    comment.setText("comment");
  }

  @Test
  public void signUp_User_Success() throws SignUpException {

    when(userRoleDao.getRole(anyString())).thenReturn(userRole);

    User tempUser = userDao.signup(user);
    assertEquals(tempUser.getUsername(), user.getUsername());
  }

  @Test(expected = SignUpException.class)
  public void signUp_User_SignUpException() throws SignUpException {

    when(session.save(any(User.class))).thenThrow(new RuntimeException("test"));
    userDao.signup(user);
  }

  @Test
  public void login_User_Success() throws EntityNotFoundException {

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getSingleResult()).thenReturn(user);

    User savedUser = userDao.login(user);
    assertEquals(savedUser, user);
  }

  @Test(expected = EntityNotFoundException.class)
  public void login_User_LoginException() throws EntityNotFoundException {

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getSingleResult()).thenThrow(new RuntimeException());

    userDao.login(user);
  }

  @Test
  public void getUser_ByUsername_Success() {

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.uniqueResult()).thenReturn(user);

    User tempUser = userDao.getUserByUsername(user.getUsername());
    assertEquals("chris", tempUser.getUsername());
  }

  @Test
  public void getUser_ByEmail_Success() {

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.uniqueResult()).thenReturn(user);

    User tempUser = userDao.getUserByEmail(user.getEmail());
    assertEquals("testEmail", tempUser.getEmail());
  }

  @Test
  public void getUser_ById_Success() {

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.uniqueResult()).thenReturn(user);

    User tempUser = userDao.getUserByUserId(user.getUserId());
    assertEquals(new Long(1), tempUser.getUserId());
  }

  @Test
  public void getPost_ByUser_Success() {

    postList.add(new Post());

    user.setPostList(postList);
    when(session.createQuery(anyString())).thenReturn(query);
    when(query.uniqueResult()).thenReturn(user);

    List<Post> actualPosts = userDao.getPostsByUser("user1");
    assertEquals(1, actualPosts.size());
  }

  @Test
  public void getComment_ByUsername_Success() {

    commentList.add(new Comment());

    user.setCommentList(commentList); // set the list to user
    when(session.createQuery(anyString())).thenReturn(query);
    when(query.uniqueResult()).thenReturn(user);

    List<Comment> actualComment = userDao.getCommentsByUser("user1");
    assertEquals(1, actualComment.size());
  }

  @Test
  public void listUsers_UserList_Success() {

    userList.add(user);

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getResultList()).thenReturn(userList);

    List<User> actualUserList = userDao.listUsers();

    assertEquals(userList, actualUserList);
  }
}
