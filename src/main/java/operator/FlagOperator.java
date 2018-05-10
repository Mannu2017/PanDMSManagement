package operator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import utility.DataUtility;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.DocumentException;

import model.WorkData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FlagOperator extends Thread{
	private String username;
	private String sysip;
	private String fullname,empid;

	DataUtility utility=new DataUtility();
	private JTextField frominwardno;
	private JTextField toinwardno;
	private JTable dataview;
	private JComboBox comboBox;
	DefaultTableModel model;
	JLabel totcou;
	JScrollPane imgview;
	JList lsm;
	
	public FlagOperator(String username, String hostAddress, String fullname, String empid) {
		this.fullname=fullname;
		this.username=username;
		this.sysip=hostAddress;
		this.empid=empid;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		JFrame frame=new JFrame("Welcome  "+fullname+"      Emp Id: "+empid+"     IP Address: "+sysip);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FlagOperator.class.getResource("/operator/icon.png")));
		frame.getContentPane().setLayout(null);
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handleClosing();
            }
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					String baseUrl = FilenameUtils.getPath("\\\\srv-kdms-file2\\images");
					File fname=new File(baseUrl);
					Runtime rt = Runtime.getRuntime();
					if(!fname.exists()) {
						rt.exec(new String[] {"cmd.exe","/c","net use \\\\srv-kdms-file2\\images /user:logicalaccess@karvy.com India@123"});
					}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				List<String> rejlist=utility.getRejLIst();
				for(String aa:rejlist) {
					comboBox.addItem(aa);
				}
			}
        });
		JLabel imglbl=new JLabel();
		ImageIcon icon=new ImageIcon(FlagPage.class.getResource("/operator/5 (2).jpg"));
		Image img=icon.getImage();
		Image image=img.getScaledInstance((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Image.SCALE_SMOOTH);
		
		Border border=new EtchedBorder(EtchedBorder.LOWERED, null, null);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-300, 496, 224, 63);
		ImageIcon ico1	=new ImageIcon(FlagPage.class.getResource("/operator/karvygrouplogo.png"));
		Image img1=ico1.getImage();
		Image image2=img1.getScaledInstance(label_1.getWidth(), label_1.getHeight(), Image.SCALE_SMOOTH);
		label_1.setIcon(new ImageIcon(image2));
		frame.getContentPane().add(label_1);
		
		JLabel lblForSave = new JLabel("For Save : Alt + S");
		lblForSave.setForeground(new Color(25, 25, 112));
		lblForSave.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 11));
		lblForSave.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-300, 461, 146, 14);
		frame.getContentPane().add(lblForSave);
		
		JLabel lblShortcutKeys = new JLabel("ShortCut Keys");
		lblShortcutKeys.setForeground(new Color(240, 128, 128));
		lblShortcutKeys.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		lblShortcutKeys.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-320, 436, 146, 14);
		frame.getContentPane().add(lblShortcutKeys);
		
		totcou = new JLabel("");
		totcou.setForeground(new Color(0, 0, 205));
		totcou.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		totcou.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-260, 179, 61, 14);
		frame.getContentPane().add(totcou);
		
		JLabel lblTotal = new JLabel("Total :");
		lblTotal.setForeground(new Color(102, 0, 0));
		lblTotal.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		lblTotal.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-320, 179, 61, 14);
		frame.getContentPane().add(lblTotal);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-330, 207, 291, 218);
		frame.getContentPane().add(scrollPane_1);
		
		dataview = new JTable();
		dataview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model=(DefaultTableModel) dataview.getModel();
				int dat=dataview.getSelectedRow();
				dataprocess(model.getValueAt(dat, 0),model.getValueAt(dat, 1));
			}
		});
		dataview.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Inward No", "Acknowledge No"
			}
		));
		scrollPane_1.setViewportView(dataview);
		
		JButton btnSave = new JButton("Save");
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model=(DefaultTableModel) dataview.getModel();
				if(model.getRowCount()<=0) {
					JOptionPane.showMessageDialog(null, "no data available in table", "Info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					int urec=utility.SaveData(model.getValueAt(0, 0),model.getValueAt(0, 1));
					model.removeRow(dataview.getSelectedRow());
					dataview.setRowSelectionInterval(0, 0);
					model=(DefaultTableModel) dataview.getModel();
					dataprocess(model.getValueAt(0, 0),model.getValueAt(0, 1));
					
				}
			}
		});
		btnSave.setForeground(new Color(102, 0, 0));
		btnSave.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		btnSave.setBackground(new Color(224, 255, 255));
		btnSave.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-170, 93, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnReject = new JButton("Reject");
		btnReject.setForeground(new Color(102, 0, 0));
		btnReject.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		btnReject.setBackground(new Color(224, 255, 255));
		btnReject.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-115, 127, 81, 23);
		frame.getContentPane().add(btnReject);
		
		comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Rejection Type"}));
		comboBox.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-280, 128, 155, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setForeground(new Color(102, 0, 0));
		lblType.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		lblType.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-330, 130, 46, 14);
		frame.getContentPane().add(lblType);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				if(frominwardno.getText().trim().isEmpty() ) {
					JOptionPane.showMessageDialog(null, "Please enter From Inward No", "Info", JOptionPane.INFORMATION_MESSAGE);
					frominwardno.requestFocus();
				} else if (toinwardno.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter To Inward No", "Info", JOptionPane.INFORMATION_MESSAGE);
					toinwardno.requestFocus();
				} else {
					model=(DefaultTableModel) dataview.getModel();
					model.setRowCount(0);
					Object[] trow=new Object[2];
					List<WorkData> workDatas=utility.WorkAssign(frominwardno.getText(),toinwardno.getText(),empid);
					for(WorkData wd:workDatas) {
						trow[0]=wd.getInwardno();
						trow[1]=wd.getAckno();
						model.addRow(trow);
						totcou.setText(Integer.toString(wd.getTotack()));
					}
					dataview.setRowSelectionInterval(0, 0);
					model=(DefaultTableModel) dataview.getModel();
					System.out.println("Mannu Table Inward No:"+model.getValueAt(0, 0)+"_"+model.getValueAt(0, 1));
					dataprocess(model.getValueAt(0, 0),model.getValueAt(0, 1));
					
				}
			}
		});
		btnSubmit.setBackground(new Color(224, 255, 255));
		btnSubmit.setForeground(new Color(102, 0, 0));
		btnSubmit.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		btnSubmit.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-280, 93, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		toinwardno = new JTextField();
		toinwardno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				char vchar = event.getKeyChar();
		        if ((!Character.isDigit(vchar)) || (vchar == '\b') || (vchar == '') || 
		          (toinwardno.getText().length() == 10)) {
		          event.consume();
		        }
			}
		});
		toinwardno.setForeground(new Color(0, 0, 102));
		toinwardno.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		toinwardno.setColumns(10);
		toinwardno.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-180, 62, 123, 20);
		frame.getContentPane().add(toinwardno);
		
		frominwardno = new JTextField();
		frominwardno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				char vchar = event.getKeyChar();
		        if ((!Character.isDigit(vchar)) || (vchar == '\b') || (vchar == '') || 
		          (frominwardno.getText().length() == 10)) {
		          event.consume();
		        }
			}
		});
		frominwardno.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		frominwardno.setForeground(new Color(0, 0, 102));
		frominwardno.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-310, 62, 123, 20);
		frame.getContentPane().add(frominwardno);
		frominwardno.setColumns(10);
		
		JLabel lblToInwardNo = new JLabel("To Inward No");
		lblToInwardNo.setForeground(new Color(102, 0, 0));
		lblToInwardNo.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblToInwardNo.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-180, 48, 136, 14);
		frame.getContentPane().add(lblToInwardNo);
		
		JLabel lblFromInwardNo = new JLabel("From Inward No");
		lblFromInwardNo.setForeground(new Color(102, 0, 0));
		lblFromInwardNo.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblFromInwardNo.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-310, 48, 136, 14);
		frame.getContentPane().add(lblFromInwardNo);
		
		JLabel label = new JLabel("");
		label.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-340, 36, 314, 132);
		label.setBorder(border);
		frame.getContentPane().add(label);
		
		
		JLabel lblControlPanel = new JLabel("Control Panel");
		lblControlPanel.setForeground(Color.BLUE);
		lblControlPanel.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		lblControlPanel.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-330, 11, 123, 14);
		frame.getContentPane().add(lblControlPanel);
		
		imgview = new JScrollPane();
		imgview.setBounds(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-350, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-75);
		frame.getContentPane().add(imgview);
		
		JLabel lblCopyright = new JLabel("@ Karvy Data Management Limited 2018");
		lblCopyright.setFont(new Font("Bookman Old Style", Font.BOLD, 11));
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setBounds(20, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-70, 343, 14);
		frame.getContentPane().add(lblCopyright);
		
		imglbl.setIcon(new ImageIcon(image));
		imglbl.setSize(frame.getWidth(), frame.getHeight());
		frame.getContentPane().add(imglbl);
		frame.setVisible(true);
	}

	protected void dataprocess(Object inwardno, Object ackno) {
		List<String> filepath=utility.getFilePath(ackno.toString());
		if(filepath.size()>0) {
			DefaultListModel listModel = new DefaultListModel();
			listModel.removeAllElements();
			int count = 0;
			for (int i = 0; i < filepath.size(); i++) {
				try {
					ImageIcon ii = new ImageIcon(ImageIO.read(new File(filepath.get(i).toString())));
					Image image=ii.getImage();
			        Image img=image.getScaledInstance(imgview.getWidth()-50, 600, Image.SCALE_SMOOTH);
			        ImageIcon ico=new ImageIcon(img);
			        listModel.add(count++, ico);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			lsm = new JList(listModel);
		    lsm.setVisibleRowCount(1);
		    imgview.setViewportView(lsm);
		} else {
			lsm.removeAll();
			JOptionPane.showMessageDialog(null, "Image Not Found", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
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