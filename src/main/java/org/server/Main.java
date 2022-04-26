package org.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    int port = 1234;
    List<PrintWriter> list = new ArrayList<>();
    try(ServerSocket serverSocket = new ServerSocket(port)){
      System.out.println("Сервер запущено");
      while (true){
        try {
          new Thread(new Handler(serverSocket.accept(), list)).start();
        }catch (Exception e){
          e.printStackTrace();
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}