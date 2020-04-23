package Lingrui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.Timer;
import java.awt.Toolkit;

public class RealTimeManager extends JFrame implements ActionListener{
	
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private Vector<String> dataTitle = new Vector<String>();
	JPanel jp5, jp2, jp3, jp4;
	JScrollPane jp1;
	JButton jb1,jb2, jb3;
	JLabel jlb1,jlb2,jlb3,jlb4,jlb5;
	JTable tb;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5;
	Timer timer;
	InputStreamReader reader;
	BufferedReader br;
	
	public RealTimeManager(){
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
        jb1 = new JButton("Begin");
		jb1.addActionListener(this);
        jb2 = new JButton("Stop");
        jb2.addActionListener(this);
        jb3 = new JButton("Back");
        jb3.addActionListener(this);
        jtf1 = new JTextField(5);
        jtf2 = new JTextField(5);
        jtf3 = new JTextField(5);
        jtf4 = new JTextField(5);
        jlb1 = new JLabel("Max temperature");
        jlb2 = new JLabel("Min temperature");
        jlb3 = new JLabel("Max Humidity");
        jlb4 = new JLabel("Min Humidity");
        jlb5 = new JLabel("Configuration");
        this.setLayout(null);
		dataTitle.add("Temperature");
		dataTitle.add("Humidity");
		dataTitle.add("Date");
		dataTitle.add("Emergency type");
		
		tb = new JTable(data,dataTitle);//create a dummy table
		DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
		jp1.setViewportView(tb);
		jp2.add(jlb5);
		jp3.add(jlb1);
		jp3.add(jtf1);
		jp3.add(jlb3);
		jp3.add(jtf3);
		jp4.add(jlb2);
		jp4.add(jtf2);
		jp4.add(jlb4);
		jp4.add(jtf4);
        jp5.add(jb1);
        jp5.add(jb2);
        jp5.add(jb3);

        jp1.setBounds(0, 0, 687, 250);
        jp2.setBounds(190, 285, 300, 40);
        jp3.setBounds(40, 350, 587, 50);
        jp4.setBounds(40, 415, 587, 50);
        jb1.setBounds(50, 500, 100, 40);
		jb2.setBounds(280, 500, 100, 40);
		jb3.setBounds(510, 500, 100, 40);
        
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
        //this.add(jp5);
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);

		setTitle("Emergency Manager");
		setSize(700, 600);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timer = new Timer(200,this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof JButton){
			JButton jbTemp = (JButton) e.getSource();
			if(jb1 == jbTemp) {
				if(jtf1.getText().trim().equals("") || jtf2.getText().trim().equals("") || jtf3.getText().trim().equals("") || jtf4.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please finish the configuration!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				Double maxT = Double.parseDouble(jtf1.getText());
				Double minT = Double.parseDouble(jtf2.getText());
				Double maxH = Double.parseDouble(jtf3.getText());
				Double minH = Double.parseDouble(jtf4.getText());
				if(maxT<minT) {
					JOptionPane.showMessageDialog(null, "Max temperature should be greater than min temperature!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(maxH<minH) {
					JOptionPane.showMessageDialog(null, "Max humidity should be greater than min humidity!", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(!timer.isRunning()) {
					jb1.setEnabled(false);
					jtf1.setEditable(false);
					jtf2.setEditable(false);
					jtf3.setEditable(false);
					jtf4.setEditable(false);
					timer.start();
				}
			}
			else if(jb2 == jbTemp){
				if(timer.isRunning()) {
					timer.stop();
					jtf1.setEditable(true);
					jtf2.setEditable(true);
					jtf3.setEditable(true);
					jtf4.setEditable(true);
					jb1.setEnabled(true);
				}
			}
			else if(jb3 == jbTemp){
				this.dispose();
				timer.stop();
				Menu M= new Menu();
				M.jtf1.setText(Menu.s);
			}
	     }else {
				DefaultTableModel dtm=(DefaultTableModel)tb.getModel();
				//dtm.setRowCount( 0 );
				try {
					Double maxT = Double.parseDouble(jtf1.getText());
					Double minT = Double.parseDouble(jtf2.getText());
					Double maxH = Double.parseDouble(jtf3.getText());
					Double minH = Double.parseDouble(jtf4.getText());
					String line = "";
					line = br.readLine();
					while(line != null) {
						Vector vData = new Vector();
						String[] str = line.split(",");
						double temprature = Double.parseDouble(str[1].substring(0,str[1].length()-2));
						double humidity = Double.parseDouble(str[2].substring(0,str[2].length()-3));
						if(temprature > maxT || temprature < minT || humidity > maxH || humidity < minH) {
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
							JScrollBar vertical = jp1.getVerticalScrollBar();
							vertical.setValue( vertical.getMaximum() );
							Toolkit toolkit = Toolkit.getDefaultToolkit();
							toolkit.beep();
						}
						line = br.readLine();
					}
				}catch (IOException er) {
		            er.printStackTrace();
		            timer.stop();
		            jtf1.setEditable(true);
					jtf2.setEditable(true);
					jtf3.setEditable(true);
					jtf4.setEditable(true);
		            jb1.setEnabled(true);
		            JOptionPane.showMessageDialog(null, "There is no record file, please plug in the recorder!", "Warning",JOptionPane.WARNING_MESSAGE);
		            return;
		        }
	     }
	}
	
	public static void main(String[] args){
		RealTimeManager T = new RealTimeManager();
	}
}
