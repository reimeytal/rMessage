package message;

import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

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
        out.write(message);
        out.flush();
      } catch(Exception e){}
    }
  }
  public boolean addUserToChat(User u){ //Make sure to send all the preexisting messages to the user
    return addUser(u);
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
        } else if(msg == "/leave\\"){
          User u = users.get(i);
          removeUser(u.getId());
          ServerHub.hub.addUser(u);
        } else{
          msg = users.get(i).prepareMessage(msg);
          forwardMessage(msg);
        }
      }
    } while(true);

  }
}
