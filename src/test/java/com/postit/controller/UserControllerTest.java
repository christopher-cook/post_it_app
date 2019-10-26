package com.postit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.service.UserService;
import com.postit.utils.SecurityUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  UserController userController;

  @InjectMocks
  User user;

  @InjectMocks
  Post post;

  @InjectMocks
  Comment comment;

  @Mock
  UserService userService;

  @Mock
  SecurityUtils securityUtils;

  List<User> userList;
  List<Post> postList;
  List<Comment> commentList;

  @Before
  public void initMockBuild() {

    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    userList = new ArrayList<User>();
    postList = new ArrayList<Post>();
    commentList = new ArrayList<Comment>();

    user.setEmail("testEmail");
    user.setPassword("testPass");
    user.setUsername("testUser");

    post.setPostId(1L);
    post.setTitle("title");
    post.setDescription("content");

    comment.setCommentId(1L);
    comment.setText("comment");
  }

  @Test
  public void signup_User_Success() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON)
            .content(createUserJson("testEmail", "testUser", "testPass"));

    when(userService.signup(any())).thenReturn("testReturnString");

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content().json("{\"token\":\"testReturnString\"}")).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void login_User_Success() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON)
            .content(createUserJson("testEmail@email.com", "testUser", "testPass"));

    when(userService.login(any())).thenReturn("testString");
    when(userService.getUserByEmail(anyString())).thenReturn(user);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content().json("{\"username\":\"testUser\", \"token\":\"testString\"}"))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void listUsers_UserList_Success() throws Exception {

    userList.add(user);
    
    when(userService.listUsers()).thenReturn(userList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/list");

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(content().json("[{\"username\":\"testUser\"}]")).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getPostsByUser_PostList_Success() throws Exception {

    postList.add(post);

    when(userService.getPostsByUser(anyString())).thenReturn(postList);
    when(securityUtils.getAuthenticatedUsername()).thenReturn("user1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/post");

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(content()
            .json("[{\"postId\":1,\"title\":\"title\",\"description\":\"content\",\"user\":null}]"))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getCommentsByUser_CommentList_Success() throws Exception {

    commentList.add(comment);

    when(userService.getCommentsByUser(anyString())).thenReturn(commentList);
    when(securityUtils.getAuthenticatedUsername()).thenReturn("user1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/comment");

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(content().json("[{\"commentId\":1,\"text\":\"comment\",\"user\":null}]"))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  private static String createUserJson(String email, String username, String password) {

    return "{ \"email\": \"" + email + "\", " + "\"username\": \"" + username + "\", "
        + "\"password\":\"" + password + "\"}";
  }
}
