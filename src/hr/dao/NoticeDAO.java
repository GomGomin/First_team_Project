package hr.dao;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

import hr.util.DBCon;
import hr.vo.*;

public class NoticeDAO {

	private Connection con = DBCon.getConnection();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;

	public List<NoticeVO> noticeSelectAll() {
		List<NoticeVO> noticelist = new ArrayList<>();
		query = "select noticeno, noticetitle, content, to_char(insertdate, 'yyyy.mm.dd') as day, name "
				+ "from t_notice n inner join t_employee e on (n.noticewriter = e.id) order by insertdate asc";
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NoticeVO nvo = new NoticeVO();
				nvo.setNoticeNumber(rs.getInt(1));
				nvo.setNoticeTitle(rs.getString(2));
				nvo.setNoticeContents(rs.getString(3));
				nvo.setNoticeDate(rs.getString(4));
				nvo.setNoticeWriter(rs.getString(5));
				noticelist.add(nvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return noticelist;
	}

	public List<NoticeVO> noticeSearchList(String search) {
		List<NoticeVO> noticeSearchList = new ArrayList<>();
		query = "select noticeno, noticetitle, content, to_char(insertdate, 'yyyy.mm.dd') as day, e.name "
				+ "from t_notice n inner join t_employee e on (n.noticewriter = e.id) where noticetitle like '%' || ? || '%' "
				+ "order by insertdate asc";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, search);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NoticeVO nvo = new NoticeVO();
				nvo.setNoticeNumber(rs.getInt(1));
				nvo.setNoticeTitle(rs.getString(2));
				nvo.setNoticeContents(rs.getString(3));
				nvo.setNoticeDate(rs.getString(4));
				nvo.setNoticeWriter(rs.getString(5));
				noticeSearchList.add(nvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return noticeSearchList;
	}

	public NoticeVO noticeInfo(int noticeNumber) {
		query = "select noticeno, noticetitle, content, to_char(insertdate, 'yyyy.mm.dd') as day, e.name "
				+ "from t_notice n inner join t_employee e on (n.noticewriter = e.id) where noticeno = ?";
		NoticeVO nvo = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNumber);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				nvo = new NoticeVO();
				nvo.setNoticeNumber(rs.getInt(1));
				nvo.setNoticeTitle(rs.getString(2));
				nvo.setNoticeContents(rs.getString(3));
				nvo.setNoticeDate(rs.getString(4));
				nvo.setNoticeWriter(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return nvo;
	}

	public boolean noticeInsert(NoticeVO nvo, String id) {
		query = "insert into t_notice (NOTICETITLE, CONTENT, INSERTDATE, NOTICEWRITER) VALUES (?, ?, sysdate, ?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, nvo.getNoticeTitle());
			pstmt.setString(2, nvo.getNoticeContents());
			pstmt.setString(3, id);
			int result = pstmt.executeUpdate();
			boolean res = (result > 0) ? true : false;
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}

	public boolean noticeUpdate(NoticeVO nvo) {
		query = "update t_notice set NOTICETITLE=?, CONTENT=? where NOTICENO = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, nvo.getNoticeTitle());
			pstmt.setString(2, nvo.getNoticeContents());
			pstmt.setInt(3, nvo.getNoticeNumber());
			int result = pstmt.executeUpdate();
			boolean res = (result > 0) ? true : false;
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}

	public boolean noticeDelete(NoticeVO nvo) {
		query = "delete from t_notice where NOTICENO = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nvo.getNoticeNumber());
			int result = pstmt.executeUpdate();
			boolean res = (result > 0) ? true : false;
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}
}