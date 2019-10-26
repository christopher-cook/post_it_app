package com.postit.config;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class JwtUtilTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @InjectMocks
  JwtUtil jwtUtil;

  @Mock
  UserDetails userDetails;

  @Before
  public void init() {

    jwtUtil.setSecret("test");
  }

  @Test
  public void generateToken_TokenString_Success() {

    when(userDetails.getUsername()).thenReturn("user1");
    String token = jwtUtil.generateToken(userDetails);
    assertNotNull(token);
    assertTrue(token.length() > 0);
  }

  @Test
  public void getUsernameFromToken_Username_Success() {

    when(userDetails.getUsername()).thenReturn("user1");
    String token = jwtUtil.generateToken(userDetails);
    String username = jwtUtil.getUsernameFromToken(token);
    assertEquals("user1", username);
  }

  @Test
  public void validateToken_Boolean_Success() {

    when(userDetails.getUsername()).thenReturn("user1");
    String token = jwtUtil.generateToken(userDetails);
    Boolean isValid = jwtUtil.validateToken(token, userDetails);
    assertTrue(isValid);
  }

  @Test
  public void getExpirationDateFromToken_Date_Success() {

    when(userDetails.getUsername()).thenReturn("user1");

    String token = jwtUtil.generateToken(userDetails);
    Date expirationDate = jwtUtil.getExpirationDateFromToken(token);
    assertNotNull(expirationDate);

    Date now = new Date(System.currentTimeMillis());
    assertTrue(now.before(expirationDate));
  }
}
