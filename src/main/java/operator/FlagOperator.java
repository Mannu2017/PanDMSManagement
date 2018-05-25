package operator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utility.DataUtility;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.common.views.DocumentViewControllerImpl;
import org.icepdf.ri.util.PropertiesManager;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import model.WorkData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;

@SuppressWarnings("serial")
public class FlagOperator extends JFrame{
	private String username;
	private String sysip;
	private String fullname,empid;

	DataUtility utility=new DataUtility();
	private JTextField frominwardno;
	private JTextField toinwardno;
	private JTable dataview;
	private JComboBox<String> comboBox;
	DefaultTableModel model;
	JLabel totcou;
	JList<ImageIcon> lsm;
	File tmp=new File("C:\\temp2");
	int pc=0;
	SwingController controller;
	SwingViewBuilder factory;
	
	public FlagOperator(String username, String hostAddress, String fullname, String empid) {
		this.fullname=fullname;
		this.username=username;
		this.sysip=hostAddress;
		this.empid=empid;
		run();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		setTitle("Welcome  "+fullname+"      Emp Id: "+empid+"     IP Address: "+sysip);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FlagOperator.class.getResource("/operator/icon.png")));
		getContentPane().setLayout(null);
		setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
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
				File ff=new File("C:\\Sample\\Non Flag\\");
				if(!ff.exists()) {
					ff.mkdirs();
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
		getContentPane().add(label_1);
		
		JLabel lblForSave = new JLabel("For Save : Alt + S");
		lblForSave.setForeground(new Color(25, 25, 112));
		lblForSave.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 11));
		lblForSave.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-300, 461, 146, 14);
		getContentPane().add(lblForSave);
		
		JLabel lblShortcutKeys = new JLabel("ShortCut Keys");
		lblShortcutKeys.setForeground(new Color(240, 128, 128));
		lblShortcutKeys.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		lblShortcutKeys.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-320, 436, 146, 14);
		getContentPane().add(lblShortcutKeys);
		
		totcou = new JLabel("");
		totcou.setForeground(new Color(0, 0, 205));
		totcou.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		totcou.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-260, 179, 61, 14);
		getContentPane().add(totcou);
		
		JLabel lblTotal = new JLabel("Total :");
		lblTotal.setForeground(new Color(102, 0, 0));
		lblTotal.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		lblTotal.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-320, 179, 61, 14);
		getContentPane().add(lblTotal);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-330, 207, 291, 218);
		getContentPane().add(scrollPane_1);
		
		dataview = new JTable();
		dataview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model=(DefaultTableModel) dataview.getModel();
				int dat=dataview.getSelectedRow();
				try {
					dataprocess(model.getValueAt(dat, 0),model.getValueAt(dat, 1));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
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
					JOptionPane.showMessageDialog(null, "No Data Available in Table", "Info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					utility.SaveData(model.getValueAt(0, 0),model.getValueAt(0, 1));
					model.removeRow(dataview.getSelectedRow());
					dataview.setRowSelectionInterval(0, 0);
					if(model.getRowCount()>0) {
						try {
							dataprocess(model.getValueAt(0, 0),model.getValueAt(0, 1));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnSave.setForeground(new Color(102, 0, 0));
		btnSave.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		btnSave.setBackground(new Color(224, 255, 255));
		btnSave.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-170, 93, 89, 23);
		getContentPane().add(btnSave);
		
		JButton btnReject = new JButton("Reject");
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model=(DefaultTableModel) dataview.getModel();
				if(model.getRowCount()==0) {
					JOptionPane.showMessageDialog(null, "No Data Available", "Info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if(comboBox.getSelectedItem().equals("Rejection Type")) {
						JOptionPane.showMessageDialog(null, "Select Rejection Type", "Info", JOptionPane.INFORMATION_MESSAGE);
					} else {
						int urec=utility.RejData(model.getValueAt(0, 0),model.getValueAt(0, 1),comboBox.getSelectedItem());
						if(urec==1) {
							model.removeRow(dataview.getSelectedRow());
							dataview.setRowSelectionInterval(0, 0);
							if(model.getRowCount()>0) {
								try {
									dataprocess(model.getValueAt(0, 0),model.getValueAt(0, 1));
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}	
						} else if(urec==2) {
							JOptionPane.showMessageDialog(null, "Database Error", "Info", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		btnReject.setForeground(new Color(102, 0, 0));
		btnReject.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		btnReject.setBackground(new Color(224, 255, 255));
		btnReject.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-125, 176, 89, 22);
		getContentPane().add(btnReject);
		
		comboBox = new JComboBox<String>();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Rejection Type"}));
		comboBox.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-320, 128, 275, 20);
		getContentPane().add(comboBox);
		
//		JLabel lblType = new JLabel("Type:");
//		lblType.setForeground(new Color(102, 0, 0));
//		lblType.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
//		lblType.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-330, 130, 46, 14);
//		getContentPane().add(lblType);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				if(frominwardno.getText().trim().isEmpty() ) {
					JOptionPane.showMessageDialog(null, "Please enter From Inward No", "Info", JOptionPane.INFORMATION_MESSAGE);
					frominwardno.requestFocus();
				} else if (toinwardno.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter To Inward No", "Info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					int fin=Integer.parseInt(frominwardno.getText());
					int tin=Integer.parseInt(toinwardno.getText());
					if(tin<fin) {
						JOptionPane.showMessageDialog(null, "From Inwardno less than To Inwardno", "info", JOptionPane.INFORMATION_MESSAGE);
						frominwardno.setText("");
						toinwardno.setText("");
						frominwardno.requestFocus();
					} else if ((tin-fin)>50) {
						JOptionPane.showMessageDialog(null, "You are trying to get more then 50 Application", "info", JOptionPane.INFORMATION_MESSAGE);
						frominwardno.setText("");
						toinwardno.setText("");
						frominwardno.requestFocus();
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
						try {
							dataprocess(model.getValueAt(0, 0),model.getValueAt(0, 1));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					
				}
			}

			
		});
		
		btnSubmit.setBackground(new Color(224, 255, 255));
		btnSubmit.setForeground(new Color(102, 0, 0));
		btnSubmit.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 13));
		btnSubmit.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-280, 93, 89, 23);
		getContentPane().add(btnSubmit);
		
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
		getContentPane().add(toinwardno);
		
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
		getContentPane().add(frominwardno);
		frominwardno.setColumns(10);
		
		JLabel lblToInwardNo = new JLabel("To Inward No");
		lblToInwardNo.setForeground(new Color(102, 0, 0));
		lblToInwardNo.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblToInwardNo.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-180, 48, 136, 14);
		getContentPane().add(lblToInwardNo);
		
		JLabel lblFromInwardNo = new JLabel("From Inward No");
		lblFromInwardNo.setForeground(new Color(102, 0, 0));
		lblFromInwardNo.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 12));
		lblFromInwardNo.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-310, 48, 136, 14);
		getContentPane().add(lblFromInwardNo);
		
		JLabel label = new JLabel("");
		label.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-340, 36, 314, 132);
		label.setBorder(border);
		getContentPane().add(label);
		
		JLabel lblControlPanel = new JLabel("Control Panel");
		lblControlPanel.setForeground(Color.BLUE);
		lblControlPanel.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		lblControlPanel.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-330, 11, 123, 14);
		getContentPane().add(lblControlPanel);
		
		try {
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		JLabel lblCopyright = new JLabel("@ Karvy Data Management Limited 2018");
		lblCopyright.setFont(new Font("Bookman Old Style", Font.BOLD, 11));
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setBounds(20, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-70, 343, 14);
		getContentPane().add(lblCopyright);
		
		imglbl.setIcon(new ImageIcon(image));
		imglbl.setSize(getWidth(), getHeight());
		getContentPane().add(imglbl);
				
		setVisible(true);
	}


	protected void dataprocess(Object inwardno, Object ackno) throws Exception {
//		if(viewer.isActive()) {
//			viewer.deactivate();
//		}
//		if(imgview.isVisible()) {
//			imgview.setClosable(true);
//		}
		
		List<String> filepath=utility.getFilePath(ackno.toString());
		if(filepath.size()>0) {
			try {
				Document document=new Document();
				PdfWriter.getInstance(document, new FileOutputStream("C:\\Sample\\Non Flag\\"+inwardno+"_"+ackno+".pdf"));
				document.open();
				for(String fil:filepath) {
					com.itextpdf.text.Image img=com.itextpdf.text.Image.getInstance(fil.toString());
					document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
					document.setMargins(0, 0, 0, 0);
					document.newPage();
					document.add(img);
				}
				document.close();
				
				controller=new SwingController();
				factory = new SwingViewBuilder(controller);
				JPanel panel=factory.buildViewerPanel();
				controller.closeDocument();
				Properties properties = new Properties();
		        properties.put("application.showLocalStorageDialogs", "false");
		        PropertiesManager props = new PropertiesManager(System.getProperties(),
		                properties,
		                ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
		        props.setInt(PropertiesManager.PROPERTY_DEFAULT_PAGEFIT,
		                DocumentViewController.PAGE_FIT_WINDOW_HEIGHT);
		        props.setInt("document.viewtype",
		                DocumentViewControllerImpl.ONE_COLUMN_VIEW);
		        
		        controller.setPropertiesManager(props);
				controller.getDocumentViewController().setAnnotationCallback(
		                new org.icepdf.ri.common.MyAnnotationCallback(
		                        controller.getDocumentViewController()));
				
				panel.setBounds(0, -40, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 350, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
				getContentPane().add(panel);
				controller.openDocument("C:\\Sample\\Non Flag\\"+inwardno+"_"+ackno+".pdf");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		} else {
			JOptionPane.showMessageDialog(null, "Image Not found in Smart Server");
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
			try {
				FileUtils.cleanDirectory(new File("C:\\Sample\\Non Flag"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else if (exppass==JOptionPane.NO_OPTION) {
			System.out.println("No Option");
		}
	}
}
