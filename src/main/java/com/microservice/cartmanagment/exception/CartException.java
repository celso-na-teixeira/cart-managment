package com.microservice.cartmanagment.exception;

public class CartException extends RuntimeException {

  public CartException() {
  }

  public CartException(String message) {
    super(message);
  }

  public CartException(String message, Throwable cause) {
    super(message, cause);
  }
}
