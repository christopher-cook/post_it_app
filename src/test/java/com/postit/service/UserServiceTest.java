package com.postit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.postit.config.JwtUtil;
import com.postit.dao.UserDao;
import com.postit.entity.User;
import com.postit.entity.UserRole;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserDao userDao;
	
	@Mock
	JwtUtil jwtUtil;
	
	@Mock
    private PasswordEncoder bCryptPasswordEncoder;
	
	@InjectMocks
	private User user;
	
	
	@Before
	public void initMockBuild() {
		UserRole userRole = new UserRole();
		
		 	userRole.setName("ROLE_ADMIN");
	        user.setUserId(1L);
	        user.setUsername("testUser");
	        user.setPassword("testPass");
	        user.setUserRole(userRole);
	}
	
	@Test
	public void signup_ReturnToken_Success() {
		String expectedToken = "returnToken";
		
		 when(userDao.signup(any())).thenReturn(user);
	     when(userDao.getUserByUsername(anyString())).thenReturn(user);
	     when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
	     when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testPass");
	        
	        String actualToken = userService.signup(user);
	        
	        assertEquals(actualToken, expectedToken);
	}
	
	@Test
	public void login_ReturnToken_Success() {
		String expectedToken = "returnToken";
		
		when(userDao.login(any())).thenReturn(user);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testPass");
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);

        
        String actualToken = userService.login(user);
        
        assertEquals(actualToken, expectedToken);
	}
	
}
