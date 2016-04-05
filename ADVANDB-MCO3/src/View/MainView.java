/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

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
    
    private JTable table;
    
    
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
    
    
    public MainView(){
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
        
        query = new TextArea(5,10);
        
        JScrollPane scroll = new JScrollPane(query,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       
        central = new JRadioButton("Central");
        palawan = new JRadioButton("Palawan");
        marinduque = new JRadioButton("Marinduque");
        readUn = new JRadioButton("Read Uncommitted");
        readCom = new JRadioButton("Read Committed");
        readRe = new JRadioButton("Read Repeatable");
        serial = new JRadioButton("Serializable");
        
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
        readUn.setSelected(true);
        
        
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
                begin.setEnabled(false);
            }
            
        });
        end.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                begin.setEnabled(true);
                end.setEnabled(false);
            }
            
        });
    }
}
