/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat.libs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author ADMIN
 */
public class ChatMessageSocket {
    private Socket socket;
    private JTextPane txpNhan;
    private  PrintWriter out;
    private  BufferedReader reader;

    public ChatMessageSocket(Socket socket, JTextPane txpNhan) throws IOException {
        this.socket = socket;
        this.txpNhan = txpNhan;
        
        out= new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        receie();
    }
    
   private void receie(){
   Thread th = new Thread(){
       public void run(){
       while(true){
            try {
               String line = reader.readLine();
               if(line != null){
                   txpNhan.setText(txpNhan.getText()+"\nBan: "+line);
               }
           } catch (Exception e) {
           }
            }
       }
   };
   th.start();
   }
   public void send(String msg){
   String current = txpNhan.getText();
   txpNhan.setText(current+ "\n Toi: "+ msg);
   out.println(msg);
   out.flush();
   }
   public void close(){
       try {
           out.close();
           reader.close();
           socket.close();
       } catch (Exception e) {
       }
   }
}
