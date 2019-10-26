package com.postit.exception;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class ErrorResponseTest {

  private ErrorResponse errorResponse;

  @Before
  public void init() {

    try {
      throw new IllegalArgumentException("msg");
    } catch (Exception e) {
      errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), "cause");
    }
  }

  @Test
  public void constructWithStatusMsgCause_ErrorResponse_Success() {

    errorResponse = null;
    try {
      throw new IllegalArgumentException("test");
    } catch (Exception e) {
      errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), "cause");
    }
  }

  @Test
  public void constructWithStatusMsg_ErrorResponse_Success() {

    errorResponse = null;
    try {
      throw new IllegalArgumentException("test");
    } catch (Exception e) {
      errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "cause");
    }
  }

  @Test
  public void getHttpStatus_HttpStatus_Success() {

    assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
  }

  @Test
  public void setHttpStatus_HttpStatus_Success() {

    errorResponse.setHttpStatus(HttpStatus.ACCEPTED);
    assertEquals(HttpStatus.ACCEPTED, errorResponse.getHttpStatus());
  }

  @Test
  public void getMessage_ErrorMessage_Success() {

    assertNotNull(errorResponse.getMessage());
    assertEquals("msg", errorResponse.getMessage());
  }

  @Test
  public void setMessage_ErrorMessage_Success() {

    assertNotEquals("test", errorResponse.getMessage());
    errorResponse.setMessage("test");
    assertEquals("test", errorResponse.getMessage());
  }

  @Test
  public void getCause_ErrorCause_Success() {

    assertEquals("cause", errorResponse.getCause());
  }

  @Test
  public void setCause_ErrorCause_Success() {

    assertNotEquals("test2", errorResponse.getCause());
    errorResponse.setCause("test2");
    assertEquals("test2", errorResponse.getCause());
  }

  @Test
  public void getTimestamp_TimestampString_Success() throws ParseException {

    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(errorResponse.getTimestamp());
    assertNotNull(date);
  }

  @Test
  public void setTimestamp_TimestampString_Success() {

    try {
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(errorResponse.getTimestamp());
    } catch (ParseException e) {
      assertTrue(false);
    }
    errorResponse.setTimestamp("test2");
    assertEquals("test2", errorResponse.getTimestamp());
  }
}
