/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Transaction;

import Controller.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author WilliamPC
 */
public class Transaction {
    private DBConnector connector;
    private Connection connection;
    private ResultSet result;
    private PreparedStatement statement;
    private JTable table;
    
    public Transaction(){
        connector = DBConnector.getInstance();
        connection = connector.getConnect();
        setIsolationLevel(3); //sets the isolation to serializable by default
    }
    
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean closeTransaction() {
        boolean committed = false;
        try {
            
            connection.commit();
            committed = true;
            // unlock tables
            // update log
            // send data to other database
            connection.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return committed;
    }
    
    public void ProcessQuery(String query){
        query = query.toLowerCase();
        try {
            Statement stmt = connection.createStatement();
            if(query.contains("select")) {
                ResultSet result = stmt.executeQuery(query);
                //set result in table
                table = new JTable();
                table.setModel(DbUtils.resultSetToTableModel(result));
            } else if(query.contains("update")) {
                // show number of rows updated
                int update = stmt.executeUpdate(query);
                // write in log
            } else if(query.contains("insert into")) {
                int insert = stmt.executeUpdate(query);
                // write in log
            } else {
                // error due to invalid statement
            }
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //sets node of the transaction
    public static void setNode(int node){
        switch(node){
           //Use connect.setCatalog()
            case 1: 
                break;
            case 2: 
                break;
            case 3:
                break;
        }
    }
    
    //sets the iso level of the transaction
    public void setIsolationLevel(int iso){
        try {
            switch (iso) {
                case 0: connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    break;
                case 1: connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    break;
                case 2: connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    break;
                case 3: connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
