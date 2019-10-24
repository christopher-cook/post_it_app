package com.postit.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import javax.persistence.criteria.CriteriaUpdate;

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
import com.postit.entity.UserRole;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private User user;
	
	
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
	
	@Mock
	private List<Post> postList;

	
	@Before
	public void initSession() {
	   when(sessionFactory.getCurrentSession()).thenReturn(session);
	   when(session.getTransaction()).thenReturn(transaction);
	}
	
	@Before
	public void initTestUser() {
		userRole.setRoleId(1);
        userRole.setName("ROLE_ADMIN");
        user.setUserId(1L);
        user.setUsername("chris");
        user.setEmail("testEmail");
        user.setPassword("testPass");
        user.setUserRole(userRole);
	}
	
	@Test
	public void signup_User_Success() {
		when(userRoleDao.getRole(anyString())).thenReturn(userRole);
		
		User tempUser = userDao.signup(user);
        assertEquals(tempUser.getUsername(), user.getUsername());
	}
	
	@Test
	public void login_User_Success() {
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		
		User savedUser = userDao.login(user);
		assertEquals(savedUser, user);	
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
	
//	@Test
//	public void getPost_ByUser_Success() {
//		when(session.createQuery(anyString())).thenReturn(query);
//		when(query.uniqueResult()).thenReturn(user);
//		
//		List<Post> tempUser = userDao.getPostsByUser(user.getUsername());
//		assertEquals(postList, tempUser;
//	}
//	
}
