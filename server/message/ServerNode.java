package message;

import java.util.ArrayList;

public abstract class ServerNode implements Runnable{

  protected ArrayList<User> users;
  private static int nextId = 0;
  protected int id;
  protected Chattype type;
  static public ArrayList<ServerNode> chats = new ArrayList<ServerNode>();

  public ServerNode(Chattype type){
    this.type = type;
    users = new ArrayList<User>();
    chats.add(this);
    id = ServerNode.nextId;
    ServerNode.nextId++;
  }

  protected boolean addUser(User user){
    if(users.size() < type.maxUsers){
      users.add(user);
      return true;
    }
    user.setServerNode(this);
    return false;
  }

  public static int getNextId(){
    return nextId;
  }

  public void removeUser(int uid){
    for(int i=0;i<users.size();i++){
      if(users.get(i).getId() == uid){
        users.get(i).setServerNode(null);
        users.remove(i);
        return;
      }
    }
  }
}