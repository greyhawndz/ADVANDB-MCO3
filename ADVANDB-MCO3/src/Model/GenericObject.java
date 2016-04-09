/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Helper.NodeType;
import Helper.ValidAction;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author WilliamPC
 */
public class GenericObject implements Serializable {
    
    private NodeType database;
    private String query;
    private boolean updated;
    private ValidAction action;
    private String dbName;
    private ResultSet rs = null;

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public GenericObject(NodeType database, String query, boolean updated, ValidAction action,String dbName) {

        this.database = database;
        this.query = query;
        this.updated = updated;
        this.action = action;
        this.dbName = dbName;
    }
    
    public GenericObject(String query, boolean updated, ValidAction action) {
        this.query = query;
        this.updated = updated;
        this.action = action;
    }
    
    public GenericObject(NodeType database, ValidAction action, String dbName){
        this.action = action;
        this.database = database;
        this.dbName = dbName;
    }
    

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public ValidAction getAction() {
        return action;
    }

    public void setAction(ValidAction action) {
        this.action = action;
    }

    public NodeType getDatabase() {
        return database;
    }

    public void setDatabase(NodeType database) {
        this.database = database;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
    
    
}
