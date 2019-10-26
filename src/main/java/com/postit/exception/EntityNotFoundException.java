package com.postit.exception;

@SuppressWarnings("serial")
public class EntityNotFoundException extends Exception {

  public EntityNotFoundException(String msg) {
    super(msg);
  }
}
