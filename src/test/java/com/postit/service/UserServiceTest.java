package com.postit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
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
import com.postit.exception.EmptyFieldException;
import com.postit.exception.EntityNotFoundException;
import com.postit.exception.LoginException;
import com.postit.exception.SignUpException;

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
	
	@Mock
	private Session session;
	
	@Before
	public void initMockBuild() {
			UserRole userRole = new UserRole();
		
		 	userRole.setName("ROLE_ADMIN");
	        user.setUserId(1L);
	        user.setEmail("testEmail@email.com");
	        user.setUsername("testUser");
	        user.setPassword("testPass");
	        user.setUserRole(userRole);
	}

	@Test
	public void signup_ReturnToken_Success() throws SignUpException, EmptyFieldException {
		String expectedToken = "returnToken";

		 when(userDao.signup(any())).thenReturn(user);
	     when(userDao.getUserByUsername(anyString())).thenReturn(user);
	     when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
	     when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testPass");

	        String actualToken = userService.signup(user);

	        assertEquals(actualToken, expectedToken);
	}

	@Test
	public void login_ReturnToken_Success() throws EntityNotFoundException, LoginException, EmptyFieldException {
		String expectedToken = "returnToken";

		when(userDao.login(any())).thenReturn(user);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testPass");
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);


        String actualToken = userService.login(user);

        assertEquals(actualToken, expectedToken);
	}

	@Test(expected = EmptyFieldException.class)
	public void login_EmptyUsername_Caught() throws EmptyFieldException, LoginException, EntityNotFoundException {

        User wrongUserNoUsername = new User();
        userService.login(wrongUserNoUsername); // empty/missing email exception
	}

	@Test(expected = SignUpException.class)
	public void signup_Null_Throw() throws SignUpException, EmptyFieldException {
	String expectedToken = "returnToken";

//		 when(userDao.signup(any())).thenReturn(user);
		 when(userDao.getUserByUsername(anyString())).thenReturn(user);
//	     when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
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
	public void signup_DuplicateId_Caught() throws SignUpException, EmptyFieldException {
//		User duplicateUser = new User();
//		duplicateUser.setUserId(new Long(1));

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

}
