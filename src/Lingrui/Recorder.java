package Lingrui;

import javax.swing.*;
import javax.swing.table.*;

import Lingrui.Menu;

import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
 
public class Recorder extends JFrame implements ActionListener{
	
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private Vector<String> dataTitle = new Vector<String>();
	JPanel jp3, jp4, jp5;
	JScrollPane jp1;
	JButton jb1, jb2;
	JLabel jlb2,jlb3;
	JTextField jtf2,jtf3;
	JTable tb;
	InputStreamReader reader;
	BufferedReader br;
	
	public Recorder() {
		try{
			File filename = new File(Menu.s);
			reader = new InputStreamReader(new FileInputStream(filename));
			br = new BufferedReader(reader);
			br.readLine();
		}catch (IOException er) {
            JOptionPane.showMessageDialog(null, "There is no record file, please plug in the recorder!", "Warning",JOptionPane.WARNING_MESSAGE);
            Menu m = new Menu();
            m.jtf1.setText(Menu.s);
            return;
        }
		jp1 = new JScrollPane();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();
        jlb2 = new JLabel("Begin date");
        jlb3 = new JLabel("End date");
        jb1 = new JButton("Begin");
		jb1.addActionListener(this);
		jb1.setActionCommand("Begin");
        jb2 = new JButton("Back");
        jb2.addActionListener(this);
		jb2.setActionCommand("Back");
        jtf2 = new JTextField(20);
        jtf3 = new JTextField(20);
        this.setLayout(null);
		dataTitle.add("Temperature");
		dataTitle.add("Humidity");
		dataTitle.add("Date");
		
		tb = new JTable(data,dataTitle);//create a dummy table
		DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
		jp1.setViewportView(tb);
		
		jp1.setBounds(0, 0, 587, 300);
		jp4.setBounds(150, 320, 300, 40);
		jp5.setBounds(145, 360, 300, 40);
		//jp3.setBounds(150, 400, 300, 100);
		jb1.setBounds(100, 410, 100, 30);
		jb2.setBounds(400, 410, 100, 30);
		
        //jp3.add(jb1);
        //jp3.add(jb2);
        jp4.add(jlb2);
        jp4.add(jtf2);
        jp5.add(jlb3);
        jp5.add(jtf3);

		
		this.add(jp1);
        this.add(jp4);
        this.add(jp5);
        this.add(jb1);
        this.add(jb2);
        
        setResizable(false);
		setTitle("History");
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static boolean check(String s) {
		if(s.length()!=19)return false;
		if(!(s.charAt(0)>'0'||s.charAt(0)<'9'))return false;
		if(!(s.charAt(1)>'0'||s.charAt(1)<'9'))return false;
		if(!(s.charAt(2)>'0'||s.charAt(2)<'9'))return false;
		if(!(s.charAt(3)>'0'||s.charAt(3)<'9'))return false;
		if(s.charAt(4)!='-')return false;
		if(!(s.charAt(5)>'0'||s.charAt(5)<'9'))return false;
		if(!(s.charAt(6)>'0'||s.charAt(6)<'9'))return false;
		if(s.charAt(7)!='-')return false;
		if(!(s.charAt(8)>'0'||s.charAt(8)<'9'))return false;
		if(!(s.charAt(9)>'0'||s.charAt(9)<'9'))return false;
		if(s.charAt(10)!=' ')return false;
		if(!(s.charAt(11)>'0'||s.charAt(11)<'9'))return false;
		if(!(s.charAt(12)>'0'||s.charAt(12)<'9'))return false;
		if(s.charAt(13)!=':')return false;
		if(!(s.charAt(14)>'0'||s.charAt(14)<'9'))return false;
		if(!(s.charAt(15)>'0'||s.charAt(15)<'9'))return false;
		if(s.charAt(16)!=':')return false;
		if(!(s.charAt(17)>'0'||s.charAt(17)<'9'))return false;
		if(!(s.charAt(18)>'0'||s.charAt(18)<'9'))return false;
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("Begin")){
			DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
			dtm.setRowCount( 0 );
			try {
				File filename = new File(Menu.s);
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
				BufferedReader br = new BufferedReader(reader);
				if(jtf2.getText().trim().equals("") || jtf3.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter date!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				String begin = jtf2.getText();
				String end = jtf3.getText();
				if(!check(begin)||!check(end)) {
					JOptionPane.showMessageDialog(null, "Date format should be:YYYY-MM-DD HH:mm:ss", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(begin.compareTo(end)>0) {
					JOptionPane.showMessageDialog(null, "The end date should be larger than begin date!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				String line = "";
				line = br.readLine();
				line = br.readLine();
				while(line != null) {
					Vector vData = new Vector();
					String[] str = line.split(",");
					if(str[0].compareTo(begin) >= 0 && str[0].compareTo(end) <= 0) {
						vData.add(str[1].substring(0,str[1].length()-2));
						vData.add(str[2].substring(0,str[2].length()-3));
						vData.add(str[0]);
						dtm.addRow(vData);
					}
					line = br.readLine();
				}
				reader.close();
				br.close();
			}catch (IOException er) {
	            er.printStackTrace();
	            JOptionPane.showMessageDialog(null, "There is no record file, please plug in the recorder!", "Warning",JOptionPane.WARNING_MESSAGE);
	            return;
	        }
		}
		else if (e.getActionCommand().equals("Back")){
			this.dispose();
			Menu M = new Menu();
			M.jtf1.setText(Menu.s);
		}
	}
	
	public static void main(String[] args) {
		Recorder T = new Recorder();
	}
}
