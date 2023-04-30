package hr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.util.DBCon;
import hr.vo.DepartmentVO;

public class DepartmentDAO {

	private Connection con = DBCon.getConnection();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;

	public List<DepartmentVO> departmentList() { // 부서 전체 목록
		query = "SELECT DEPARTMENT " + " FROM t_DEPT ORDER BY DEPTNO DESC "; // 부서 테이블에서 부서명을 검색하는 쿼리문
		List<DepartmentVO> departmentlist = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DepartmentVO dvo = new DepartmentVO(rs.getString("DEPARTMENT"));
				departmentlist.add(dvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return departmentlist;
	}

	public int departmentInfo(DepartmentVO dvo) { // 부서 정보
		query = "select count(e.id) from t_dept d, t_employee e where d.deptno = e.deptno and d.department = ?";
		int result = 0;

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dvo.getDepartmentName());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}

		return result;
	}

	public int departmentDuplication(DepartmentVO dvo) { // 부서 테이블에 중복 값 찾기
		query = "SELECT COUNT(DEPARTMENT) FROM T_DEPT where department = ? GROUP BY DEPARTMENT HAVING COUNT(*) >=1";
		int result = 0;

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dvo.getDepartmentName());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return result;
	}

	public boolean departmentInsert(DepartmentVO dvo) { // 부서 등록
		query = "INSERT INTO T_DEPT(department)" + "VALUES(?)";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dvo.getDepartmentName());

			Boolean rs = pstmt.execute(); // Boolean 쿼리를 실행하고 결과를 받아 성공 여부 확인
			if (rs == true) { // 1행이 삽입되면 데이터 추가 성공

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}

		return false;
	}

	public List<DepartmentVO> departmentSelect(DepartmentVO dvo) { // 부서 상세 보기
		query = "select p.POSITION FROM t_dept d ,t_employee e, t_position p"
				+ " where d.deptno = e.deptno and e.positionno = p.positionno and d.department = ?";
		List<DepartmentVO> departmentSelect = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dvo.getDepartmentName());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DepartmentVO dvo1 = new DepartmentVO(rs.getString("POSITION"));
				departmentSelect.add(dvo1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}

		return departmentSelect;
	}

	public boolean departmentUpdate(String departmentName, String ChangeName) { // 부서 변경
		query = "UPDATE T_DEPT SET DEPARTMENT = ? WHERE DEPARTMENT = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, ChangeName);
			pstmt.setString(2, departmentName);

			Boolean rs = pstmt.execute();
			if (rs == true) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}

		return false;
	}

	public boolean departmentDelete(DepartmentVO dvo) { // 부서 삭제
		query = "delete from t_dept where department = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dvo.getDepartmentName());

			Boolean rs = pstmt.execute();
			if (rs == true) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}
}