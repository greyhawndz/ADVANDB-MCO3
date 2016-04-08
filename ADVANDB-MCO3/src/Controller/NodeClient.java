/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helper.NodeType;
import Model.GenericObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WilliamPC
 */
public class NodeClient implements Runnable{
    private String ip;
    private final int PORT_NUM = 1234;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private GenericObject object;
   
    public NodeClient(String ip, GenericObject object) {
        this.ip = ip;
        this.object = object;
        
        try{
            socket = new Socket(ip,PORT_NUM);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("client online");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    

    @Override
    public void run() {
        try {
            out.writeObject(object);
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
       
    }
}
