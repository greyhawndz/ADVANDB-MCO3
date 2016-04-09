/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;



import Helper.NodeType;
import Model.GenericObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author WilliamPC
 */
public class QueryHandler {
//   private static final String ip = "";
   private static NodeClient client;
   private static String ipCentral;
   private static String ipPalawan;
   private static String ipMarinduque;
   
    public static void Start() {
        BufferedReader br = null;  
        try {
           FileInputStream inputStream = null;
           String fileUrl = "";  // file address where ip address is stored
           br = new BufferedReader(new FileReader(fileUrl));
           ipCentral = br.readLine();
           ipPalawan = br.readLine();
           ipMarinduque = br.readLine();
           br.close();
        } catch (FileNotFoundException ex) {
           Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
           Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           try {
               br.close();
           } catch (IOException ex) {
               Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        String ipAddress = "";
        client = new NodeClient(ipAddress);
        Thread clientThread = new Thread(client);
        clientThread.start();

    }
   
    public static void OnNotification(int node, String query) throws SQLException{
        String ipAddress = null;
        switch (node) {
            case 1: ipAddress = ipCentral;
                break;
            case 2: ipAddress = ipPalawan;
                break;
            case 3: ipAddress = ipMarinduque;
                break;
        }
        
        GenericObject object = new GenericObject();
        object.setQuery(query);
        object.setUpdated(false);
        // set origin
        if(query.contains("db_hpq_palawan")) {
            object.setUpdated(false);
            object.setDestination(NodeType.PALAWAN);
//            object.setDestination(NodeType.CENTRAL);
                    
        } else {
            object.setDestination(NodeType.MARINDUQUE);
//            object.setDestination(NodeType.CENTRAL);
            
        }
        client.setObject(object);
        client.run();
    }
    
    //updates tableview
    public static void NotifyTableView(JTable table){
        
    }
}
