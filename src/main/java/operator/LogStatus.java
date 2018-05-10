package operator;

import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import utility.DataUtility;

public class LogStatus extends TimerTask {
	private String sysip,username;
	
	DataUtility utility=new DataUtility();

	public LogStatus(String hostAddress, String username) {
		this.username=username;
		this.sysip=hostAddress;
	}

	@Override
	public void run() {
		JDialog.setDefaultLookAndFeelDecorated(true);
		int logstatus=utility.logstatus(username,sysip);
		if(logstatus==2) {
			JOptionPane.showMessageDialog(null, "User Login different systems", "Info", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

}
