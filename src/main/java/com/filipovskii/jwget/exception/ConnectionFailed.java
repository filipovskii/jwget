package com.filipovskii.jwget.exception;

public final class ConnectionFailed extends Exception {
  
  public ConnectionFailed (String message, Exception ex) {
    super(message, ex);
  }
  
  public ConnectionFailed (String message) {
    super(message);
  }

  public ConnectionFailed(Exception e) {
    super(e);
  }

}
