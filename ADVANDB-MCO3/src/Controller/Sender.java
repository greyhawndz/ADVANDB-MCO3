/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;



import Controller.Transaction.Transaction;
import Helper.IsolationLevel;
import Helper.ValidAction;
import Helper.NodeType;
import Model.GenericObject;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author WilliamPC
 */
public class Sender {
    private GenericObject object;
    private String centralIP = "localhost";
    private String palIP = "localhost";
    private String marIP = "localhost";
    private NodeClient client;
    private Thread clientThread;
    private ValidAction action;
    
    
    public Sender(ValidAction action){
        this.action = action;
        System.out.println("ACTION: " +action);
    }
    
    public void executeQuery(NodeType destination, String query){
        System.out.println("executing query.....");
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination, query, false,action, "db_hpq_palawan");
            client = new NodeClient(palIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination, query, false,action, "db_hpq_palawan");
            client = new NodeClient(marIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
    public void setIsolationLevel(NodeType destination, IsolationLevel level){
        System.out.println("Setting iso level");
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination,action,level, "db_hpq_palawan");
            client = new NodeClient(palIP,object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination,action,level, "db_hpq_marinduque");
            client = new NodeClient(marIP,object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
    public void startTransaction(NodeType destination){
        System.out.println("in start transaction");
        
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination,action, "db_hpq_palawan");
            client = new NodeClient(palIP,object);
            clientThread = new Thread(client);
            System.out.println("Starting transaction");
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination,action, "db_hpq_palawan");
            client = new NodeClient(marIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        System.out.println("end of start");
    }
    
    public void setNode(NodeType destination){
        if(destination == NodeType.PALAWAN){
            System.out.println("SETTING NODE");
            object = new GenericObject(destination, "use db_hpq_palawan;", false, action, "db_hpq_palawan");
            System.out.println("Client starting");
            client = new NodeClient(palIP,object);
            System.out.println("lol");
            clientThread = new Thread(client);
            System.out.println("lol2");
            clientThread.start();
            System.out.println("Client started");
            
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination, "use db_hpq_marinduque", false, action, "db_hpq_marinduque");
            client = new NodeClient(marIP,object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
}
