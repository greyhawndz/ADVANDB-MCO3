/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

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
    
    private JTable table;
    
    private JComboBox isoLevel;
    private String[] isoValues = {"Read Uncommited", "Read Committed", "Read Repeatable", "Serializable"};
    private JComboBox actions;
    private String[] actionValues = {"Start Transaction", "Read", "Update", "Commit", "Rollback"};
    
    private JButton execute;
    
    private TextArea query;
    
    
    public MainView(){
        this.setSize(1000, 700);
        
        this.setResizable(false);
        this.setTitle("MCO3");
        
        drawComponents();
        setListeners();
        
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void drawComponents(){
        mainPanel = new JPanel();
        queryPanel = new JPanel();
        querySub = new JPanel();
        resultPanel = new JPanel();
        
        table = new JTable();
        
        isoLevel = new JComboBox(isoValues);
        actions = new JComboBox(actionValues);
        
        execute = new JButton("Execute");
        
        query = new TextArea(5,10);
        
        JScrollPane scroll = new JScrollPane(query,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        queryPanel.setBorder(new EmptyBorder(30,30,30,30));
        resultPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
        
//        queryPanel.setBackground(Color.BLUE);
//        resultPanel.setBackground(Color.red);
        
        JScrollPane scroll2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        querySub.add(isoLevel);
        querySub.add(actions);
        querySub.add(execute);
        queryPanel.add(scroll);
        queryPanel.add(querySub);
        resultPanel.add(scroll2);
        mainPanel.add(queryPanel);
        mainPanel.add(resultPanel);
        this.add(mainPanel);
    }
    
    public void setListeners(){
        
    }
}
