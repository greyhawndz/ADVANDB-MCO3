/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Helper.IsolationLevel;
import Helper.NodeType;
import Helper.ValidAction;
import java.io.Serializable;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.util.List;
import javax.sql.rowset.CachedRowSet;


/**
 *
 * @author WilliamPC
 */
public class GenericObject implements Serializable {
    
    private NodeType database;
    private NodeType source;
    private InetAddress ip;
    private boolean committed;
    private String query;
    private boolean updated;
    private ValidAction action;
    private String dbName;
    private IsolationLevel iso;
    private CachedRowSet cRow;
    

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
    
    public GenericObject(NodeType database, ValidAction action, IsolationLevel iso, String dbName){
        this.database = database;
        this.action = action;
        this.iso = iso;
        this.dbName = dbName;
    }

    public IsolationLevel getIso() {
        return iso;
    }

    public void setIso(IsolationLevel iso) {
        this.iso = iso;
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
    
    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public CachedRowSet getcRow() {
        return cRow;
    }

    public void setcRow(CachedRowSet cRow) {
        this.cRow = cRow;
    }

    public NodeType getSource() {
        return source;
    }

    public void setSource(NodeType source) {
        this.source = source;
    }

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    
    
    
}
