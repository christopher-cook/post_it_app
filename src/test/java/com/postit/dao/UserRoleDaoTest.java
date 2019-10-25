package com.postit.dao;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import com.postit.entity.User;
import com.postit.entity.UserProfile;
import com.postit.entity.UserRole;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleDaoTest {
	
	private MockMvc mockMvc;

	@InjectMocks
	User user;
	
	 @Mock
	  private SessionFactory sessionFactory;

	  @Mock
	  private Session session;

	  @Mock
	  private Transaction transaction;

	  @InjectMocks
	  UserRole userRole;
	  
	  @InjectMocks
	  UserRoleDaoImpl userRoleDao;
	  
		@Mock
		private Query<UserRole> query;
	  
	 @Before
	 	public void init() {
		 userRole.setName("ROLE_ADMIN");
		 user.setUserId(1L);
	     user.setUsername("user1");
	     user.setUserRole(userRole);
	    
		 
		when(sessionFactory.getCurrentSession()).thenReturn(session);
//		when(session.getTransaction()).thenReturn(transaction);
		
	 }
	  
	  @Test
	  public void getRole_ByName_Success() {
		
		  when(session.createQuery(anyString())).thenReturn(query);
		  when(query.uniqueResult()).thenReturn(userRole);
		  
		  UserRole actualUserRole = userRoleDao.getRole("testRole");
		  
		  assertEquals(userRole.getName(), actualUserRole.getName());

	  }
}
