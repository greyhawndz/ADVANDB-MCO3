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
    private final int PORT_NUM = 4321;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private GenericObject object;
   
    public NodeClient(String ip) {
        this.ip = ip;
        
        try{
            System.out.println("Creating client");
            socket = new Socket(ip,PORT_NUM);
            System.out.println("socket created");
            //in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("client online");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("caught");
        }
    }
    
    public void setObject(GenericObject object) {
        this.object = object;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("client started");
            out.writeObject(object);
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
