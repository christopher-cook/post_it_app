package com.postit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.postit.entity.User;
import com.postit.service.CommentService;
import com.postit.service.PostService;
import com.postit.utils.SecurityUtils;

public class CommentControllerTest {
	  @Rule
	  public MockitoRule rule = MockitoJUnit.rule();

	  private MockMvc mockMvc;

	  @InjectMocks
	  private CommentController commentController;

	  @InjectMocks
	  private Post post;

	  @InjectMocks
	  private Comment comment;
	  
	  @InjectMocks
	  User user;

	  @Mock
	  private SecurityUtils securityUtils;

	  @Mock
	  private CommentService commentService;

	  List<Post> postList;
	  List<Comment> commentList;
	
	  @Before
	  public void init() {
		  mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
		  
		  comment.setUser(user);
		  comment.setCommentId(2L);
		  comment.setPost(post);
		  comment.setText("test");
		  
		  when(securityUtils.getAuthenticatedUsername()).thenReturn("user1");
		  
	  }
	@Test
	public void createComment_NewComment_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/1")
            .contentType(MediaType.APPLICATION_JSON).content(createJson("comment"));

        when(commentService.createComment(anyString(), any(), anyLong())).thenReturn(comment);

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
            .andExpect(content().json("{\"commentId\":2,\"text\":\"test\",\"user\":{}}"))
            .andReturn();
        System.out.println(result.getResponse().getContentAsString());
	}
	
	private String createJson(String text) {

	    return "{\"text\":\"" + text + "\"}";
	  }
	
}
