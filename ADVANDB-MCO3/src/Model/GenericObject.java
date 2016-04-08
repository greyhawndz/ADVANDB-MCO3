/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Helper.NodeType;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author WilliamPC
 */
public class GenericObject implements Serializable {
    private NodeType type;
    private NodeType database;
    private String query;
    private boolean updated;
    private ResultSet rs = null;

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public GenericObject(NodeType type, NodeType database, String query, boolean updated) {
        this.type = type;
        this.database = database;
        this.query = query;
        this.updated = updated;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
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
