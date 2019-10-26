package com.postit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.service.PostService;
import com.postit.utils.SecurityUtils;

public class PostControllerTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  private MockMvc mockMvc;

  @InjectMocks
  private PostController postController;

  @InjectMocks
  private Post post;

  @InjectMocks
  private Comment comment;

  @Mock
  private SecurityUtils securityUtils;

  @Mock
  private PostService postService;

  List<Post> postList;
  List<Comment> commentList;

  @Before
  public void init() {

    mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

    post.setPostId(1L);
    post.setTitle("title");
    post.setDescription("content");

    comment.setCommentId(1L);
    comment.setText("comment1");

    postList = new ArrayList<Post>();
    postList.add(post);
    commentList = new ArrayList<Comment>();
    commentList.add(comment);

    when(securityUtils.getAuthenticatedUsername()).thenReturn("user1");
  }

  @Test
  public void createPost_NewPost_Success() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/post")
        .contentType(MediaType.APPLICATION_JSON).content(createJson("title", "content"));

    when(postService.createPost(anyString(), any())).thenReturn(post);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content()
            .json("{\"postId\":1,\"title\":\"title\",\"description\":\"content\",\"user\":null}"))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());

  }

  @Test
  public void listPosts_ListOfPost_Success() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/post/list");

    when(postService.listPosts()).thenReturn(postList);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content()
            .json("[{\"postId\":1,\"title\":\"title\",\"description\":\"content\",\"user\":null}]"))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getCommentsByPostId_CommentList_Success() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/post/1/comment");

    when(postService.getCommentsByPostId(anyLong())).thenReturn(commentList);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content().json("[{\"commentId\":1,\"text\":\"comment1\",\"user\":null}]"))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void deletePostByPostId_Long_Success() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/post/1");

    when(postService.deletePostByPostId(anyString(), anyLong())).thenReturn(1L);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content().string("1")).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  private String createJson(String title, String description) {

    return "{\"title\":\"" + title + "\"," + "\"description\":\"" + description + "\"" + "}";
  }
}
