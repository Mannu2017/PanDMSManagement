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
import model.Report;
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
			cb.setInt(1, Integer.parseInt(text));
			cb.setInt(2, Integer.parseInt(text2));
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

	public List<Report> OpWorkReport(String fdate, String tdate, String empid) {
		List<Report> reports=new ArrayList<Report>();
		Report report=null;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			ResultSet rs=null;
			CallableStatement cb=con.prepareCall("{call OpReport (?,?,?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			cb.setString(1,  fdate.toString());
			cb.setString(2,  tdate.toString());
			cb.setString(3,  empid.toString());
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
				report=new Report();
				report.setDate(rs.getString(1));
				report.setTotalack(rs.getInt(2));
				report.setPending(rs.getInt(3));
				report.setComplete(rs.getInt(4));
				report.setReject(rs.getInt(5));
				reports.add(report);
			}
			cb.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return reports;
		}
		return reports;
	}

	public int newUser(String email, String username, String fullname, String usempid, String confpass, String role, String empid) {
		int sta=0;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			String enpass=username+confpass;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(enpass.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call adduser (?,?,?,?,?,?,?)}");
			cs.setString(1, username);
			cs.setString(2, sb.toString());
			cs.setString(3, fullname);
			cs.setString(4, usempid);
			cs.setString(5, email);
			cs.setString(6, role);
			cs.setString(7, empid);
			boolean st=cs.execute();
			int roweff=0;
			while (st || roweff !=-1) {
				if(st) {
					rs=cs.getResultSet();
					break;
				} else {
					roweff=cs.getUpdateCount();
				}
				st=cs.getMoreResults();
			}
			while (rs.next()) {
				sta=rs.getInt(1);
			}
			cs.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return sta;
		}
		return sta;
	}

	public List<Report> AdminReport(String fdate, String tdate) {
		List<Report> reports=new ArrayList<Report>();
		Report report=null;
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			ResultSet rs=null;
			CallableStatement cb=con.prepareCall("{call Adminrepo (?,?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			cb.setString(1,  fdate.toString());
			cb.setString(2,  tdate.toString());
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
				report=new Report();
				report.setEmpid(rs.getString(1));
				report.setFullname(rs.getString(2));
				report.setDate(rs.getString(3));
				report.setTotalack(rs.getInt(4));
				report.setPending(rs.getInt(5));
				report.setComplete(rs.getInt(6));
				report.setReject(rs.getInt(7));
				reports.add(report);
			}
			cb.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return reports;
		}
		return reports;
	}

	public void adminUpdate(String string) {
		try {
			if(con.isClosed()) {
				con=DbCon.DbCon();
			}
			PreparedStatement ps=con.prepareStatement("update inward set NsdlUploadStatus=1 where Ackno='"+string+"'");
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
