/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GenericObject;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WilliamPC
 */
public class Server implements Runnable {
    
    private final int PORT_NUM = 4321;
    private ServerSocket server = null;
    private Socket socket = null;
    private GenericObject object;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private byte[] receiveData;
    private Receiver receiver;
    private int read = 0;
    
    public Server() throws IOException{
        server = new ServerSocket(PORT_NUM);
        
    }
    
    public String getServerInetAddress(){
        return server.getInetAddress().toString();
    }

    @Override
    public void run() {
        while(true){
            System.out.println("Server running");
            receiveData = new byte[1024];
            try {
                socket = server.accept();
                System.out.println("connection receive from " +socket.getInetAddress());
                System.out.println("Socket accepted");
                in = new ObjectInputStream(socket.getInputStream());
                try {
                    object = (GenericObject) in.readObject();
                    
                    if(object == null){
                        System.out.println("object is null");
                    }
                    receiver = new Receiver(object);
                    //receiver.UnpackObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
