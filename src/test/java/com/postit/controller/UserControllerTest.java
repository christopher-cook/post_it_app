package com.postit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.postit.config.JwtUtil;
import com.postit.entity.User;
import com.postit.service.UserService;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	UserController userController;
	
	@InjectMocks
	User user;
	
	@Mock
	UserService userService;
	
	@Before
	public void initMockBuild() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		
		user.setEmail("testEmail");
		user.setPassword("testPass");
		user.setUsername("testUser");
	}
	
	@Test
	public void signup_User_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/user/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createUserJson("testEmail", "testUser", "testPass"));
		
		when(userService.signup(any())).thenReturn("testReturnString");
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("{\"token\":\"testReturnString\"}"))
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}

	private static String createUserJson(String email, String username, String password) {
		return "{ \"email\": \"" + email + "\", " + "\"username\": \"" + username + "\", " +
                "\"password\":\"" + password + "\"}";
	}
	
	@Test
	public void login_User_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createUserJson("testEmail", "testUser", "testPass"));
		
		
		when(userService.login(any())).thenReturn("testString");
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("{\"token\":\"testString\"}"))
				.andReturn();
	}
}
