package message;

import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

enum Chattype{
  PRIVATE(2),
  GROUP(16);

  protected int maxUsers;

  private Chattype(int maxUsers){
    this.maxUsers = maxUsers;
  }
  public int getMaxUsers(){
    return maxUsers;
  }
}

public final class Chat implements Runnable{
  private static int nextId;
  protected int id;
  private ArrayList<String> messages;
  protected Chattype type;
  protected ArrayList<User> users;
  static public ArrayList<Chat> chats;

  public Chat(Chattype type){
    this.type = type;
    id = Chat.nextId;
    Chat.nextId++;
    messages = new ArrayList<String>();
    users = new ArrayList<User>();
  }

  private void forwardMessage(String message){
    messages.add(message);
    PrintWriter out;
    for(int i=0;i<users.size();i++){
      try{
        out = new PrintWriter(users.get(i).sock.getOutputStream());
        out.write(message);
        out.flush();
      } catch(Exception e){}
    }
  }
  public boolean addUser(User u){
    if(users.size() < Chattype.GROUP.maxUsers){
      users.add(u);
      return true;
    }
    return false;
  }

  public void removeUser(int uid){
    for(int i=0;i<users.size();i++){
      if(users.get(i).getId() == uid){
        users.remove(i);
        return;
      }
    }
  }

  public static int getNextId(){
    return nextId;
  }

  public void run(){

    InputStreamReader in = null;
    BufferedReader br = new BufferedReader(in);
    //PrintWriter out = null;
    String msg = null;
    int i;

    do{
      for(i=0;i<users.size();i++){
        try{
          in = new InputStreamReader(users.get(i).sock.getInputStream());
          //out = new PrintWriter(users.get(i).sock.getOutputStream());
          msg = br.readLine();
        } catch(Exception e){
          continue;
        }

        if(msg == null){
          continue;
        } else if(0 == 1){
          //Later check for messages requesting to leave chat
        } else{
          msg = users.get(i).prepareMessage(msg);
          forwardMessage(msg);
        }
      }
    } while(true);

  }
}
