package com.postit.exception;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerTest {

  private ExceptionHandler exceptionHandler;
  private ResponseEntity<ErrorResponse> response;

  @Before
  public void init() {

    exceptionHandler = new ExceptionHandler();
    response = exceptionHandler.handleException(new RuntimeException("msg"));
  }

  @Test
  public void constructor_ResponseEntityErrorResponse() {

    assertEquals("msg", response.getBody().getMessage());
    assertEquals(400, response.getStatusCodeValue());
  }
}
