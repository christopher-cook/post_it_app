package com.postit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.postit.config.JwtUtil;
import com.postit.dao.UserDao;
import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.entity.UserRole;
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.LoginException;
import com.postit.exception.SignUpException;

public class UserServiceTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule().silent();

  @InjectMocks
  UserServiceImpl userService;

  @InjectMocks
  private User user;

  @InjectMocks
  private Post post;

  @InjectMocks
  private Comment comment;

  @Mock
  UserDao userDao;

  @Mock
  JwtUtil jwtUtil;

  @Mock
  private PasswordEncoder bCryptPasswordEncoder;

  List<Post> postList;
  List<Comment> commentList;

  @Before
  public void initMockBuild() {

    postList = new ArrayList<Post>();
    commentList = new ArrayList<Comment>();

    UserRole userRole = new UserRole();

    userRole.setName("ROLE_ADMIN");
    user.setUserId(1L);
    user.setEmail("testEmail@email.com");
    user.setUsername("testUser");
    user.setPassword("testPass");
    user.setUserRole(userRole);

    post.setPostId(1L);
    post.setTitle("title");
    post.setDescription("content");

    comment.setCommentId(1L);
    comment.setText("comment");
  }

  @Test
  public void signup_ReturnToken_Success() throws SignUpException, EmptyFieldException {

    String expectedToken = "returnToken";

    when(userDao.signup(any())).thenReturn(user);
    when(userDao.getUserByUsername(anyString())).thenReturn(null);
    when(userDao.getUserByUsernameForUserDetails(anyString())).thenReturn(user);
    when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
    when(bCryptPasswordEncoder.encode(anyString())).thenReturn("testPass");

    String actualToken = userService.signup(user);

    assertEquals(expectedToken, actualToken);
  }

  @Test(expected = SignUpException.class)
  public void signup_ReturnToken_SignUpException() throws SignUpException, EmptyFieldException {

    user.setUserId(null);
    when(userDao.signup(any())).thenReturn(user);
    when(userDao.getUserByUsername(anyString())).thenReturn(null);
    when(bCryptPasswordEncoder.encode(anyString())).thenReturn("testPass");

    userService.signup(user);
  }

  @Test
  public void login_ReturnToken_Success()
      throws EntityNotFoundException, LoginException, EmptyFieldException {

    String expectedToken = "returnToken";

    when(userDao.login(any())).thenReturn(user);
    when(userDao.getUserByUsername(anyString())).thenReturn(user);
    when(userDao.getUserByUsernameForUserDetails(anyString())).thenReturn(user);
    when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
    when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testPass");
    when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);

    String actualToken = userService.login(user);

    assertEquals(actualToken, expectedToken);
  }

  @Test(expected = EmptyFieldException.class)
  public void login_EmptyUsername_Caught()
      throws EmptyFieldException, LoginException, EntityNotFoundException {

    User wrongUserNoUsername = new User();
    userService.login(wrongUserNoUsername); // empty/missing email exception
  }

  @Test(expected = LoginException.class)
  public void login_WrongUsernamePwd_LoginException()
      throws EmptyFieldException, LoginException, EntityNotFoundException {

    User user = new User();
    user.setEmail("email1@email.com");
    user.setPassword("pwd1");

    when(userDao.login(any())).thenReturn(null);

    userService.login(user);
  }

  @Test(expected = SignUpException.class)
  public void signup_Null_Throw() throws SignUpException, EmptyFieldException {

    String expectedToken = "returnToken";

    when(userDao.getUserByUsername(anyString())).thenReturn(user);
    when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testPass");

    String actualToken = userService.signup(user);

    assertEquals(actualToken, expectedToken);
  }

  @Test(expected = SignUpException.class)
  public void signup_DuplicateEmail_Caught() throws SignUpException, EmptyFieldException {

    when(userDao.getUserByEmail(any())).thenReturn(user);
    userService.signup(user);
  }

  @Test(expected = SignUpException.class)
  public void signup_DuplicateUsername_Caught() throws SignUpException, EmptyFieldException {

    when(userDao.getUserByUsername(anyString())).thenReturn(user);
    when(userDao.getUserByUserId(any())).thenReturn(user);

    userService.signup(user);
  }

  @Test
  public void listAll_Users_Success() {

    List<User> userList = new ArrayList<User>();
    userList.add(user);

    when(userDao.listUsers()).thenReturn(userList);

    List<User> actualUserList = userService.listUsers();

    assertEquals(1, actualUserList.size());
  }

  @Test
  public void getUser_ByUserId_Success() {

    when(userDao.getUserByUserId(any())).thenReturn(user);
    User actualUser = userService.getUserByUserId(1L);

    assertEquals(actualUser, user);
  }

  @Test
  public void getUser_ByEmail_Success() {

    when(userDao.getUserByEmail(any())).thenReturn(user);
    User actualUser = userService.getUserByEmail("testUser");

    assertEquals(actualUser, user);
  }

  @Test
  public void loadUserByUsername_UserDetails_Success() {

    when(userDao.getUserByUsernameForUserDetails(anyString())).thenReturn(user);
    when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("robin");

    UserDetails userDetails = userService.loadUserByUsername("batman");

    assertEquals(user.getUsername(), userDetails.getUsername());
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_UserDetails_UserNotFound() {

    userService.loadUserByUsername("batman");
  }

  @Test
  public void getPostsByUser_PostList_Success() {

    postList.add(post);
    when(userDao.getPostsByUser(anyString())).thenReturn(postList);
    List<Post> actualPostList = userService.getPostsByUser("user1");
    assertEquals(1, actualPostList.size());
  }

  @Test
  public void getCommentsByUser_CommentList_Success() {

    commentList.add(comment);
    when(userDao.getCommentsByUser(anyString())).thenReturn(commentList);
    List<Comment> actualCommentList = userService.getCommentsByUser("user1");
    assertEquals(1, actualCommentList.size());
  }

  @Test
  public void getUserByUsername_User_Success() {

    when(userDao.getUserByUsername(anyString())).thenReturn(user);
    User actualUser = userService.getUserByUsername("user1");
    assertEquals("testUser", actualUser.getUsername());
  }
}
