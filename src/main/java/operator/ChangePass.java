package operator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utility.DataUtility;

public class ChangePass extends Thread{

	private JTextField usernametxt;
	private JPasswordField newpass;
	private JPasswordField conpass;
	private JPasswordField oldpass;
	
	DataUtility utility=new DataUtility();
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		final JFrame frame1=new JFrame("Change Password");
		frame1.setIconImage(Toolkit.getDefaultToolkit().getImage(ChangePass.class.getResource("/operator/icon.png")));
		frame1.getContentPane().setBackground(new Color(255, 255, 255));
		frame1.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);
		frame1.setSize(459, 241);
		frame1.getContentPane().setLayout(null);
		
		JLabel lblPanChangePassword = new JLabel("Pan Change Password");
		lblPanChangePassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPanChangePassword.setForeground(Color.BLUE);
		lblPanChangePassword.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		lblPanChangePassword.setBounds(251, 11, 208, 14);
		frame1.getContentPane().add(lblPanChangePassword);
		
		JLabel uslblc = new JLabel("User Name");
		uslblc.setForeground(new Color(51, 0, 0));
		uslblc.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
		uslblc.setBounds(264, 36, 72, 14);
		frame1.getContentPane().add(uslblc);
		
		usernametxt = new JTextField();
		usernametxt.setToolTipText("User Name");
		usernametxt.setForeground(new Color(0, 0, 51));
		usernametxt.setFont(new Font("Cambria Math", Font.BOLD, 12));
		usernametxt.setColumns(10);
		usernametxt.setBounds(264, 49, 175, 26);
		frame1.getContentPane().add(usernametxt);
		
		JLabel passch = new JLabel("New Password");
		passch.setForeground(new Color(51, 0, 0));
		passch.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
		passch.setBounds(264, 115, 86, 14);
		frame1.getContentPane().add(passch);
		
		newpass = new JPasswordField();
		newpass.setForeground(new Color(0, 0, 51));
		newpass.setBounds(264, 130, 175, 26);
		frame1.getContentPane().add(newpass);
		
		JLabel conchlbl = new JLabel("Conf Password");
		conchlbl.setForeground(new Color(51, 0, 0));
		conchlbl.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
		conchlbl.setBounds(264, 156, 86, 14);
		frame1.getContentPane().add(conchlbl);
		
		conpass = new JPasswordField();
		conpass.setForeground(new Color(0, 0, 51));
		conpass.setBounds(264, 171, 175, 26);
		frame1.getContentPane().add(conpass);
		
		JLabel changepassimg = new JLabel("");
		changepassimg.setBounds(0, 0, 254, 241);
		ImageIcon ico=new ImageIcon(ChangePass.class.getResource("/operator/panimg.jpg"));
		Image img=ico.getImage();
		Image image=img.getScaledInstance(changepassimg.getWidth(), changepassimg.getHeight(), Image.SCALE_SMOOTH);
		changepassimg.setIcon(new ImageIcon(image));
		frame1.getContentPane().add(changepassimg);
		
		JButton btnChnage = new JButton("Chnage");
		btnChnage.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(usernametxt.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Username", "Info", JOptionPane.INFORMATION_MESSAGE);
					usernametxt.requestFocus();
				} else if (oldpass.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Password", "Info", JOptionPane.INFORMATION_MESSAGE);
					oldpass.requestFocus();
				} else if (newpass.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter New Password", "Info", JOptionPane.INFORMATION_MESSAGE);
					newpass.requestFocus();
				} else if (conpass.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Conform Password", "Info", JOptionPane.INFORMATION_MESSAGE);
					conpass.requestFocus();
				} else if(newpass.getText().trim().equals(oldpass.getText().trim())){
					JOptionPane.showMessageDialog(null, "Old Password and New Password are Same\nPlease Use Different Password", "Info", JOptionPane.INFORMATION_MESSAGE);
					newpass.setText("");
					conpass.setText("");
					newpass.requestFocus();
				} else {
					if(!newpass.getText().trim().equals(conpass.getText().trim())) {
						JOptionPane.showMessageDialog(null, "New Password and Conform Password not Match", "Info", JOptionPane.INFORMATION_MESSAGE);
						newpass.setText("");
						conpass.setText("");
						newpass.requestFocus();
					} else {
						int status=utility.chnagePass(usernametxt.getText().trim(),oldpass.getText().trim(),conpass.getText().trim());
						if(status==2) {
							JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Info", JOptionPane.INFORMATION_MESSAGE);
						} else if(status==1) {
							JOptionPane.showMessageDialog(null, "Done...", "Info", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
					}
				}
			}
		});
		btnChnage.setForeground(new Color(102, 0, 0));
		btnChnage.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		btnChnage.setBackground(new Color(230, 230, 250));
		btnChnage.setBounds(264, 206, 86, 23);
		frame1.getContentPane().add(btnChnage);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame1.dispose();
			}
		});
		btnClose.setForeground(new Color(102, 0, 0));
		btnClose.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		btnClose.setBackground(new Color(230, 230, 250));
		btnClose.setBounds(353, 206, 86, 23);
		frame1.getContentPane().add(btnClose);
		
		JLabel label = new JLabel("Password");
		label.setForeground(new Color(51, 0, 0));
		label.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
		label.setBounds(264, 75, 72, 14);
		frame1.getContentPane().add(label);
		
		oldpass = new JPasswordField();
		oldpass.setForeground(new Color(0, 0, 51));
		oldpass.setBounds(264, 90, 175, 26);
		frame1.getContentPane().add(oldpass);
		frame1.setResizable(false);
		frame1.setUndecorated(true);
		frame1.setVisible(true);
	}
	
}
