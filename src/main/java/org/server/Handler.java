package org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

public class Handler implements Runnable{
  final Socket socket;
  List<PrintWriter> list;

  public Handler(Socket socket, List<PrintWriter> list){
    this.socket = socket;
    this.list = list;
  }
  @Override
  public void run() {
    System.out.println("Коннект " + list.size() + " підключено");
    try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true)) {
        synchronized (this){
          list.add(pw);
        }
        String str;
        while((str = br.readLine()) != null){
          if(str.equalsIgnoreCase("exit")){
            System.out.println("Конект відключено");
            pw.println("exit");
            list.remove(pw);
            break;
          }else{
            for(PrintWriter p : list){
              p.println(LocalDateTime.now() + "\n" + str);
            }
          }
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
