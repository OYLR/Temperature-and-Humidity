package Lingrui;

import javax.swing.*;
import javax.swing.table.*;

import Lingrui.Menu;

import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.Timer;

public class RealTime extends JFrame implements ActionListener{
	
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private Vector<String> dataTitle = new Vector<String>();
	JPanel jp2, jp5;
	JScrollPane jp1;
	JButton jb1, jb2, jb3;
	JLabel jlb1,jlb2,jlb3;
	JTextField jtf1;
	JTable tb;
	Timer timer;
	InputStreamReader reader;
	BufferedReader br;
	int count = 0;
	
	public RealTime() {
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
        jp5 = new JPanel();
        jlb1 = new JLabel("Frequency");
        jb1 = new JButton("Begin");
		jb1.addActionListener(this);
        jb2 = new JButton("Stop");
        jb2.addActionListener(this);
        jb3 = new JButton("Back");
        jb3.addActionListener(this);
        jtf1 = new JTextField(5);
        this.setLayout(null);
		dataTitle.add("Temperature");
		dataTitle.add("Humidity");
		dataTitle.add("Date");
		
		tb = new JTable(data,dataTitle);//create a dummy table
		DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
		jp1.setViewportView(tb);
		jp1.setBounds(0, 0, 587, 300);
		jp2.setBounds(0, 330, 600, 50);
		jb1.setBounds(50, 400, 100, 40);
		jb2.setBounds(250, 400, 100, 40);
		jb3.setBounds(450, 400, 100, 40);
		
        jp2.add(jlb1);
        jp2.add(jtf1);
        //jp5.add(jb1);
        //jp5.add(jb2);
        //jp5.add(jb3);

		
		this.add(jp1);
        this.add(jp2);
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        //this.add(jp5);

		setTitle("Recorder");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(600, 500);
		setLocationRelativeTo(null);
		timer = new Timer(200,this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof JButton){
			JButton jbTemp = (JButton) e.getSource();
			if(jb1 == jbTemp) {
				if(jtf1.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter frequency!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				String s1 = jtf1.getText();
				for (int j = 0; j < s1.length(); j++){
					if (!(s1.charAt(j) >= 48 && s1.charAt(j) <= 57)) {
						JOptionPane.showMessageDialog(null, "Please enter a positive integer for the frequency!", "Warning",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!timer.isRunning()) {
					jtf1.setEditable(false);
					jb1.setEnabled(false);
					timer.start();
				}
			}
			else if(jb2 == jbTemp){
				if(timer.isRunning()) {
					timer.stop();
					jtf1.setEditable(true);
					jb1.setEnabled(true);
				}
			}
			else if(jb3 == jbTemp){
				this.dispose();
				timer.stop();
				Menu M = new Menu();
				M.jtf1.setText(Menu.s);
			}
	     }else {
				DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
				//dtm.setRowCount( 0 );
				try {
					int fr = Integer.parseInt(jtf1.getText());
					String line = "";
					line = br.readLine();
					while(line != null) {
						Vector vData = new Vector();
						String[] str = line.split(",");
						if(count % fr == 0) {
							vData.add(str[1].substring(0,str[1].length()-2));
							vData.add(str[2].substring(0,str[2].length()-3));
							vData.add(str[0]);
							dtm.addRow(vData);
						}
						line = br.readLine();
						JScrollBar vertical = jp1.getVerticalScrollBar();
						vertical.setValue( vertical.getMaximum() );
						count++;
					}
				}catch (IOException er) {
		            er.printStackTrace();
		            timer.stop();
		            jb1.setEnabled(true);
		            jtf1.setEditable(true);
		            JOptionPane.showMessageDialog(null, "There is no record file, please plug in the recorder!", "Warning",JOptionPane.WARNING_MESSAGE);
		            return;
		        }
	     }
	}
	
	public static void main(String[] args) {
		RealTime T = new RealTime();
	}
}
