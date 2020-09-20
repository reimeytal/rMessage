package client.gui;

import javax.swing.JSeparator;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;

public class ChatTab extends JPanel{
  public static ChatTab chattab = new ChatTab();

  private int panelsize_y;
  private ArrayList<JLabel> message_labels;

  private ChatTab(){
    message_labels = new ArrayList<JLabel>();
    panelsize_y = 0;

    setLayout(null);
    setPreferredSize(new Dimension(260, panelsize_y));
  }

  public void addMessage(String message){
    JLabel lta = new JLabel(message);
    JSeparator sep = new JSeparator();
    lta.setBounds(5, panelsize_y, 245, 50);
    sep.setBounds(2, panelsize_y+35, 243, 2);
    sep.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    add(lta);
    add(sep);

    panelsize_y+=30;
    setPreferredSize(new Dimension(260, panelsize_y));

    message_labels.add(lta);
    revalidate();
    repaint();
  }
}
