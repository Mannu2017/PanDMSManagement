package admin;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

import model.WorkData;
import utility.DataUtility;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BulkUpadate extends Thread{
	private String empid,fullname;
	private JTextField finward;
	private JTextField tinward;
	DefaultTableModel model;

	DataUtility utility=new DataUtility();
	private JTable dataview;
	JLabel lblStatus;
	
	public BulkUpadate(String empid, String fullname) {
		this.empid=empid;
		this.fullname=fullname;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		JFrame frame=new JFrame("Welcome "+fullname);
		frame.getContentPane().setLayout(null);
		frame.setSize(358, 419);
		
		lblStatus = new JLabel("Status:");
		lblStatus.setForeground(new Color(51, 0, 0));
		lblStatus.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 13));
		lblStatus.setBounds(20, 372, 306, 14);
		frame.getContentPane().add(lblStatus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 74, 332, 287);
		frame.getContentPane().add(scrollPane);
		
		dataview = new JTable();
		dataview.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Inward No", "Acknowledge No"
			}
		));
		scrollPane.setViewportView(dataview);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneratePDF gp=new GeneratePDF(model,lblStatus);
				gp.start();
			}
		});
		btnUpdate.setForeground(new Color(102, 0, 0));
		btnUpdate.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		btnUpdate.setBackground(new Color(230, 230, 250));
		btnUpdate.setBounds(184, 40, 86, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JDialog.setDefaultLookAndFeelDecorated(true);
				if(finward.getText().trim().isEmpty() ) {
					JOptionPane.showMessageDialog(null, "Please enter From Inward No", "Info", JOptionPane.INFORMATION_MESSAGE);
					finward.requestFocus();
				} else if (tinward.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter To Inward No", "Info", JOptionPane.INFORMATION_MESSAGE);
					tinward.requestFocus();
				} else {
					model=(DefaultTableModel) dataview.getModel();
					model.setRowCount(0);
					Object[] trow=new Object[2];
					List<WorkData> workDatas=utility.WorkAssign(finward.getText().trim(),tinward.getText().trim(),empid);
					for(WorkData wd:workDatas) {
						trow[0]=wd.getInwardno();
						trow[1]=wd.getAckno();
						model.addRow(trow);	
					}
				}
				
			}
		});
		btnSubmit.setForeground(new Color(102, 0, 0));
		btnSubmit.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		btnSubmit.setBackground(new Color(230, 230, 250));
		btnSubmit.setBounds(90, 40, 86, 23);
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblToInward = new JLabel("To Inward:");
		lblToInward.setForeground(new Color(51, 0, 0));
		lblToInward.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 13));
		lblToInward.setBounds(188, 15, 72, 14);
		frame.getContentPane().add(lblToInward);
		
		tinward = new JTextField();
		tinward.setToolTipText("User Name");
		tinward.setForeground(new Color(0, 0, 51));
		tinward.setFont(new Font("Cambria Math", Font.BOLD, 12));
		tinward.setColumns(10);
		tinward.setBounds(256, 11, 86, 21);
		frame.getContentPane().add(tinward);
		
		finward = new JTextField();
		finward.setToolTipText("User Name");
		finward.setForeground(new Color(0, 0, 51));
		finward.setFont(new Font("Cambria Math", Font.BOLD, 12));
		finward.setColumns(10);
		finward.setBounds(92, 11, 86, 21);
		frame.getContentPane().add(finward);
		
		JLabel lblFromInward = new JLabel("From Inward:");
		lblFromInward.setForeground(new Color(51, 0, 0));
		lblFromInward.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 13));
		lblFromInward.setBounds(10, 15, 86, 14);
		frame.getContentPane().add(lblFromInward);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(BulkUpadate.class.getResource("/admin/5 (2).jpg")));
		label.setBounds(0, 0, 352, 391);
		frame.getContentPane().add(label);
		frame.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
