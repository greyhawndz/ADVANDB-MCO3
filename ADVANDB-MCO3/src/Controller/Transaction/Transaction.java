/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Transaction;

import Controller.DBConnector;
import Helper.IsolationLevel;
import Helper.NodeType;
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
    private static final String ipPalawan = "";
    private static final String ipCentral = "";
    private static final String ipMarinduque = "";
    private DBConnector connector;
    private Connection connection;
    private ResultSet result;
    private PreparedStatement statement;
    private JTable table;
    
    public Transaction(String dbName){
        connector = DBConnector.getInstance(dbName);
        connection = connector.getConnect();
        setIsolationLevel(IsolationLevel.SERIALIZABLE);
    }
    
    public void startTransaction() {
        System.out.println("Transaciton started");
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean closeTransaction() {
        boolean committed = false;
        try {
            // listen to other nodes before commit
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
            statement = connection.prepareStatement(query);
            if(query.contains("select")) {
                ResultSet result = statement.executeQuery(query);
                //set result in table
                table = new JTable();
                table.setModel(DbUtils.resultSetToTableModel(result));
            } else if(query.contains("update")) {
                // show number of rows updated
                int update = statement.executeUpdate(query);
                // write in log
            } else if(query.contains("insert into")) {
                int insert = statement.executeUpdate(query);
                // write in log
            } else {
                // error due to invalid statement
            }
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //sets node of the transaction
    public void setNode(NodeType node, String query) throws SQLException{
        statement = connection.prepareStatement(query);
        statement.execute(query);
        System.out.println("Node changed to " +node);
    }
    
    //sets the iso level of the transaction
    public void setIsolationLevel(IsolationLevel iso){
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
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
