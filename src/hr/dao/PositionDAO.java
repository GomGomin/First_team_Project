package hr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hr.util.DBCon;

public class PositionDAO {

	private Connection con = DBCon.getConnection();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;

	public String positionName(int positionNo) {
		query = "Select position from t_position where positionno = ?";
		String name = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, positionNo); // ? 자리수
			rs = pstmt.executeQuery();
			if (rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return name;
	}

	public int positionNum(String PositionName) {
		query = "Select positionno from t_position where position = ?";
		int num = 0;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, PositionName.trim());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			// 직위명을 잘못 입력한 경우 오류메세지 출력
			System.out.print("직위명을 잘못 입력하였습니다.\n");
		} finally {
			DBCon.close(rs, pstmt);
		}
		return num;
	}
}
