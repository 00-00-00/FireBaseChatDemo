package com.ground0.model;

/**
 * Created by zer0 on 5/10/16.
 */

public class ChatMessage {

  private String message;
  private Long author;

  // Required default constructor for Firebase object mapping
  @SuppressWarnings("unused") private ChatMessage() {
  }

  public ChatMessage(String message, Long author) {
    this.message = message;
    this.author = author;
  }

  public String getMessage() {
    return message;
  }

  public Long getAuthor() {
    return author;
  }
}