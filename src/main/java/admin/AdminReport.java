package admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import model.Report;
import operator.OperatorPage;
import utility.DataUtility;

public class AdminReport extends Thread{
	private String empid,fullname,sysip,username;
	private InetAddress ipAddr;
	DataUtility utility=new DataUtility();
	private JTable dataview;
	JXDatePicker tdate,fdate;
	DefaultTableModel model;

	public AdminReport(String fullname) {
		this.fullname=fullname;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		JFrame frame=new JFrame("Welcome "+fullname);
		frame.setSize(617,403);
		frame.setResizable(false);
		frame.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 591, 318);
		frame.getContentPane().add(scrollPane);
		
		dataview = new JTable();
		dataview.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmpId", "Emp Name", "Date", "Total", "Pending", "Complete", "Reject"
			}
		));
		scrollPane.setViewportView(dataview);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model=(DefaultTableModel) dataview.getModel();
				model.setRowCount(0);
				Object[] row=new Object[10];
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				List<Report> reports=utility.AdminReport(df.format(fdate.getDate()),df.format(tdate.getDate()));
				for(Report repo : reports) {
					row[0]=repo.getEmpid();
					row[1]=repo.getFullname();
					row[2]=repo.getDate();
					row[3]=repo.getTotalack();
					row[4]=repo.getPending();
					row[5]=repo.getComplete();
					row[6]=repo.getReject();
					model.addRow(row);
				}
				
			}
		});
		btnSubmit.setBackground(new Color(204, 255, 255));
		btnSubmit.setForeground(new Color(51, 0, 0));
		btnSubmit.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		btnSubmit.setBounds(437, 11, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		tdate = new JXDatePicker();
		tdate.setFormats(new String[] {"yyyy-MM-dd"});
		tdate.setBounds(321, 11, 110, 22);
		tdate.setDate(new Date());
		frame.getContentPane().add(tdate);
		
		JLabel lblToDate = new JLabel("To Date:");
		lblToDate.setForeground(new Color(51, 0, 0));
		lblToDate.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		lblToDate.setBounds(257, 14, 72, 14);
		frame.getContentPane().add(lblToDate);
		
		fdate = new JXDatePicker();
		fdate.setFormats(new String[] {"yyyy-MM-dd"});
		fdate.setBounds(145, 11, 110, 22);
		fdate.setDate(new Date());
		frame.getContentPane().add(fdate);
		
		JLabel lblFromDate = new JLabel("From Date:");
		lblFromDate.setForeground(new Color(51, 0, 0));
		lblFromDate.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		lblFromDate.setBounds(59, 14, 90, 14);
		frame.getContentPane().add(lblFromDate);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(OperatorPage.class.getResource("/operator/5 (2).jpg")));
		label.setBounds(0, 0, 611, 375);
		frame.getContentPane().add(label);
		frame.setVisible(true);
		
	}
}
