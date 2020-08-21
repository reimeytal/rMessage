package message;

import message.Chat;

public final class User{

  protected String name;
  private Chat chats[];

  public User(String name){
    this.name = name;
    chats = new Chat[16];
  }
}
