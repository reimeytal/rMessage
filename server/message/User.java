package message;

import message.Chat;
import java.util.ArrayList;
import java.net.Socket;

public final class User{

  protected String name;
  private int id;
  private ServerNode currentServerNode;
  protected Socket sock;

  private static int nextId = 0;

  public User(String name, Socket usock){
    this.name = name;
    currentServerNode = null;
    this.id = User.nextId;
    User.nextId++;
    sock = usock;
  }

  public boolean setServerNode(ServerNode c){
    if(c.addUser(this)){
      currentServerNode = c;
      return true;
    }
    return false;
  }

  public void leaveServerNode(){
    currentServerNode.removeUser(id);
    currentServerNode = null;
  }

  protected String prepareMessage(String message){
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
