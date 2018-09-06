package com.example.thangpham.testfirebasenhap.model;

public class ChatMessage {
  public String content;
  public long time;
  public boolean isOwn;
  public ChatMessage(String content, long time) {
    this.content = content;
    this.time = time;
  }
  public ChatMessage(){

  }

  public ChatMessage(String content, long time, boolean isOwn) {
    this.content = content;
    this.time = time;
    this.isOwn = isOwn;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public boolean isOwn() {
    return isOwn;
  }

  public void setOwn(boolean own) {
    isOwn = own;
  }
}
