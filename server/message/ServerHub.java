package server.message;

import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import general.message.Chattype;

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
    while(true){
      for(i=0;i<super.users.size();i++){
        try{
          sock = super.users.get(i).getSocket();
          in = new InputStreamReader(sock.getInputStream());
          br = new BufferedReader(in);
          message = br.readLine();
          if(message == null){
            continue;
          }
          out = new PrintWriter(sock.getOutputStream());
          if(message.length() > 2 && message.substring(0, 3).equals("/jc")){
            if(chats.get(Integer.parseInt(message.substring(3))) instanceof Chat){
              if(!chats.get(Integer.parseInt(message.substring(3))).addUser(super.users.get(i))){
                out.println("/jc");
                out.println("0");
                out.flush();
              } else{
                removeUser(users.get(i).getId());
                out.println("/jc");
                out.println("1");
                out.flush();
              }
            } else{
              out.println("/jc");
              out.println("0");
              out.flush();
            }
          } else if(message.equals("/ll")){
            super.users.get(i).disconnect();
          } else if(message.equals("/cc")){
            if(ServerNode.getNextId() < 17){
              Chat c = new Chat(Chattype.GROUP);
              Thread nt = new Thread(c);
              nt.start();
              out.println("/cc");
              out.println("1");
              out.flush();
            } else{
              out.println("/cc");
              out.println("0");
              out.flush();
            }
          } else if(message.equals("/gcl")){
            int x;
            String ret = "";
            for(x=0;x<chats.size();x++){
              if(chats.get(x) != null && chats.get(x) instanceof Chat){
                ret += chats.get(x).id + "_";
              }
            }
            if(ret.equals("")){
              ret = "_";
            }
            out.println("/gcl");
            out.println(ret);
            out.flush();
          }
        } catch(Exception e){}
      }
    }
  }
}
