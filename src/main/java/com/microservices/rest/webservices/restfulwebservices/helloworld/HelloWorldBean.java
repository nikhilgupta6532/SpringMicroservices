package com.microservices.rest.webservices.restfulwebservices.helloworld;

public class HelloWorldBean {
  private String message;

  public HelloWorldBean(String messge) {
    this.message = messge;
  }

  @Override
  public String toString() {
    return String.format("Hello world bean [message=%s]",message);
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
