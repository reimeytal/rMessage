package server;

import server.message.*;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server{

  public static void main(String[] args) throws java.io.IOException{
    ServerHub sh = ServerHub.hub;
    Thread sh_thread = new Thread(ServerHub.hub);
    User u = null;

    ServerSocket server_sock = new ServerSocket(8889);
    Socket client_sock = null;

    InputStreamReader in = null;
    BufferedReader br = null;

    sh_thread.run();

    while(true){
      client_sock = server_sock.accept();
      in = new InputStreamReader(client_sock.getInputStream());
      br = new BufferedReader(in);
      String nameLine = br.readLine();

      if(nameLine == null){
        client_sock.close();
        continue;
      }

      u = new User(nameLine, client_sock);
      sh.addUser(u);
    }

  }
}
