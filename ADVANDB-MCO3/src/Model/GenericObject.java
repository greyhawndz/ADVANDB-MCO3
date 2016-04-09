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
    private NodeType destination; // node where to go
    private NodeType origin; // node where it came from
    private String query; // sql statement
    private boolean updated; // commited or not
    private ResultSet rs = null;

    public GenericObject() {
    }

    public ResultSet getRs() {
        return rs;   
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

//    if(destination == NodeType.MARINDUQUE || origin == NodeType.MARINDUQUE) {
//            // do something to marinduque schema
//        } else {
//            // do something to palawan schema
//        }
    public GenericObject(NodeType destination, NodeType origin, String query, boolean updated) {
        this.destination = destination;
        this.origin = origin;
        this.query = query;
        this.updated = updated;
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

    public NodeType getDestination() {
        return destination;
    }

    public void setDestination(NodeType destination) {
        this.destination = destination;
    }

    public NodeType getOrigin() {
        return origin;
    }

    public void setOrigin(NodeType origin) {
        this.origin = origin;
    }
    
    
}
