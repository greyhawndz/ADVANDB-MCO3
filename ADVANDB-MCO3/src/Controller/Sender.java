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
    private String centralIP = "localhost";
    private String palIP = "localhost";
    private String marIP = "localhost";
    private NodeClient client;
    private Thread clientThread;
    private ValidAction action;
    
    public Sender(){
        client = new NodeClient();
        object = new GenericObject();
        object.setUpdated(false);
        object.setCommitted(false);
        
    }
    
    public Sender(GenericObject object){
        client = new NodeClient();
        this.object = object;
    }
    
    public void sendObject(String query){
        object.setQuery(query);
        System.out.println("I AM USING THE DB" +object.getDatabase());
        client.setObject(object);
        clientThread = new Thread(client);
        clientThread.start();
    }
    
    public void setIso(IsolationLevel iso){
        object.setIso(iso);
    }
    
    public void setNode(NodeType destination){
        object.setDatabase(destination);
        if(destination == NodeType.CENTRAL){
            object.setIp(centralIP);  
            object.setDbName("db_hpq_central");
        }
        else if(destination == NodeType.MARINDUQUE){
            object.setIp(marIP);  
            object.setDbName("db_hpq_marinduque");
        }
        else if(destination == NodeType.PALAWAN){
            object.setIp(palIP);
            object.setDbName("db_hpq_palawan");
        }
        client.setIp(object.getIp());
    }
    
    public void updateNodes(NodeType destination, GenericObject object){
        GenericObject objectC = new GenericObject();
        GenericObject objectM = new GenericObject();
        GenericObject objectP = new GenericObject();
        objectC.setCommitted(false);
        objectC.setUpdated(true);
        objectM.setCommitted(false);
        objectM.setUpdated(true);
        objectP.setCommitted(false);
        objectP.setUpdated(true);
        if(destination == NodeType.CENTRAL){
           objectM.setDatabase(NodeType.MARINDUQUE);
           objectM.setIp(marIP);
           objectM.setDbName("db_hpq_marinduque");
           objectM.setQuery(object.getQuery());
           objectM.setIso(IsolationLevel.SERIALIZABLE);
            System.out.println("Under marinduque");
           sendOthers(objectM);
           objectP.setDatabase(NodeType.PALAWAN);
           objectP.setIp(palIP);
           objectP.setDbName("db_hpq_palawan");
           objectP.setQuery(object.getQuery());
           objectP.setIso(IsolationLevel.SERIALIZABLE);
            System.out.println("under palawan");
           sendOthers(objectP);
        }
        else{
            objectC.setDatabase(NodeType.CENTRAL);
           objectC.setIp(centralIP);
           objectC.setDbName("db_hpq_central");
           objectC.setQuery(object.getQuery());
           objectC.setIso(IsolationLevel.SERIALIZABLE);
            sendOthers(objectC);
        }
    }
    
    public void sendOthers(GenericObject objectU){
        client = new NodeClient();
        client.setObject(objectU);
        clientThread = new Thread(client);
        clientThread.start();
    }
    
    public void endTransaction(){
        object.setCommitted(true);
        object.setUpdated(false);
        object.setAction(ValidAction.END_TRANSACTION);
        sendObject(object.getQuery());
    }
    
    public void commitNodes(NodeType destination, GenericObject object){
        this.object = object;
        object.setUpdated(false);
        object.setCommitted(true);
        object.setAction(ValidAction.COMMIT);
        if(destination == NodeType.CENTRAL){
           setNode(NodeType.MARINDUQUE);
           sendObject(object.getQuery());
           setNode(NodeType.PALAWAN);
           sendObject(object.getQuery());
        }
        else {
            setNode(NodeType.CENTRAL);
            sendObject(object.getQuery());
        }
    }
    
    /*
    
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
*/
    public ValidAction getAction() {
        return action;
    }

    public void setAction(ValidAction action) {
        this.action = action;
    }
    
    
    
}
