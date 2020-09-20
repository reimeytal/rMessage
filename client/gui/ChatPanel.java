package client.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

import client.message.ClientInterface;
import client.message.Client;
import client.ClientFront;
import client.gui.ChatTab;

import general.message.Chattype;

public class ChatPanel extends JPanel implements ClientInterface, ActionListener{
  private Client           client;
  private JScrollPane      chattabsp;
  private JScrollPane      chatlistsp;
  private JTextField       messagefield;
  private JButton          sendbutton;
  private DefaultListModel chats;
  private JList            chatlist;
  private JLabel           statuslabel;
  private JButton          refresh;
  private JButton          create_chat;
  private JButton          join_chat;
  private Thread           clientThread;

  public ChatPanel(Client client){
    this.client = client;
    setLayout(null);

  }

  public void init(){
    chattabsp = new JScrollPane(ChatTab.chattab);
    chattabsp.setBounds(240, 10, 250, 430);
    chattabsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    chattabsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    add(chattabsp);

    chats = new  DefaultListModel();

    clientThread = new Thread(client);
    clientThread.start();

    try{
      client.getChatList();
    } catch(Exception e){}
    chatlist = new JList(chats);
    chatlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    chatlistsp = new JScrollPane(chatlist);
    chatlistsp.setBounds(10, 40, 200, 400);
    chatlistsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    chatlistsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    add(chatlistsp);

    messagefield = new JTextField();
    messagefield.setBounds(235, 445, 210, 25);
    add(messagefield);

    sendbutton = new JButton("Send");
    sendbutton.setBounds(445, 448, 43, 20);
    sendbutton.addActionListener(this);
    add(sendbutton);

    statuslabel = new JLabel("Chats");
    statuslabel.setBounds(70, 5, 150, 30);
    statuslabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
    add(statuslabel);

    refresh = new JButton("Refresh");
    refresh.setBounds(142, 448, 60, 20);
    refresh.addActionListener(this);
    add(refresh);

    join_chat = new JButton("Join");
    join_chat.setBounds(76, 448, 60, 20);
    join_chat.addActionListener(this);
    add(join_chat);

    create_chat = new JButton("Create");
    create_chat.setBounds(10, 448, 60, 20);
    create_chat.addActionListener(this);
    add(create_chat);
  }

  private void refresh_action() throws java.io.IOException{
    if(client.getServerNodeCT() != Chattype.HUB){
      int id = client.getServerNodeID();
      try{
        client.leaveChat();
        Thread.sleep(1);
        client.getChatList();
        Thread.sleep(1);
        client.joinChat(id);
      } catch(Exception e){
        System.out.println("Exception: "+e);
      }
    } else{
      client.getChatList();
    }
  }

  private void join_action(int id) throws java.io.IOException{
    if(client.getServerNodeCT() != Chattype.HUB){
      client.leaveChat();
      try{
        Thread.sleep(1);
      } catch(Exception e){
        System.out.println(e);
      }
    }
    client.joinChat(id);
  }

  private boolean create_action() throws java.io.IOException{
    boolean ret = false;
    if(client.getServerNodeCT() != Chattype.HUB){
      int id = client.getServerNodeID();
      try{
        client.leaveChat();
        Thread.sleep(1);
        ret = client.createChat();
        Thread.sleep(1);
        client.joinChat(id);
      } catch(Exception e){
        System.out.println(e);
      }
    } else{
      ret = client.createChat();
    }
    refresh_action();
    return ret;
  }

  public void handleMessage(String message){
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        ChatTab.chattab.addMessage(message);
        messagefield.setText("");
      }
    });
  }

  public void handleChatList(ArrayList<Integer> chatlist){
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        chats.clear();
        int i;
        for(i=0;i<chatlist.size();i++){
          chats.addElement(chatlist.get(i));
        }
      }
    });
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == sendbutton){
      client.sendMessage(messagefield.getText());
    } else if(e.getSource() == refresh){
      try{
        refresh_action();
      } catch(Exception ex){}
    } else if(e.getSource() == join_chat){
      try{
        join_action(Integer.parseInt(chatlist.getSelectedValue().toString()));
      } catch(Exception ex){}
    } else if(e.getSource() == create_chat){
      try{
        create_action();
      } catch(Exception ex){}
    }
  }
}
