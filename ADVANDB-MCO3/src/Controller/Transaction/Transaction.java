/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Transaction;

import Controller.DBConnector;
import Controller.QueryHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Connection connect;
    private ResultSet result;
    private PreparedStatement statement;
    private JTable table;
    public Transaction(){
        connector = DBConnector.getInstance();
        connect = connector.getConnect();
        setIsolationLevel(3); //sets the isolation to serializable by default
    }
    
    public void ProcessQuery(){
        
        
        
    }
    
    //sets node of the transaction
    public static void setNode(int node){
        switch(node){
           //Use connect.setCatalog()
        }
    }
    
    //sets the iso level of the transaction
    public static void setIsolationLevel(int iso){
        
    }
    
    
}
