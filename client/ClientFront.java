package client;

import client.message.Client;

import client.gui.LoginPanel;
import client.gui.ChatPanel;

import javax.swing.JFrame;

public class ClientFront extends JFrame{
  public static void main(String args[]){
    ClientFront cf = new ClientFront();
  }

  private LoginPanel lp;
  private ChatPanel  cp;

  private ClientFront(){
    super("rMessage");
    cp = new ChatPanel(Client.client);
    lp = new LoginPanel(Client.client, this, cp);
    add(lp);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 500);
    setVisible(true);
  }
}
