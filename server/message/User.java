package message;

import message.Chat;
import java.util.ArrayList;

public final class User{

  protected String name;
  protected int id;
  private ArrayList<Chat> chats;

  private static int nextId = 0;

  public User(String name){
    this.name = name;
    this.chats = new ArrayList<Chat>();
    this.id = User.nextId;
    User.nextId++;
  }

  public void sendMessage(int chatId, String message){
    String finalMessage = name + ": " + message;
    int i;
    for(i=0;i<chats.size();i++){
      if(chats.get(i).id == chatId){
        chats.get(i).forwardMessage(finalMessage);
        return;
      }
    }
  }
}
