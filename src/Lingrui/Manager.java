package Lingrui;

import javax.swing.*;
import javax.swing.table.*;

import Lingrui.Menu;

import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Manager extends JFrame implements ActionListener{
	
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private Vector<String> dataTitle = new Vector<String>();
	JPanel jp2, jp3, jp4, jp5, jp6, jp7;
	JScrollPane jp1;
	JButton jb1, jb2;
	JLabel jlb1,jlb2,jlb3,jlb4,jlb5,jlb6,jlb7;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JTable tb;
	InputStreamReader reader;
	BufferedReader br;
	
	public Manager() {
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
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();
        jlb1 = new JLabel("Begin date");
        jlb2 = new JLabel("End date");
        jlb3 = new JLabel("Max temperature");
        jlb4 = new JLabel("Min temperature");
        jlb5 = new JLabel("Max humidity");
        jlb6 = new JLabel("Min humidity");
        jlb7 = new JLabel("Configuration");
        jb1 = new JButton("Filter");
        jb1.addActionListener(this);
		jb1.setActionCommand("Filter");
        jb2 = new JButton("Back");
        jb2.addActionListener(this);
		jb2.setActionCommand("Back");
        jtf1 = new JTextField(20);
        jtf2 = new JTextField(20);
        jtf3 = new JTextField(5);
        jtf4 = new JTextField(5);
        jtf5 = new JTextField(5);
        jtf6 = new JTextField(5);
        
        this.setLayout(null);
		dataTitle.add("Temperature");
		dataTitle.add("Humidity");
		dataTitle.add("Date");
		dataTitle.add("Emergency type");
		
		tb = new JTable(data,dataTitle);//create a dummy table
		DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
		jp1.setViewportView(tb);

		jp2.add(jlb7);
		jp3.add(jlb1);
		jp3.add(jtf1);
		jp4.add(jlb2);
		jp4.add(jtf2);
		jp5.add(jlb3);
		jp5.add(jtf3);
		jp5.add(jlb5);
		jp5.add(jtf5);
		jp6.add(jlb4);
		jp6.add(jtf4);
		jp6.add(jlb6);
		jp6.add(jtf6);
		//jp7.add(jb1);
		//jp7.add(jb2);
		
		jp1.setBounds(0, 0, 687, 250);
		jp2.setBounds(190, 270, 300, 40);
		jp3.setBounds(180, 310, 300, 40);
		jp4.setBounds(180, 350, 300, 40);
		jp5.setBounds(40, 400, 587, 50);
		jp6.setBounds(40, 450, 587, 50);
		//jp7.setBounds(40, 510, 587, 300);
		jb1.setBounds(100, 510, 100, 40);
		jb2.setBounds(500, 510, 100, 40);
		
		this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
        this.add(jp6);
        //this.add(jp7);
        this.add(jb1);
        this.add(jb2);
		
		setTitle("History Emergency");
		setSize(700, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("Filter")){
			DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
			dtm.setRowCount( 0 );
			try {
				File filename = new File(Menu.s);
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
				BufferedReader br = new BufferedReader(reader);
				if(jtf1.getText().trim().equals("") || jtf2.getText().trim().equals("") || jtf3.getText().trim().equals("") || jtf4.getText().trim().equals("") || jtf5.getText().trim().equals("") || jtf6.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please finish the configuration!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				String begin = jtf1.getText();
				String end = jtf2.getText();
				if(!Recorder.check(begin)||!Recorder.check(end)) {
					JOptionPane.showMessageDialog(null, "Date format should be:YYYY-MM-DD HH:mm:ss", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(begin.compareTo(end)>0) {
					JOptionPane.showMessageDialog(null, "The end date should be larger than begin date!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				Double maxT = Double.parseDouble(jtf3.getText());
				Double minT = Double.parseDouble(jtf4.getText());
				Double maxH = Double.parseDouble(jtf5.getText());
				Double minH = Double.parseDouble(jtf6.getText());
				if(maxT<minT) {
					JOptionPane.showMessageDialog(null, "Max temperature should be greater than min temperature!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(maxH<minH) {
					JOptionPane.showMessageDialog(null, "Max humidity should be greater than min humidity!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				String line = "";
				line = br.readLine();
				line = br.readLine();
				while(line != null) {
					Vector vData = new Vector();
					String[] str = line.split(",");
					System.out.println(str[1].substring(0,str[1].length()-2));
					System.out.println(str[2].substring(0,str[2].length()-3));
					double temprature = Double.parseDouble(str[1].substring(0,str[1].length()-2));
					double humidity = Double.parseDouble(str[2].substring(0,str[2].length()-3));
					if(str[0].compareTo(begin) >= 0 && str[0].compareTo(end) <= 0 && (temprature > maxT || temprature < minT || humidity > maxH || humidity < minH)) {
						vData.add(str[1].substring(0,str[1].length()-2));
						vData.add(str[2].substring(0,str[2].length()-3));
						vData.add(str[0]);
						String type = "";
						if(temprature > maxT || temprature < minT){
							type += "Temperature ";
						}
						if(humidity > maxH || humidity < minH){
							type += "Humidity ";
						}
						vData.add(type);
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
		Manager T = new Manager();
	}
}