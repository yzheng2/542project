package dao;

import java.sql.*;

import vo.*;
import dbc.DatabaseConnection;

public class IInvestorsDAO {
	private DatabaseConnection dbc = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	public IInvestorsDAO(){// instance class
		try {
			dbc = new DatabaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.conn = this.dbc.getConnection();
	}
	
	public Investors getInvestorById(Investors investor) throws Exception {
		try {
			String sql = "select userName, sex, userID, Assets from investors where userID = ?";
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, investor.getuserID());
			ResultSet rs = this.pstmt.executeQuery();
			if (rs.next()) {
				investor.setuserName(rs.getString("userName"));
				investor.setsex(rs.getString("sex"));
				investor.setAssets(rs.getDouble("Assets"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			this.pstmt.close();
			this.dbc.close();
		}
		return investor;
	}
	/** This function is to increase amount of money for the investor
	  * @param String investorID, double assets
	  * @return true/false
	  * @throws Exception 
	  * @exception exceptions database exceptions
	  */ 
	public boolean increaseMoney(String investorID, double assets) throws Exception {
		boolean result = true;
		try {
			String sql = "update investors set Assets = Assets+? where userID = ?";
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setDouble(1, assets);
			this.pstmt.setString(2, investorID);
			int rs = this.pstmt.executeUpdate();
			if(rs<1) return false;
		} catch (Exception e) {
			throw e;
		} finally {
			this.pstmt.close();
			this.dbc.close();
		}
		return result;
	}
}
