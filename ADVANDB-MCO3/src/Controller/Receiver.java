/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Transaction.Transaction;
import Helper.ValidAction;
import Model.GenericObject;
import View.MainView;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author WilliamPC
 */
public class Receiver {
    
    private GenericObject object;
    private Transaction transaction;
    public Receiver(GenericObject object){
        this.object = object;
    }
    
    
    public void UnpackObject() throws SQLException{
        if(object.getAction() == ValidAction.SET_NODE){
            transaction = new Transaction(object.getDbName());
            transaction.setIsolationLevel(object.getIso());
            try {
                transaction.setNode(object.getDatabase(), object.getQuery());
            } catch (SQLException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(object.getAction() == ValidAction.START_TRANSACTION){
            System.out.println("start transaction");
            transaction = new Transaction(object.getDbName());
            transaction.setIsolationLevel(object.getIso());
            transaction.startTransaction();
        }
        else if(object.getAction() == ValidAction.SET_ISOLATION_LEVEL){
            transaction = new Transaction(object.getDbName());
            transaction.setIsolationLevel(object.getIso());
        }
        else if(object.getAction() == ValidAction.QUERY){
            transaction = new Transaction(object.getDbName());
            transaction.setIsolationLevel(object.getIso());
            transaction.ProcessQuery(object);
        }
        else if(object.getAction() == ValidAction.READ){
            System.out.println("READ");
            MainView.UpdateView(extractData(object.getcRow()));
        }
        else if(object.getAction() == ValidAction.END_TRANSACTION){
            transaction = new Transaction(object.getDbName());
            transaction.closeTransaction(object);
            System.out.println("Transaction closed");
        }
        else if(object.getAction() == ValidAction.UPDATE){
            transaction = new Transaction(object.getDbName());
            transaction.updateNodes(object);
            
        }
        else if(object.getAction() == ValidAction.COMMIT){
            transaction = new Transaction(object.getDbName());
            transaction.commitNodes(object);
        }
    }
    
    public DefaultTableModel extractData(CachedRowSet set) throws SQLException{
        ResultSetMetaData metaData = set.getMetaData();
        System.out.println("extracting");
        
    // names of columns
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }

    // data of the table
    Vector<Vector<Object>> data = new Vector<>();
    set.beforeFirst();
    while (set.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(set.getObject(columnIndex));
            System.out.println("Added new object");
        }
        data.add(vector);
    }
        System.out.println("Size of data " +data.size());
    return new DefaultTableModel(data, columnNames);
    }
}
