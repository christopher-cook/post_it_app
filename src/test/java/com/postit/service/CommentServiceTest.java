package com.postit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.postit.dao.CommentDao;
import com.postit.dao.PostDao;
import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EntityNotFoundException;



public class CommentServiceTest {
	
	 @Rule
	  public MockitoRule rule = MockitoJUnit.rule();

	  @InjectMocks
	  private CommentServiceImpl commentService;

	  @Mock
	  private Post post;
	  
	  @InjectMocks
	  User user;

	  @InjectMocks
	  private Comment comment;
	  
	  @Mock
	  private CommentDao commentDao;

	  List<Post> postList;
	  List<Comment> commentList;
	
	@Before
	public void init() {
		
		comment.setCommentId(2L);
		comment.setPost(post);
		comment.setText("testText");
		comment.setUser(user);
		
	    commentList = new ArrayList<Comment>();
	    commentList.add(comment);
	}
	
	@Test
	public void createComment_NewComm_Success() {
		
		when(commentDao.createComment(anyString(), any(), anyLong())).thenReturn(comment);
		Comment actualComment = commentService.createComment("chris", comment, 2L);
    
		assertEquals(comment, actualComment);
	}
	
	@Test
	public void deleteComm_ById_Success() throws EntityNotFoundException {
		
		when(commentDao.deleteComment(anyString(), anyLong())).thenReturn(1L);
		
		Long deletedPostId = commentService.deleteComment("user1", 1L);
		
		assertEquals((long)1L, (long)deletedPostId);
	}
	
}
