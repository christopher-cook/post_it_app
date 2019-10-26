package com.postit.exception;

@SuppressWarnings("serial")
public class LoginException extends Exception {

  public LoginException(String msg) {
    super(msg);
  }
}
