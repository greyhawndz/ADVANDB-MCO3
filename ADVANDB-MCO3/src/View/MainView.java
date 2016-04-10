/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Driver;
import Controller.Sender;
import Controller.Server;
import Controller.Transaction.Transaction;
import Helper.IsolationLevel;
import Helper.NodeType;
import Helper.ValidAction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author WilliamPC
 */
public class MainView extends JFrame {
    private Toolkit tk;
    
    private JPanel mainPanel;
    private JPanel queryPanel;
    private JPanel querySub;
    private JPanel resultPanel;
    private JPanel nodePanel;
    private JPanel transactionPanel;
    private JPanel isoPanel;
    
    private static JTable table;
    
    
    private JButton executeQuery;
    private JButton setIsoLevel;
    private JButton setNode;
    private JButton begin;
    private JButton end;
    
    private JRadioButton central;
    private JRadioButton palawan;
    private JRadioButton marinduque;
    private JRadioButton readUn;
    private JRadioButton readCom;
    private JRadioButton readRe;
    private JRadioButton serial;
    
    private ButtonGroup node;
    private ButtonGroup iso;
    private TextArea query;
    
    private Server server;
    public MainView(){
        
        try {
            server = new Server();
            Thread serverThread = new Thread(server);
            serverThread.start();
            
        } catch (IOException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setSize(1200, 700);
        
        this.setResizable(false);
        this.setTitle("MCO3");
        
        drawComponents();
        setListeners();
        
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void drawComponents(){
        TitledBorder title = BorderFactory.createTitledBorder("Select Node");
        TitledBorder trans = BorderFactory.createTitledBorder("Transaction");
        TitledBorder isoTitle = BorderFactory.createTitledBorder("Isolation Level");
        mainPanel = new JPanel();
        queryPanel = new JPanel();
        querySub = new JPanel();
        resultPanel = new JPanel();
        nodePanel = new JPanel();
        transactionPanel = new JPanel();
        isoPanel = new JPanel();
        
        nodePanel.setBorder(title);
        transactionPanel.setBorder(trans);
        isoPanel.setBorder(isoTitle);
        
        table = new JTable();
        
       
        
        executeQuery = new JButton("Execute");
        setIsoLevel = new JButton("Set Isolation Level");
        setNode = new JButton("Set Node");
        begin = new JButton("Begin Transaction");
        end = new JButton("End Transaction");
        end.setEnabled(false);
        setNode.setEnabled(false);
        setIsoLevel.setEnabled(false);
        executeQuery.setEnabled(false);
        
        query = new TextArea(5,10);
        query.setEnabled(false);
        
        JScrollPane scroll = new JScrollPane(query,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       
        central = new JRadioButton("Central");
        palawan = new JRadioButton("Palawan");
        marinduque = new JRadioButton("Marinduque");
        readUn = new JRadioButton("Read Uncommitted");
        readCom = new JRadioButton("Read Committed");
        readRe = new JRadioButton("Read Repeatable");
        serial = new JRadioButton("Serializable");
        central.setEnabled(false);
        palawan.setEnabled(false);
        marinduque.setEnabled(false);
        readUn.setEnabled(false);
        readCom.setEnabled(false);
        readRe.setEnabled(false);
        serial.setEnabled(false);
        
        node = new ButtonGroup();
        node.add(central);
        node.add(palawan);
        node.add(marinduque);
        central.setSelected(true);
        
        iso = new ButtonGroup();
        iso.add(readUn);
        iso.add(readCom);
        iso.add(readRe);
        iso.add(serial);
        serial.setSelected(true);
        
        
        queryPanel.setBorder(new EmptyBorder(10,10,10,10));
        resultPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
        
//        queryPanel.setBackground(Color.BLUE);
//        resultPanel.setBackground(Color.red);
        
        JScrollPane scroll2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        transactionPanel.add(begin);
        transactionPanel.add(end);
        nodePanel.add(central);
        nodePanel.add(palawan);
        nodePanel.add(marinduque);
        nodePanel.add(setNode);
        querySub.add(nodePanel);
        isoPanel.add(readUn);
        isoPanel.add(readCom);
        isoPanel.add(readRe);
        isoPanel.add(serial);
        isoPanel.add(setIsoLevel);
        querySub.add(isoPanel);
        querySub.add(executeQuery);
        queryPanel.add(scroll);
        queryPanel.add(querySub);
        resultPanel.add(scroll2);
        mainPanel.add(transactionPanel);
        mainPanel.add(queryPanel);
        mainPanel.add(resultPanel);
        this.add(mainPanel);
    }
    
    public void setListeners(){
        begin.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                end.setEnabled(true);
                setNode.setEnabled(true);
                setIsoLevel.setEnabled(true);
                executeQuery.setEnabled(true);
                central.setEnabled(true);
                palawan.setEnabled(true);
                marinduque.setEnabled(true);
                readUn.setEnabled(true);
                readCom.setEnabled(true);
                readRe.setEnabled(true);
                serial.setEnabled(true);
                query.setEnabled(true);
                begin.setEnabled(false);
                Sender sender = new Sender(ValidAction.START_TRANSACTION);
                sender.startTransaction(SelectNode());
                System.out.println("clicked");
            }
            
        });
        end.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                begin.setEnabled(true);
                end.setEnabled(false);
                setNode.setEnabled(false);
                query.setEnabled(false);
                setIsoLevel.setEnabled(false);
                executeQuery.setEnabled(false);
                central.setEnabled(false);
                palawan.setEnabled(false);
                marinduque.setEnabled(false);
                readUn.setEnabled(false);
                readCom.setEnabled(false);
                readRe.setEnabled(false);
                serial.setEnabled(false);
                
                Sender sender = new Sender(ValidAction.END_TRANSACTION);
                sender.endTransaction(SelectNode());
               
            }
            
        });
        
        setNode.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
              //  transaction.setNode(SelectNode());
                Sender sender = new Sender(ValidAction.SET_NODE);
                sender.setNode(SelectNode());
            }
        
        
        
        });
        
        setIsoLevel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               Sender sender = new Sender(ValidAction.SET_ISOLATION_LEVEL);
               sender.setIsolationLevel(SelectNode(), SelectIso());
            }
        
        });
        
        executeQuery.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               Sender sender = new Sender(ValidAction.QUERY);
               sender.executeQuery(SelectNode(), query.getText());
            }
        
        
        });
    }
    
    public static void UpdateView(DefaultTableModel model){
        table.setModel(model);
        table.revalidate();
    }
    
    public NodeType SelectNode(){
        if(central.isSelected()){
            return NodeType.CENTRAL;
        }
        else if(palawan.isSelected()){
            return NodeType.PALAWAN;
        }
        else if(marinduque.isSelected()){
            return NodeType.MARINDUQUE;
        }
        else{
            return NodeType.CENTRAL;
        }
    }
    
    public IsolationLevel SelectIso(){
        if(readUn.isSelected()){
            return IsolationLevel.READ_UNCOMMITTED;
        }
        else if(readCom.isSelected()){
            return IsolationLevel.READ_COMMITTED;
        }
        else if(readRe.isSelected()){
            return IsolationLevel.REPEATABLE_READ;
        }
        else if(serial.isSelected()){
            return IsolationLevel.SERIALIZABLE;
        }
        else{
            return IsolationLevel.SERIALIZABLE;
        }
    }
}
