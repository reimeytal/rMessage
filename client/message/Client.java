package client.message;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;

import general.message.Chattype;

import java.net.Socket;

public class Client{

  public static Client client = new Client();

  private int id;
  private int serverNodeId;
  private volatile Chattype serverNodeCT;
  private Socket socket;
  private InputStreamReader isr;
  private BufferedReader in;
  private PrintWriter out;
  private ClientInterface ci;

  private Client(){} //Complete

  public void init(ClientInterface ci, String name) throws java.io.IOException{ //Complete (PROBLEM IN THIS METHOD)
    this.ci = ci;
    try{
      socket = new Socket("localhost", 8859);
      socket.setSoTimeout(3000);
      out = new PrintWriter(socket.getOutputStream());
      isr = new InputStreamReader(socket.getInputStream());
      in = new BufferedReader(isr);
    } catch(Exception e){
      System.out.println("CLIENT: Unknown Host");
      System.exit(0);
    }
    out.println(name);
    out.flush();
    String rdid = in.readLine();
    if(rdid == null){
      System.out.println("CLIENT: Problem with server (did not receive id)");
      System.exit(0);
    }
    serverNodeCT = Chattype.HUB;
    id = Integer.parseInt(rdid);
  }

  public void runClient(){ //Complete
    while(true){
      try{
        String recv = in.readLine();
        if(recv != null){
          ci.handleMessage(recv);
        }
      } catch(Exception e){
        continue;
      }
    }
  }

  public void sendMessage(String message){ //Complete
    if(serverNodeCT == Chattype.HUB){
      return;
    }
    out.println(message);
    out.flush();
  }

  public void leaveChat(){ //Complete
    switch(serverNodeCT){
      case HUB:
        return;
      default:
        out.println("/ll");
        out.flush();
    }
  }

  public void disconnect(){ //Complete
    out.println("/lv");
    out.flush();
  }

  public boolean joinChat(int chatId) throws java.io.IOException{ //Complete
    if(serverNodeCT != Chattype.HUB){
      return false;
    }
    boolean success;
    out.println("/jc" + Integer.toString(chatId));
    out.flush();
    String ret = in.readLine();
    if(Integer.parseInt(ret) == 1){
      success = true;
      serverNodeCT = Chattype.GROUP;
      serverNodeId = chatId;
    } else{
      success = false;
    }
    return success;
  }

  public void getChatList() throws java.io.IOException{ //Complete
    if(serverNodeCT != Chattype.HUB){
      return;
    }
    out.println("/gcl");
    out.flush();

    int i;
    String ret = in.readLine();
    String cla[] = ret.split("|",  128);

    ArrayList<Integer> chatlist = new ArrayList<Integer>();

    if(ret == null){
      return;
    }
    for(i=0;i<cla.length;i++){
      chatlist.add(Integer.parseInt(cla[i]));
    }

    ci.handleChatList(chatlist);
  }

  public boolean createChat() throws java.io.IOException{ //Complete
    if(serverNodeCT != Chattype.HUB){
      return false;
    }
    out.println("/cc");
    out.flush();
    int retval = Integer.parseInt(in.readLine());
    switch(retval){
      case 0:
        return false;
      case 1:
        return true;
    }
    return false;
  }
}
