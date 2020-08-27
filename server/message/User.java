package message;

import message.Chat;
import java.util.ArrayList;
import java.net.Socket;

public final class User{

  protected String name;
  private int id;
  private Chat currentChat;
  protected Socket sock;

  private static int nextId = 0;

  public User(String name, Socket usock){
    this.name = name;
    currentChat = null;
    this.id = User.nextId;
    User.nextId++;
    sock = usock;
  }
  
  public boolean setChat(Chat c){
    if(c.addUser(this)){
      currentChat = c;
      return true;
    }
    return false;
  }

  public void leaveChat(){
    currentChat.removeUser(id);
    currentChat = null;
  }

  public String prepareMessage(String message){
    String finalMessage = name + ": " + message;
    int i;
    return finalMessage;
  }

  public Socket getSocket(){
    return sock;
  }

  public int getId(){
    return id;
  }
}
