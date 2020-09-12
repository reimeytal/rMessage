package client.message;

import java.util.ArrayList;

public interface ClientInterface{
  public void handleMessage(String message);
  public void handleChatList(ArrayList<Integer> chatlist);
}
