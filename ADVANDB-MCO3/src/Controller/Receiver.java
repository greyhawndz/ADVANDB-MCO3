/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Transaction.Transaction;
import Helper.ValidAction;
import Model.GenericObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    
    public void UnpackObject(){
        if(object.getAction() == ValidAction.SET_NODE){
            transaction = new Transaction(object.getDbName());
            try {
                transaction.setNode(object.getDatabase(), object.getQuery());
            } catch (SQLException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(object.getAction() == ValidAction.START_TRANSACTION){
            System.out.println("start transaction");
            transaction = new Transaction(object.getDbName());
            transaction.startTransaction();
        }
    }
}
