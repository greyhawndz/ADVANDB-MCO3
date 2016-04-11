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
import View.MainView;
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
    private String centralIP = "10.100.193.228";
    private String palIP = "10.100.214.58";
    private String marIP = "10.100.216.188";
    private NodeClient client;
    private Thread clientThread;
    private ValidAction action;
    
    
    
    
    public void endTransaction(NodeType destination){
        action = ValidAction.END_TRANSACTION;
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination,action,"db_hpq_palawan");
            object.setCommitted(true);
          //  object.setIso(MainView.SelectIso());
            client = new NodeClient(palIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination,action,"db_hpq_marinduque");
            object.setCommitted(true);
          //  object.setIso(MainView.SelectIso());
            client = new NodeClient(marIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.CENTRAL){
            object = new GenericObject(destination,action,"db_hpq_central");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(centralIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
    public void commitNodes(NodeType destination){
        action = ValidAction.COMMIT;
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination,action,"db_hpq_palawan");
        //    object.setIso(MainView.SelectIso());
            object.setCommitted(true);
            client = new NodeClient(palIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination,action,"db_hpq_marinduque");
            object.setIso(MainView.SelectIso());
            object.setCommitted(true);
            client = new NodeClient(marIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.CENTRAL){
            object = new GenericObject(destination,action,"db_hpq_central");
            object.setIso(MainView.SelectIso());
            object.setCommitted(true);
            client = new NodeClient(centralIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
    public void executeQuery(NodeType destination, String query){
        action = ValidAction.QUERY;
        System.out.println("executing query.....");
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination, query, false,action, "db_hpq_palawan");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(palIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination, query, false,action, "db_hpq_marinduque");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(marIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.CENTRAL){
            object = new GenericObject(destination, query, false,action, "db_hpq_central");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(centralIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
    public void updateNodes(NodeType destination, String query){
        action = ValidAction.UPDATE;
        System.out.println("executing query.....");
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination, query, true,action, "db_hpq_palawan");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(palIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination, query, true,action, "db_hpq_marinduque");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(marIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.CENTRAL){
            object = new GenericObject(destination, query, true,action, "db_hpq_central");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(centralIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
    public void setIsolationLevel(NodeType destination, IsolationLevel level){
        System.out.println("Setting iso level");
        action = ValidAction.SET_ISOLATION_LEVEL;
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination,action,level, "db_hpq_palawan");
            object.setIso(MainView.SelectIso());
            client = new NodeClient(palIP,object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination,action,level, "db_hpq_marinduque");
            object.setIso(MainView.SelectIso());
            client = new NodeClient(marIP,object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.CENTRAL){
            object = new GenericObject(destination,action,level, "db_hpq_central");
            object.setIso(MainView.SelectIso());
            client = new NodeClient(centralIP,object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }
    
    public void startTransaction(NodeType destination){
        System.out.println("in start transaction");
        action = ValidAction.START_TRANSACTION;
        if(destination == NodeType.PALAWAN){
            object = new GenericObject(destination,action, "db_hpq_palawan");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(palIP,object);
            clientThread = new Thread(client);
            System.out.println("Starting transaction");
            clientThread.start();
        }
        else if(destination == NodeType.MARINDUQUE){
            object = new GenericObject(destination,action, "db_hpq_palawan");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(marIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        else if(destination == NodeType.CENTRAL){
            object = new GenericObject(destination,action, "db_hpq_central");
         //   object.setIso(MainView.SelectIso());
            client = new NodeClient(centralIP, object);
            clientThread = new Thread(client);
            clientThread.start();
        }
        System.out.println("end of start");
    }
    
    public void setNode(NodeType destination){
        action = ValidAction.SET_NODE;
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
        else if(destination == NodeType.CENTRAL){
            object = new GenericObject(destination, "use db_hpq_central", false, action, "db_hpq_central");
            client = new NodeClient(centralIP,object);
            clientThread = new Thread(client);
            clientThread.start();
        }
    }

    public ValidAction getAction() {
        return action;
    }

    public void setAction(ValidAction action) {
        this.action = action;
    }
    
    
    
}
