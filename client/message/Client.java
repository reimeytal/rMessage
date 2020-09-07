package client.message;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import general.message.Chattype;

import java.net.Socket;

public class Client{

  public static Client client = new Client();

  private int id;
  private int serverNodeId;
  private Socket socket;
  private InputStreamReader isr;
  private BufferedReader in;
  private PrintWriter out;
  private ClientInterface ci;

  private Client(){} //Completed

  public void init(ClientInterface ci, String name) throws java.io.IOException{ //Completed
    this.ci = ci;
    try{
      socket = new Socket("localhost", 8889);
    } catch(Exception e){
      System.out.println("CLIENT: Unknown Host");
      System.exit(0);
    }
    out = new PrintWriter(socket.getOutputStream());
    out.write(name);
    out.flush();
    isr = new InputStreamReader(socket.getInputStream());
    in = new BufferedReader(isr);
    String rdid = in.readLine();
    if(rdid == null){
      System.out.println("CLIENT: Problem with server (did not receive id)");
      System.exit(0);
    }
    id = Integer.parseInt(rdid);
  }

  public void runClient() throws java.io.IOException{
    while(true){
      String recv = in.readLine();
      if(recv != null){
        ci.handleMessage(recv);
      }
    }
  }

  public void sendMessage(String message){
    //Uncompleted
  }

  public void leaveChat(){

  }

  public boolean joinChat(int chatId){
    return true; //Placeholder
  }
}
