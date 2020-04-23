package Lingrui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Menu extends JFrame implements ActionListener{
	
	JPanel jp1, jp2, jp3;
	JButton jb1, jb2, jb3, jb4;
	JLabel jlb1,jlb2;
	JTextField jtf1;
	public static String s = "";
	
	public Menu() {
		jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jtf1 = new JTextField(20);
        jlb2 = new JLabel("Logger file path");
        jlb1 = new JLabel("Temperature and Humidity Manager System");
        jb1 = new JButton("History");
        jb1.addActionListener(this);
		jb1.setActionCommand("History");
        jb2 = new JButton("History emergency");
        jb2.addActionListener(this);
		jb2.setActionCommand("History emergency");
		jb3 = new JButton("Recorder");
        jb3.addActionListener(this);
		jb3.setActionCommand("Recorder");
		jb4 = new JButton("Emergency Manager");
        jb4.addActionListener(this);
		jb4.setActionCommand("Emergency Manager");
        this.setLayout(null);
        jp1.add(jlb1);
        jp2.add(jlb2);
        jp2.add(jtf1);
        
        jp1.setBounds(0, 20, 400, 50);
        jb1.setBounds(20, 100, 150, 50);
        jb2.setBounds(210, 100, 150, 50);
        jb3.setBounds(20, 180, 150, 50);
        jb4.setBounds(210, 180, 150, 50);
        jp2.setBounds(0, 250, 400, 200);
        //jp2.add(jb1);
        //jp2.add(jb2);
        //jp3.add(jb3);
        //jp3.add(jb4);
        this.add(jp1);
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);
        this.add(jp2);
        //this.add(jp2);
        //this.add(jp3);
        
		setTitle("Menu");
		setSize(400, 350);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		s = jtf1.getText();
		if(jtf1.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter file path!", "Warning",JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (e.getActionCommand().equals("Recorder")){
			this.dispose();
			RealTime r = new RealTime();
		}
		else if (e.getActionCommand().equals("History")){
			this.dispose();
			Recorder M = new Recorder();
		}
		else if (e.getActionCommand().equals("Emergency Manager")){
			this.dispose();
			RealTimeManager M = new RealTimeManager();
		}
		else if (e.getActionCommand().equals("History emergency")){
			this.dispose();
			Manager M = new Manager();
		}
	}
	
	public static void main(String[] args) {
		Menu T = new Menu();
	}
}