package operator;

import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Image;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import utility.DataUtility;

import javax.swing.JMenu;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;

public class FlagPage extends Thread{
	private String empid,fullname,sysip,username;
	InetAddress ipAddr;
	DataUtility utility=new DataUtility();
	
	public FlagPage(String empid, String fullname, String logip, String username) {
		this.empid=empid;
		this.fullname=fullname;
		this.sysip=logip;
		this.username=username;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		try {
			ipAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		final JFrame frame=new JFrame("Welcome "+fullname+"       Emp Id: "+empid+"     Ip Address: "+ipAddr.getHostAddress()+"     Last Login Ip: "+sysip);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				utility.updateIP(username,ipAddr.getHostAddress());
				Timer timer=new Timer();
				timer.schedule(new LogStatus(ipAddr.getHostAddress(),username), 0,1000*30*1);
			}
		});
		frame.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FlagPage.class.getResource("/operator/icon.png")));
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handleClosing();
            }
        });
		frame.getContentPane().setLayout(null);
		JLabel imglbl=new JLabel();
		ImageIcon icon=new ImageIcon(FlagPage.class.getResource("/operator/5 (2).jpg"));
		Image img=icon.getImage();
		Image image=img.getScaledInstance((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Image.SCALE_SMOOTH);
		
		JLabel label = new JLabel("");
		label.setBounds(889, 97, 233, 60);
		ImageIcon ico1	=new ImageIcon(FlagPage.class.getResource("/operator/karvygrouplogo.png"));
		Image img1=ico1.getImage();
		Image image2=img1.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		
		JLabel lblCopyright = new JLabel("@ Karvy Data Management Limited 2018");
		lblCopyright.setFont(new Font("Bookman Old Style", Font.BOLD, 11));
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setBounds(20, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100, 343, 14);
		frame.getContentPane().add(lblCopyright);
		label.setIcon(new ImageIcon(image2));
		frame.getContentPane().add(label);
		
		imglbl.setIcon(new ImageIcon(image));
		imglbl.setSize(frame.getWidth(), frame.getHeight());
		frame.getContentPane().add(imglbl);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnWork = new JMenu("Work");
		mnWork.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FlagOperator fo=new FlagOperator(username,ipAddr.getHostAddress(),fullname,empid);
				fo.start();
				frame.dispose();
			}
		});
		mnWork.setForeground(new Color(0, 0, 0));
		mnWork.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		menuBar.add(mnWork);
		
		JMenu mnReport = new JMenu("Report");
		mnReport.setForeground(Color.BLACK);
		mnReport.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		menuBar.add(mnReport);
		
		JMenu mnChangePassword = new JMenu("Change Password");
		mnChangePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChangePass cp=new ChangePass();
				cp.start();
			}
		});
		mnChangePassword.setForeground(Color.BLACK);
		mnChangePassword.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		menuBar.add(mnChangePassword);
		
		JMenu mnLogOut = new JMenu("Log Out");
		mnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				int exppass=JOptionPane.showConfirmDialog(null, "Dou You want to Exit?","Info",JOptionPane.YES_NO_OPTION);
				if(exppass==JOptionPane.YES_OPTION) {
					int ss=utility.logout(username);
					if(ss==1) {
						System.exit(0);
					} else {
						System.exit(0);
					}
				} else if (exppass==JOptionPane.NO_OPTION) {
					System.out.println("No Option");
				}
			}
		});
		mnLogOut.setForeground(Color.BLACK);
		mnLogOut.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		menuBar.add(mnLogOut);
		frame.setVisible(true);
	}

	protected void handleClosing() {
		JDialog.setDefaultLookAndFeelDecorated(true);
		int exppass=JOptionPane.showConfirmDialog(null, "Dou You want to Exit?","Info",JOptionPane.YES_NO_OPTION);
		if(exppass==JOptionPane.YES_OPTION) {
			int ss=utility.logout(username);
			if(ss==1) {
				System.exit(0);
			} else {
				System.exit(0);
			}
		} else if (exppass==JOptionPane.NO_OPTION) {
			System.out.println("No Option");
		}
	}
}
