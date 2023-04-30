package hr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.util.DBCon;
import hr.vo.WorkRecordVO;

public class WorkRecordDAO {
	private Connection con = DBCon.getConnection();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;

	public List<WorkRecordVO> workRecordSelectName(String name) {
		query = "SELECT e.id, department, position, TO_CHAR(startworkdate, 'YYYY.MM.DD') startworkdate, "
				+ "TO_CHAR(endworkdate, 'YYYY.MM.DD') endworkdate FROM t_work w, t_position p, t_dept d, t_employee e WHERE name = ? "
				+ "and p.positionno = w.positionno and d.deptno = w.deptno and e.id = w.id";
		List<WorkRecordVO> workRecordList = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				WorkRecordVO wrvo = new WorkRecordVO();

				wrvo.setId(rs.getString("id"));
				wrvo.setDepartment(rs.getString("department"));
				wrvo.setPosition(rs.getString("position"));
				wrvo.setStartWorkingDate(rs.getString("startworkdate"));
				wrvo.setEndWorkingDate(rs.getString("endworkdate"));

				workRecordList.add(wrvo); // workRecordList에 저장
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return workRecordList; // workRecordList 객체를 반환
	}

	public List<WorkRecordVO> workRecordSelectDe(String deName) {
		query = "SELECT id, position, TO_CHAR(startworkdate, 'YYYY.MM.DD') startworkdate, "
				+ "TO_CHAR(endworkdate, 'YYYY.MM.DD') endworkdate FROM t_work w, t_position p, t_dept d WHERE department = ? "
				+ "and p.positionno = w.positionno and d.deptno = w.deptno";
		List<WorkRecordVO> workRecordList = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, deName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				WorkRecordVO wrvo = new WorkRecordVO();

				wrvo.setId(rs.getString("id"));
				wrvo.setPosition(rs.getString("position"));
				wrvo.setStartWorkingDate(rs.getString("startworkdate"));
				wrvo.setEndWorkingDate(rs.getString("endworkdate"));

				workRecordList.add(wrvo); // workRecordList에 저장
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return workRecordList; // workRecordList 객체를 반환
	}

	public int deptToDeptNo(String dept) {
		query = "SELECT deptno FROM t_dept WHERE department = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dept);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return 0;
	}

	public int positionToPositionNo(String position) {
		query = "SELECT positionno FROM t_position WHERE position = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, position);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return 0;
	}

	public boolean workRecordInsert(WorkRecordVO wvo) {
		int deptNo = deptToDeptNo(wvo.getDepartment());
		int positionNo = positionToPositionNo(wvo.getPosition());
		query = "INSERT INTO t_work VALUES(?, ?, ?, ?, TO_DATE(?, 'yyyy.mm.dd'), null)";

		try {
			pstmt = con.prepareStatement(query); // 쿼리를 실행할 PreparedStatement 객체 생성
			pstmt.setInt(1, 0);
			pstmt.setString(2, wvo.getId());
			pstmt.setInt(3, deptNo);
			pstmt.setInt(4, positionNo);
			pstmt.setString(5, wvo.getStartWorkingDate());

			int result = pstmt.executeUpdate(); // insert 쿼리를 실행하고 결과를 받아 성공 여부 확인

			if (result == 1) { // 1행이 삽입되면 데이터 추가 성공
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}

	public List<WorkRecordVO> workRecordSelectid(String id) {
		query = "SELECT workno, department, position, TO_CHAR(startworkdate, 'YYYY.MM.DD') startworkdate, "
				+ "TO_CHAR(endworkdate, 'YYYY.MM.DD') endworkdate FROM t_work w, t_position p, t_dept d WHERE id = ? "
				+ "and p.positionno = w.positionno and d.deptno = w.deptno";
		List<WorkRecordVO> workRecordList = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				WorkRecordVO wrvo = new WorkRecordVO();

				wrvo.setWorkRecordNo(rs.getInt("workno"));
				wrvo.setDepartment(rs.getString("department"));
				wrvo.setPosition(rs.getString("position"));
				wrvo.setStartWorkingDate(rs.getString("startworkdate"));
				wrvo.setEndWorkingDate(rs.getString("endworkdate"));

				workRecordList.add(wrvo); // workRecordList에 저장
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return workRecordList; // workRecordList 객체를 반환
	}

	public WorkRecordVO workRecordSelectNum(int workNo) {
		query = " SELECT id, department, position, TO_CHAR(startworkdate, 'YYYY.MM.DD') startworkdate "
				+ ", TO_CHAR(endworkdate, 'YYYY.MM.DD') endworkdate FROM t_work w, t_position p, t_dept d "
				+ " WHERE workno = ? and p.positionno = w.positionno and d.deptno = w.deptno ";
		WorkRecordVO wvo = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, workNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				wvo = new WorkRecordVO();
				wvo.setId(rs.getString("id"));
				wvo.setDepartment(rs.getString("department"));
				wvo.setPosition(rs.getString("position"));
				wvo.setStartWorkingDate(rs.getString("startworkdate"));
				wvo.setEndWorkingDate(rs.getString("endworkdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return wvo;
	}

	public boolean workRecordUpdate(WorkRecordVO wvo, int workNo) {
		int deptNo = deptToDeptNo(wvo.getDepartment());
		int positionNo = positionToPositionNo(wvo.getPosition());
		query = "UPDATE t_work SET deptno = ?, positionno = ?, STARTWORKDATE = TO_DATE(?, 'yyyy.mm.dd')"
				+ ", ENDWORKDATE = TO_DATE(?, 'yyyy.mm.dd') WHERE workNo = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, deptNo);
			pstmt.setInt(2, positionNo);
			pstmt.setString(3, wvo.getStartWorkingDate());
			pstmt.setString(4, wvo.getEndWorkingDate());
			pstmt.setInt(5, workNo);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}

	public boolean workRecordDelete(int workNo) {
		query = "DELETE t_work WHERE workNo = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, workNo);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}
}