/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Transaction;

import Controller.DBConnector;
import Controller.NodeClient;
import Controller.Sender;
import Helper.IsolationLevel;
import Helper.NodeType;
import Helper.ValidAction;
import Model.GenericObject;
import View.MainView;
import com.sun.rowset.CachedRowSetImpl;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author WilliamPC
 */
public class Transaction {
    public static final String ipPalawan = "";
    public static final String ipCentral = "";
    public static final String ipMarinduque = "";
    private DBConnector connector;
    private Connection connection;
    private ResultSet result;
    private PreparedStatement statement;
    private NodeClient client;
    private Thread clientThread;
    private String dbName = "db_hpq_central";
    private PrintWriter pw;
    
    public String getDbName() {
        return dbName;
    }
    
    public Transaction() throws IOException{
        try {
            connector = DBConnector.getInstance(dbName);
            connection = connector.getConnect();
            connection.setAutoCommit(false);
             pw = new PrintWriter(new FileWriter("test/log.txt"));
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    
    
    public void startTransaction() {
        System.out.println("Transaciton started");
        pw.write("start\n");
        
        
    }
    
    public boolean closeTransaction(GenericObject object) {
        boolean committed = false;
        Sender sender = new Sender();
        sender.setAction(ValidAction.COMMIT);
        try {
            // listen to other nodes before commit
            connection.commit();
            committed = true;
            
            pw.write("commit");
            
            System.out.println("OBJECT DATABASE: " +object.getDatabase());
            
            sender.commitNodes(object.getDatabase(), object);
            pw.close();
            // unlock tables
            
            // update log
            // send data to other database
         //   connection.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
                pw.close();
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } /*finally {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }*/
        return committed;
    }
    
    public void commitNodes(GenericObject object){
        try {
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ProcessQuery(GenericObject object){
        setIsolationLevel(MainView.SelectIso());
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = object.getQuery().toLowerCase();
        try {
            statement = connection.prepareStatement(query);
            
            if(query.contains("select")) {
                ResultSet result = statement.executeQuery(query);
              /*  result.beforeFirst();
                if(!result.next()){
                    System.out.println("walang laman");
                }*/
                //set result in table
                object.setAction(ValidAction.READ);
                System.out.println("process read");
                CachedRowSet cRow = new CachedRowSetImpl();
                cRow.populate(result);
                object.setcRow(cRow);
                client = new NodeClient();
                client.setIp(object.getIp().toString().substring(0)); //CHANGE TO 1 WHEN NOT USING LOCALHOST
                client.setObject(object);
                clientThread = new Thread(client);
                clientThread.start();
            } else if(query.contains("update")) {
                System.out.println("update");
                statement.executeUpdate(query);
                pw.write("write "+query+"\n");
               System.out.println("updating");
               if(!object.isUpdated()){
                System.out.println("object is not updated");
                Sender sender = new Sender(object);
                sender.setAction(ValidAction.UPDATE);
                // show number of rows updated
                sender.updateNodes(object.getDatabase(), object);
               }
                //int update = statement.executeUpdate(query);
                //update other tables
                // write in log
            } else if(query.contains("insert into")) {
                int insert = statement.executeUpdate(query);
                // write in log
                pw.write("write "+query+"\n");
            } else {
                // error due to invalid statement
            }
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
            try {
                
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void updateNodes(GenericObject object){
        String query = object.getQuery().toLowerCase();
        try {
            System.out.println("In update Nodes");
            System.out.println("OBJECT DB USED: "+object.getDbName());
            statement = connection.prepareStatement(query);
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    
    //sets node of the transaction
    public void setNode(NodeType node) throws SQLException{
        String query = "";
        switch(node){
            case CENTRAL: query = "use db_hpq_central";
                    break;
            case MARINDUQUE: query = "use db_hpq_marinduque";
                break;
            case PALAWAN: query = "use db_hpq_palawan";
                break;
        }
        statement = connection.prepareStatement(query);
        statement.execute(query);
        System.out.println("Node changed to " +node);
    }
    
    //sets the iso level of the transaction
    public void setIsolationLevel(IsolationLevel iso){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            switch (iso) {
                case READ_UNCOMMITTED: connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    break;
                case READ_COMMITTED: connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    break;
                case REPEATABLE_READ: connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    break;
                case SERIALIZABLE: connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                    break;
                default: connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                    
            }
            System.out.println("Isolation set to " +iso);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
