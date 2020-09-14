package client.gui;

import client.message.Client;
import client.ClientFront;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel implements ActionListener{ //G
  JButton     login_button;
  JLabel      name_label;
  JLabel      rMessage_name;
  JLabel      error;
  JTextField  name_field;
  ClientFront parent;
  Client      client;
  ChatPanel   cp;

  public LoginPanel(Client client, ClientFront parent, ChatPanel cp){
    setLayout(null);
    this.parent = parent;
    this.cp = cp;
    this.client = client;

    error = new JLabel();

    name_label = new JLabel("Name:");
    name_label.setBounds(150, 200, 75, 20);
    add(name_label);

    rMessage_name = new JLabel("rMessage");
    rMessage_name.setFont(new Font("Helvetica Neue", Font.PLAIN, 38));
    rMessage_name.setBounds(175, 95, 300, 50);
    add(rMessage_name);

    name_field = new JTextField();
    name_field.setBounds(200, 200, 150, 20);
    add(name_field);

    login_button = new JButton();
    login_button.setText("Login");
    login_button.setBounds(200, 250, 100, 20);
    login_button.addActionListener(this);
    add(login_button);
  }

  private void handleLogin(){
    if(name_field.getText().equals("") || name_field.getText().equals(" ")){
      error.setText("Fill out all fields");
      error.setBounds(200, 265, 150, 50);
      add(error);
    } else if(!client.init(cp, name_field.getText())){
      error.setText("Server Error");
      error.setBounds(212, 265, 150, 50);
      add(error);
    } else{
      parent.remove(this);
      parent.add(cp);
    }
    parent.repaint();
    parent.revalidate();
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == login_button){
      try{
        handleLogin();
      } catch(Exception exception){
        add(error);
      }
    }
  }
}
