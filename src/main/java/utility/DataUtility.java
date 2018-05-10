package utility;

import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.DbCon;
import model.User;
import model.WorkData;

public class DataUtility {
	private Connection con;
	public DataUtility() {
		this.con=DbCon.DbCon();
	}
	
	public User getUser(String username, String pass) {
		User user=new User();
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			String enpass=username+pass;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(enpass.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
			
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call nsdllogin (?,?)}");
			cs.setString(1, username);
			cs.setString(2, sb.toString());
			boolean sta=cs.execute();
			int roweff=0;
			while (sta || roweff!=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					roweff=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			while (rs.next()) {
				user.setStatus(rs.getInt(1));
				user.setEmpid(rs.getString(2));
				user.setFullname(rs.getString(3));
				user.setRole(rs.getString(4));
				user.setLogip(rs.getString(5));
			}
			cs.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return user;
		}
		return user;
	}

	public int chnagePass(String usernametxt, String oldpass, String conpass) {
		int status = 0;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			String enpass=usernametxt+oldpass;
			String encp=usernametxt+conpass;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(enpass.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
			
			MessageDigest md1 = MessageDigest.getInstance("MD5");
			md1.update(encp.getBytes());
			byte[] bytes1 = md1.digest();
			StringBuilder sb1 = new StringBuilder();
			for(int i=0; i< bytes1.length ;i++)
            {
                sb1.append(Integer.toString((bytes1[i] & 0xff) + 0x100, 16).substring(1));
            }
			
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call nsdlchpass (?,?,?)}");
			cs.setString(1, usernametxt);
			cs.setString(2, sb.toString());
			cs.setString(3, sb1.toString());
			boolean sta=cs.execute();
			int roweff=0;
			while (sta || roweff!=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					roweff=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			while (rs.next()) {
				status=rs.getInt(1);
			}
			rs.close();
			cs.close();
			con.close();
		} catch (Exception e) {
			return status;
		}
		return status;
	}

	public void updateIP(String username, String hostAddress) {
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			PreparedStatement ps=con.prepareStatement("update NSDLUserMaster set LogIP='"+hostAddress+"',LogStatus='1' where UserName='"+username+"'");
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int logstatus(String username, String sysip) {
		int sta=0;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			PreparedStatement ps=con.prepareStatement("select UserName from NSDLUserMaster where UserName='"+username+"' and LogIP='"+sysip+"'");
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				sta=1;
			} else {
				sta=2;
			}
		} catch (Exception e) {
			return sta;
		}
		return sta;
	}

	public int logout(String username) {
		int sta=0;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			PreparedStatement ps=con.prepareStatement("update NSDLUserMaster set LogStatus='0' where UserName='"+username+"'");
			boolean b = ps.execute();
			if(b) {
				sta=1;
			}
		} catch (Exception e) {
			return sta;
		}
		return sta;
	}
	
	public List<String> getRejLIst() {
		List<String> rejlist=new ArrayList<String>();
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			PreparedStatement ps=con.prepareStatement("select * from nsdlrejection order by id");
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				rejlist.add(rs.getString(2));
			}
			ps.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return rejlist;
		}
		
		return rejlist;
	}

	public List<WorkData> WorkAssign(String text, String text2, String empid) {
		List<WorkData> workDatas=new ArrayList<WorkData>();
		WorkData workData=null;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			ResultSet rs=null;
			CallableStatement cb=con.prepareCall("{call WorkAssignNSDL (?,?,?)}");
			cb.setString(1, text);
			cb.setString(2, text2);
			cb.setString(3, empid);
			boolean results=cb.execute();
			int rowef=0;
			while (results || rowef != -1) {
				if(results) {
					rs=cb.getResultSet();
					break;
				} else {
					rowef=cb.getUpdateCount();
				}
				results=cb.getMoreResults();
			}
			int i=0;
			while (rs.next()) {
				i=1+i;
				workData=new WorkData();
				workData.setTotack(i);
				workData.setInwardno(rs.getString(1));
				workData.setAckno(rs.getString(2));
				workDatas.add(workData);
			}
			cb.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return workDatas;
		}
		return workDatas;
	}

	public List<String> getFilePath(Object ackno) {
		List<String> files=new ArrayList<String>();
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			ResultSet rs=null;
			CallableStatement cb=con.prepareCall("{call getpath (?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			cb.setString(1,  ackno.toString());
			boolean result=cb.execute();
			int roweff=0;
			while (result || roweff !=-1) {
				if(result) {
					rs=cb.getResultSet();
					break;
				} else {
					roweff=cb.getUpdateCount();
				}
				result=cb.getMoreResults();
			}
			while (rs.next()) {
				files.add(rs.getString(1));
			}
			rs.close();
			cb.close();
			con.close();
			
		} catch (SQLException e) {
			return files;
		}
		return files;
	}

	public int SaveData(Object inward, Object ack) {
		int sta=0;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			System.out.println("ack: "+inward.toString()+"^"+ack.toString());
			
			PreparedStatement ps=con.prepareStatement("update PanNSDLWorkAssign set WorkStatis=1,UpdateDateTime=GETDATE() where InwardNo='"+inward.toString()+"' and AckNo='"+ack.toString()+"'");
			boolean rs=ps.execute();
			if(rs=true) {
				sta=1;
			} else {
				sta=2;
			}
			ps.close();
			con.close();
		} catch (Exception e) {
			return sta;
		}
		return sta;
	}

	public int RejData(Object inward, Object ack, Object Reject) {
		int sta=0;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			System.out.println("ack: "+inward.toString()+"^"+ack.toString());
			
			PreparedStatement ps=con.prepareStatement("update PanNSDLWorkAssign set WorkStatis=3,rejid='"+Reject.toString()+"',RejdateTime=GETDATE() where InwardNo='"+inward.toString()+"' and AckNo='"+ack.toString()+"'");
			boolean rs=ps.execute();
			if(rs=true) {
				sta=1;
			} else {
				sta=2;
			}
			ps.close();
			con.close();
		} catch (Exception e) {
			return sta;
		}
		return sta;
	}

}
