package login;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mannu.App;

import admin.adminpage;
import model.User;
import operator.FlagPage;
import operator.LogStatus;
import utility.DataUtility;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Login extends JFrame{
	
	DataUtility utility=new DataUtility();
	
	private JLabel loginimg;
	private JTextField usernametxt;
	private JPasswordField newpass;
	private JPasswordField conpass;
	private JPasswordField oldpass;

	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Timer timer=new Timer();
				timer.schedule(new BuldPDF(), 0,1000*60*30);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/login/icon.png")));
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setForeground(Color.WHITE);
		System.out.println("Hello Mannu");
		setTitle("Pan Login");
		setResizable(false);
		setSize(376, 171);
		setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);
		getContentPane().setLayout(null);
		
		loginimg = new JLabel("");
		loginimg.setBounds(0, 0, 182, 171);
		ImageIcon ico=new ImageIcon(getClass().getResource("panimg.jpg"));
		Image img=ico.getImage();
		Image image=img.getScaledInstance(loginimg.getWidth(), loginimg.getHeight(), Image.SCALE_SMOOTH);
		loginimg.setIcon(new ImageIcon(image));
		getContentPane().add(loginimg);
		
		JLabel lblPanDmsManagement = new JLabel("Pan DMS Management");
		lblPanDmsManagement.setForeground(Color.BLUE);
		lblPanDmsManagement.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		lblPanDmsManagement.setBounds(192, 11, 181, 14);
		getContentPane().add(lblPanDmsManagement);
		
		usname = new JTextField();
		usname.setForeground(new Color(0, 0, 51));
		usname.setFont(new Font("Cambria Math", Font.BOLD, 12));
		usname.setToolTipText("User Name");
		usname.setBounds(202, 49, 159, 26);
		getContentPane().add(usname);
		usname.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
		lblUserName.setForeground(new Color(51, 0, 0));
		lblUserName.setBounds(202, 36, 72, 14);
		getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(51, 0, 0));
		lblPassword.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
		lblPassword.setBounds(202, 77, 72, 14);
		getContentPane().add(lblPassword);
		
		passtxt = new JPasswordField();
		passtxt.setForeground(new Color(0, 0, 51));
		passtxt.setBounds(202, 92, 159, 26);
		getContentPane().add(passtxt);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				if(usname.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Username !!", "Info", JOptionPane.WARNING_MESSAGE);
				} else if(passtxt.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Password !!", "Info", JOptionPane.WARNING_MESSAGE);
				} else {
					User user=utility.getUser(usname.getText().trim(),passtxt.getText().trim());
					if(user.getStatus()==0) {
						JOptionPane.showMessageDialog(null, "invalid username or password", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
						usname.setText("");
						passtxt.setText("");
						usname.requestFocus();
					} else if(user.getStatus()==2) {
						JOptionPane.showMessageDialog(null, "inactive user id", "Info", JOptionPane.WARNING_MESSAGE);
					} else if (user.getStatus()==3) {
						int exppass=JOptionPane.showConfirmDialog(null, "Password Expire..\nDou You want to Change?","Password Expire",JOptionPane.YES_NO_OPTION);
						if(exppass == JOptionPane.YES_OPTION) {
							System.out.println("Yes Option");
							dispose();
							JFrame frame=new JFrame("Change Password");
							frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/login/icon.png")));
							frame.getContentPane().setBackground(new Color(255, 255, 255));
							frame.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);
							frame.setSize(459, 241);
							frame.getContentPane().setLayout(null);
							
							JLabel lblPanChangePassword = new JLabel("Pan Change Password");
							lblPanChangePassword.setHorizontalAlignment(SwingConstants.CENTER);
							lblPanChangePassword.setForeground(Color.BLUE);
							lblPanChangePassword.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
							lblPanChangePassword.setBounds(251, 11, 208, 14);
							frame.getContentPane().add(lblPanChangePassword);
							
							JLabel uslblc = new JLabel("User Name");
							uslblc.setForeground(new Color(51, 0, 0));
							uslblc.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
							uslblc.setBounds(264, 36, 72, 14);
							frame.getContentPane().add(uslblc);
							
							usernametxt = new JTextField();
							usernametxt.setToolTipText("User Name");
							usernametxt.setForeground(new Color(0, 0, 51));
							usernametxt.setFont(new Font("Cambria Math", Font.BOLD, 12));
							usernametxt.setColumns(10);
							usernametxt.setBounds(264, 49, 175, 26);
							frame.getContentPane().add(usernametxt);
							
							JLabel passch = new JLabel("New Password");
							passch.setForeground(new Color(51, 0, 0));
							passch.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
							passch.setBounds(264, 115, 86, 14);
							frame.getContentPane().add(passch);
							
							newpass = new JPasswordField();
							newpass.setForeground(new Color(0, 0, 51));
							newpass.setBounds(264, 130, 175, 26);
							frame.getContentPane().add(newpass);
							
							JLabel conchlbl = new JLabel("Conf Password");
							conchlbl.setForeground(new Color(51, 0, 0));
							conchlbl.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
							conchlbl.setBounds(264, 156, 86, 14);
							frame.getContentPane().add(conchlbl);
							
							conpass = new JPasswordField();
							conpass.setForeground(new Color(0, 0, 51));
							conpass.setBounds(264, 171, 175, 26);
							frame.getContentPane().add(conpass);
							
							JLabel changepassimg = new JLabel("");
							changepassimg.setBounds(0, 0, 254, 241);
							ImageIcon ico=new ImageIcon(getClass().getResource("panimg.jpg"));
							Image img=ico.getImage();
							Image image=img.getScaledInstance(changepassimg.getWidth(), changepassimg.getHeight(), Image.SCALE_SMOOTH);
							changepassimg.setIcon(new ImageIcon(image));
							frame.getContentPane().add(changepassimg);
							
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
							frame.getContentPane().add(btnChnage);
							
							JButton btnClose = new JButton("Close");
							btnClose.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									System.exit(0);
								}
							});
							btnClose.setForeground(new Color(102, 0, 0));
							btnClose.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
							btnClose.setBackground(new Color(230, 230, 250));
							btnClose.setBounds(353, 206, 86, 23);
							frame.getContentPane().add(btnClose);
							
							JLabel label = new JLabel("Password");
							label.setForeground(new Color(51, 0, 0));
							label.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 12));
							label.setBounds(264, 75, 72, 14);
							frame.getContentPane().add(label);
							
							oldpass = new JPasswordField();
							oldpass.setForeground(new Color(0, 0, 51));
							oldpass.setBounds(264, 90, 175, 26);
							frame.getContentPane().add(oldpass);
							frame.setResizable(false);
							frame.setUndecorated(true);
							frame.setVisible(true);
							
						} else if (exppass == JOptionPane.NO_OPTION) {
							System.out.println("No Option");
						}
					} else if (user.getStatus()==4) {
						
						int exppass=JOptionPane.showConfirmDialog(null, "User Already Login. Dou you want Login\nIp Address: "+user.getLogip(),"Password Expire",JOptionPane.YES_NO_OPTION);
						if(exppass==JOptionPane.YES_OPTION) {
							if(user.getRole().equals("Admin")) {
								
								System.out.println("Mannu Admin Page");
								adminpage ap=new adminpage(user.getEmpid(),user.getFullname(),user.getLogip(),usname.getText());
								ap.start();
								dispose();
								
							} else if (user.getRole().equals("Flag")) {
								
								System.out.println("Hello Flag Operator");
								FlagPage fp=new FlagPage(user.getEmpid(),user.getFullname(),user.getLogip(),usname.getText());
								fp.start();
								dispose();
								
							}
							
						} else if (exppass==JOptionPane.NO_OPTION) {
							System.out.println("No Option");
						}
						
					} else if(user.getStatus()==1) {
						
						if(user.getRole().equals("Admin")) {
							
							System.out.println("Mannu Admin Page");
							adminpage ap=new adminpage(user.getEmpid(),user.getFullname(),user.getLogip(),usname.getText());
							ap.start();
							dispose();
							
						} else if (user.getRole().equals("Flag")) {
							
							System.out.println("Hello Flag Operator");
							FlagPage fp=new FlagPage(user.getEmpid(),user.getFullname(),user.getLogip(),usname.getText());
							fp.start();
							dispose();
							
						}
						
					}
				}
			}
		});
		btnLogin.setBackground(new Color(230, 230, 250));
		btnLogin.setForeground(new Color(102, 0, 0));
		btnLogin.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		btnLogin.setBounds(202, 129, 72, 23);
		getContentPane().add(btnLogin);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setForeground(new Color(102, 0, 0));
		btnClose.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		btnClose.setBackground(new Color(230, 230, 250));
		btnClose.setBounds(288, 129, 73, 23);
		getContentPane().add(btnClose);
		setUndecorated(true);
		setVisible(true);
		
	}
	
	
	public void setArgs(String[] args) {
		this.args=args;
	}

	public String[] args;
	private JTextField usname;
	private JPasswordField passtxt;
}
