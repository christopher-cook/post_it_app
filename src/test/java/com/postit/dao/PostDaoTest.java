package com.postit.dao;

import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;

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
import org.springframework.test.web.servlet.MockMvc;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EntityNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class PostDaoTest {
	
	private MockMvc mockMvc;
	
	@Mock
	UserDaoImpl userDao;
	
	@InjectMocks
	PostDaoImpl postDao;
	
	@Mock
	SessionFactory sessionFactory;
	
	@Mock
	private Session session;
	
	@Mock
	private Transaction transaction;
	
	@InjectMocks
	User user;
	
	@InjectMocks
	Post post;
	
	@InjectMocks
	Comment comment;
	
	List<Post> postList;
	
	List<Comment> commentList;
	
	@Mock
	private Query<Post> query;
	
	@Before
	public void init() {
		postList = new ArrayList<Post>();
		commentList = new ArrayList<Comment>();
		
		post.setDescription("testDescrip");
		post.setPostId(2L);
		post.setTitle("test title");
		post.setUser(user);
		
		comment.setCommentId(2L);
		comment.setPost(post);
		comment.setText("test comment");
		comment.setUser(user);
	}
	
	@Test
	public void create_Post_Success() {
		
		when(userDao.getUserByUsername(any())).thenReturn(user);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.getTransaction()).thenReturn(transaction);
		
		Post tempPost = postDao.createPost("testUsername", post);
		assertEquals(tempPost, post);
		
	}
	
//	@Test
//	public void deletePost_ById_Success() throws EntityNotFoundException {
//		
//		when(userDao.getUserByUsername(any())).thenReturn(user);
//		when(sessionFactory.getCurrentSession()).thenReturn(session);
//		
//		List<Post> newPostList = postDao.deletePostByPostId();
//		
//	}
	
	@Test
	public void listPosts_All_Success() {
		postList.add(post);
		
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(postList);
		
		List<Post> actualPostList = postDao.listPosts();
		assertEquals(postList, actualPostList);
	}
	
	
	
}
