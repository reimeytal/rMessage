package message;

import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ServerHub extends ServerNode{

  public static ServerHub hub = new ServerHub();

  private ServerHub(){
    super(Chattype.HUB);
  }

  public void addUser2(User u){
    addUser(u);
  }

  public void run(){ //Implement run method

  }
}
