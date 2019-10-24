package com.postit.exception;

@SuppressWarnings("serial")
public class JwtException extends Exception {

  public JwtException(String msg) {
    super(msg);
  }
}
