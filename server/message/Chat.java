package message;

import java.util.ArrayList;

enum Chattype{
  PRIVATE(2),
  GROUP(128);

  protected int maxUsers;

  private Chattype(int maxUsers){
    this.maxUsers = maxUsers;
  }
  public int getMaxUsers(){
    return maxUsers;
  }
}

public final class Chat{
  private static int nextId;
  protected int id;
  private ArrayList<String> messages;

  public Chat(){
    id = Chat.nextId;
    Chat.nextId++;
    messages = new ArrayList<String>();
  }

  protected void forwardMessage(String message){
    messages.add(message);
    //Function incomplete, must send the message to everyone
  }
}
