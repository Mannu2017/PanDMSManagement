package admin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import utility.DataUtility;

import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CreateUser extends Thread{
	private String fullname,empid;
	private JTextField eid;
	private JTextField usnam;
	private JTextField fnam;
	private JTextField emid;
	private JPasswordField password;
	private JPasswordField confpass;
	private JComboBox rol;

	DataUtility utility=new DataUtility();
	
	public CreateUser(String fullname, String empid) {
		this.empid=empid;
		this.fullname=fullname;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		Border border=new EtchedBorder(EtchedBorder.LOWERED, null, null);
		final JFrame frame=new JFrame("Welcome "+fullname);
		frame.setSize(344, 327);
		frame.setResizable(false);
		frame.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/6);
		frame.getContentPane().setLayout(null);
		
		JButton btnClose = new JButton("Close");
		btnClose.setForeground(new Color(102, 0, 0));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnClose.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		btnClose.setBackground(new Color(224, 255, 255));
		btnClose.setBounds(167, 251, 116, 23);
		frame.getContentPane().add(btnClose);
		
		JButton button = new JButton("Save");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				if(eid.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter Employee id", "info", JOptionPane.INFORMATION_MESSAGE);
				} else if(usnam.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter Username", "info", JOptionPane.INFORMATION_MESSAGE);
				} else if(fnam.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter Full Name", "info", JOptionPane.INFORMATION_MESSAGE);
				} else if(emid.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter Email Id", "info", JOptionPane.INFORMATION_MESSAGE);
				} else if(password.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter Password", "info", JOptionPane.INFORMATION_MESSAGE);
				} else if(confpass.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter Conf Password", "info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if(!password.getText().trim().equals(confpass.getText().trim())) {
						JOptionPane.showMessageDialog(null, "Password and Conf Password Not Match", "info", JOptionPane.INFORMATION_MESSAGE);
						password.setText("");
						confpass.setText("");
						password.requestFocus();
					} else {
						String role=null;
						if(rol.getSelectedItem().equals("Admin")) {
							role="1";
						} else if(rol.getSelectedItem().equals("Operator")) {
							role="2";
						}
						int sta=utility.newUser(emid.getText(),usnam.getText(),fnam.getText(),eid.getText(),confpass.getText(),role,empid);
						if(sta==1) {
							JOptionPane.showMessageDialog(null, "User Id Already Exists", "info", JOptionPane.INFORMATION_MESSAGE);
							emid.setText("");usnam.setText("");fnam.setText("");eid.setText("");confpass.setText("");password.setText("");
							eid.requestFocus();
						} else if (sta==2) {
							JOptionPane.showMessageDialog(null, "Done", "info", JOptionPane.INFORMATION_MESSAGE);
							emid.setText("");usnam.setText("");fnam.setText("");eid.setText("");confpass.setText("");password.setText("");
							eid.requestFocus();
						} else {
							JOptionPane.showMessageDialog(null, "Other Error", "info", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		button.setMnemonic(KeyEvent.VK_S);
		button.setForeground(new Color(102, 0, 0));
		button.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		button.setBackground(new Color(224, 255, 255));
		button.setBounds(45, 250, 116, 23);
		frame.getContentPane().add(button);
		
		confpass = new JPasswordField();
		confpass.setBounds(147, 217, 158, 20);
		frame.getContentPane().add(confpass);
		
		JLabel lblConfPassword = new JLabel("Conf Password:");
		lblConfPassword.setForeground(new Color(102, 0, 0));
		lblConfPassword.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblConfPassword.setBounds(25, 219, 136, 14);
		frame.getContentPane().add(lblConfPassword);
		
		password = new JPasswordField();
		password.setBounds(147, 190, 158, 20);
		frame.getContentPane().add(password);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(102, 0, 0));
		lblPassword.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblPassword.setBounds(25, 192, 136, 14);
		frame.getContentPane().add(lblPassword);
		
		rol = new JComboBox();
		rol.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Operator"}));
		rol.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		rol.setBackground(Color.WHITE);
		rol.setBounds(147, 161, 158, 20);
		frame.getContentPane().add(rol);
		
		JLabel lblRole = new JLabel("Role:");
		lblRole.setForeground(new Color(102, 0, 0));
		lblRole.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblRole.setBounds(25, 164, 136, 14);
		frame.getContentPane().add(lblRole);
		
		emid = new JTextField();
		emid.setForeground(new Color(0, 0, 102));
		emid.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		emid.setColumns(10);
		emid.setBounds(147, 133, 158, 20);
		frame.getContentPane().add(emid);
		
		JLabel lblEmailId = new JLabel("Email Id:");
		lblEmailId.setForeground(new Color(102, 0, 0));
		lblEmailId.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblEmailId.setBounds(25, 136, 136, 14);
		frame.getContentPane().add(lblEmailId);
		
		fnam = new JTextField();
		fnam.setForeground(new Color(0, 0, 102));
		fnam.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		fnam.setColumns(10);
		fnam.setBounds(147, 105, 158, 20);
		frame.getContentPane().add(fnam);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setForeground(new Color(102, 0, 0));
		lblUserName.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblUserName.setBounds(25, 80, 136, 14);
		frame.getContentPane().add(lblUserName);
		
		JLabel lblFullName = new JLabel("Full Name:");
		lblFullName.setForeground(new Color(102, 0, 0));
		lblFullName.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblFullName.setBounds(25, 108, 136, 14);
		frame.getContentPane().add(lblFullName);
		
		usnam = new JTextField();
		usnam.setForeground(new Color(0, 0, 102));
		usnam.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		usnam.setColumns(10);
		usnam.setBounds(147, 77, 158, 20);
		frame.getContentPane().add(usnam);
		
		JLabel lblEmployeeId = new JLabel("Employee Id:");
		lblEmployeeId.setForeground(new Color(102, 0, 0));
		lblEmployeeId.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblEmployeeId.setBounds(25, 52, 136, 14);
		frame.getContentPane().add(lblEmployeeId);
		
		eid = new JTextField();
		eid.setForeground(new Color(0, 0, 102));
		eid.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		eid.setColumns(10);
		eid.setBounds(147, 49, 158, 20);
		frame.getContentPane().add(eid);
		
		JLabel lblCreateNewUser = new JLabel("Create New User");
		lblCreateNewUser.setForeground(Color.BLUE);
		lblCreateNewUser.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		lblCreateNewUser.setBounds(25, 12, 158, 14);
		frame.getContentPane().add(lblCreateNewUser);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(10, 37, 316, 248);
		label_1.setBorder(border);
		frame.getContentPane().add(label_1);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(CreateUser.class.getResource("/admin/5 (2).jpg")));
		label.setBounds(0, 0, 357, 448);
		frame.getContentPane().add(label);
		frame.setVisible(true);
	}
}
