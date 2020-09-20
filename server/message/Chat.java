package server.message;

import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import general.message.Chattype;

public final class Chat extends ServerNode{
  private ArrayList<String> messages;

  public Chat(Chattype type){
    super(type);
    messages = new ArrayList<String>();
  }

  private void forwardMessage(String message){
    messages.add(message);
    PrintWriter out;
    for(int i=0;i<users.size();i++){
      try{
        out = new PrintWriter(users.get(i).sock.getOutputStream());
        out.println("/fw");
        out.println(message);
        out.flush();
      } catch(Exception e){}
    }
  }
  public boolean addUser(User u){ //Make sure to send all the preexisting messages to the user
    return super.addUser2(u);
  }

  public void run(){

    InputStreamReader in = null;
    BufferedReader br = null;
    //PrintWriter out = null;
    String msg = null;
    int i;

    do{
      for(i=0;i<users.size();i++){
        try{
          in = new InputStreamReader(users.get(i).sock.getInputStream());
          br = new BufferedReader(in);
          msg = br.readLine();
        } catch(Exception e){
          continue;
        }

        if(msg == null){
          continue;
        } else if(msg.equals("/lv")){
          User u = users.get(i);
          removeUser(u.getId());
          ServerHub.hub.addUser(u);
        } else if(msg.equals("/ll")){
          try{
            users.get(i).disconnect();
          } catch(Exception e){}
        } else{
          msg = users.get(i).prepareMessage(msg);
          forwardMessage(msg);
        }
      }
    } while(true);

  }
}
