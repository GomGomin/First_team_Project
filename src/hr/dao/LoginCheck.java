package hr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hr.util.DBCon;

public class LoginCheck {
	
	private ResultSet rs;
	private PreparedStatement pstmt;
	private Connection con = DBCon.getConnection();

	
	//아이디와 비밀번호를 받아서 loginMap의 데이터와 일치하는지 확인
	public boolean loginChk(String id, String pw) {
		String query = "select * from t_employee where id= ? and pw = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()==true) {
				System.out.println("로그인 성공");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return false;
	}
	
}
