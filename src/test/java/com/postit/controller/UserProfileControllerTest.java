package com.postit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

import com.postit.entity.User;
import com.postit.entity.UserProfile;
import com.postit.service.UserProfileService;
import com.postit.utils.SecurityUtils;

public class UserProfileControllerTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  private MockMvc mockMvc;

  @InjectMocks
  UserProfileController userProfileController;

  @InjectMocks
  UserProfile userProfile;

  @InjectMocks
  User user;

  @Mock
  UserProfileService userProfileService;

  @Mock
  SecurityUtils securityUtils;

  @Before
  public void initialize() {

    mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();

    when(securityUtils.getAuthenticatedUsername()).thenReturn("user1");

    userProfile.setProfileId(1L);
    userProfile.setAdditionalEmail("email1@email.com");
    userProfile.setMobile("111111");
    userProfile.setAddress("amazon");

    user.setUserId(1L);
    user.setUsername("user1");
    userProfile.setUser(user);
  }

  @Test
  public void createUserProfile_NewUserProfile_Success() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile")
        .contentType(MediaType.APPLICATION_JSON).content(createJson("email1@email.com"));

    when(userProfileService.getUserProfile(anyString())).thenReturn(null);
    when(userProfileService.createProfile(anyString(), any())).thenReturn(userProfile);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content().json("{\"additionalEmail\":\"email1@email.com\"}")).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void updateUserProfile_UserProfile_Success() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile")
        .contentType(MediaType.APPLICATION_JSON).content(createJson("email1@email.com"));

    when(userProfileService.getUserProfile(anyString())).thenReturn(userProfile);
    when(userProfileService.updateProfile(anyString(), any())).thenReturn(userProfile);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content().json("{\"additionalEmail\":\"email1@email.com\"}")).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getUserProfile_UserProfile_Success() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/profile");

    when(userProfileService.getUserProfile(anyString())).thenReturn(userProfile);

    MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(content().json("{\"additionalEmail\":\"email1@email.com\"}")).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  private String createJson(String email) {

    return "{\"additionalEmail\":\"" + email + "\"" + "}";
  }
}
