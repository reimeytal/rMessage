package client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;

import client.message.ClientInterface;
import client.message.Client;
import client.ClientFront;

public class ChatPanel extends JPanel implements ClientInterface{
  private Client client;

  public ChatPanel(Client client){
    this.client = client;
  }
  
  public void handleMessage(String message){

  }

  public void handleChatList(ArrayList<Integer> chatlist){

  }
}
