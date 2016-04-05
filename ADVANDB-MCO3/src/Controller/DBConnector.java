/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author WilliamPC
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnector {
    private final static String DB_NAME = "db_hpq_central";
    private final static String URL_NAME = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private final static String DRIVER = "com.mysql.jdbc.Driver" ;
    private final static String USERNAME = "root";
    private final static String PASSWORD = "water";
    private static DBConnector connector;
    private Connection connect;
    
    
    private DBConnector(){
        
        
        try{
            Class.forName(DRIVER).newInstance();
            connect = DriverManager.getConnection(URL_NAME, USERNAME, PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("no connection");
        }
    }
    
    public Connection getConnection(){
        
        
        return connect;
    }
    
    

    
    public Connection getConnect(){
        return connect;
    }
    
    public static DBConnector getInstance(){
        if(connector == null){
            connector = new DBConnector();
        }
        return connector;
    }
    
   
}