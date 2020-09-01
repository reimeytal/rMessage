package server.message;

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

  public boolean addUser(User u){
    return super.addUser2(u);
  }

  public void run(){
    int i;

    InputStreamReader in = null;
    PrintWriter out = null;
    BufferedReader br = null;
    Socket sock = null;
    String message = null;

    do{
      for(i=0;i<users.size();i++){
        try{
          sock = users.get(i).getSocket();
          in = new InputStreamReader(sock.getInputStream());
          br = new BufferedReader(in);
          message = br.readLine();
          if(message == null){
            continue;
          }
          out = new PrintWriter(sock.getOutputStream());
          if(message.substring(0, 3) == "/jc"){
            if(chats.get(Integer.parseInt(message.substring(3))) instanceof Chat){
              chats.get(Integer.parseInt(message.substring(3))).addUser(users.get(i));
            }
          } else if(message == "/ll"){
            users.get(i).disconnect();
          }
        } catch(Exception e){}
      }
    } while(true);
  }
}
