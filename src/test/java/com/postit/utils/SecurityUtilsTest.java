package com.postit.utils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.postit.utils.SecurityUtils;

@PowerMockIgnore("javax.security.auth.Subject")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtils.class, SecurityContextHolder.class, SecurityContext.class,
    Authentication.class})
public class SecurityUtilsTest {

  private SecurityUtils securityUtils;

  @Before
  public void setUp() {

    securityUtils = new SecurityUtils();
  }

  @Test
  public void getAuthenticatedName_Username_Success() {

    SecurityContext securityContextMock = PowerMockito.mock(SecurityContext.class);
    Authentication authenticationMock = PowerMockito.mock(Authentication.class);

    PowerMockito.mockStatic(SecurityContextHolder.class);
    when(SecurityContextHolder.getContext()).thenReturn(securityContextMock);
    when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
    when(authenticationMock.getName()).thenReturn("user1");

    String username = securityUtils.getAuthenticatedUsername();

    assertEquals("user1", username);
  }
}
