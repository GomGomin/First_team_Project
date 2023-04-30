package hr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.util.DBCon;
import hr.vo.EmployeeVO;

public class EmployeeDAO {

	private Connection con = DBCon.getConnection();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;

	public List<EmployeeVO> emNameListResign() { // 퇴사자 포함 조회
		List<EmployeeVO> emNameList = new ArrayList<>();
		query = "Select * from t_employee e order by name";
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EmployeeVO evo = new EmployeeVO();
				evo.setId(rs.getString("id"));
				evo.setName(rs.getString("name"));
				evo.setGender(rs.getString("gender"));
				evo.setEmail(rs.getString("email"));
				evo.setDeptNo(rs.getInt("deptno"));
				evo.setPositionNo(rs.getInt("positionno"));
				emNameList.add(evo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return emNameList;
	}

	public List<EmployeeVO> emNameList() { // 퇴사자 미포함 조회
		List<EmployeeVO> emNameList = new ArrayList<>();
		query = "Select * from t_employee where resign='Y' order by name";
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EmployeeVO evo = new EmployeeVO();
				evo.setId(rs.getString("id"));
				evo.setName(rs.getString("name"));
				evo.setGender(rs.getString("gender"));
				evo.setEmail(rs.getString("email"));
				evo.setDeptNo(rs.getInt("deptno"));
				evo.setPositionNo(rs.getInt("positionno"));
				emNameList.add(evo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return emNameList;
	}

	public boolean emInsert(EmployeeVO evo) {
		query = "INSERT INTO t_employee (ID,DEPTNO,POSITIONNO,PW,NAME,EMAIL,BIRTHDAY,GENDER,JOINDATE,RESIGN,SALARY,ACCOUNTNUMBER,BANKNAME)"
				+ "VALUES(?,?,?,?,?,?,to_date(?,'yyyy.mm.dd'),?, to_date(?,'yyyy.mm.dd'), 'Y', ?,?,?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, evo.getId());
			pstmt.setInt(2, evo.getDeptNo());
			pstmt.setInt(3, evo.getPositionNo());
			pstmt.setString(4, evo.getPw());
			pstmt.setString(5, evo.getName());
			pstmt.setString(6, evo.getEmail());
			pstmt.setString(7, evo.getBirthday());
			pstmt.setString(8, evo.getGender());
			pstmt.setString(9, evo.getJoinDate());
			pstmt.setInt(10, evo.getSalary());
			pstmt.setString(11, evo.getAccountNumber());
			pstmt.setString(12, evo.getBankName());
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

	public EmployeeVO emInfo(String id) {
		query = "Select id, deptno, positionno, pw, name, email, to_char(birthday, 'yyyy.mm.dd') birthday, gender, to_char(joindate, 'yyyy.mm.dd') joindate"
				+ " , to_char(resigndate, 'yyyy.mm.dd') resigndate, resign, salary, accountnumber, bankname"
				+ " from t_employee where id = ?";
		EmployeeVO evo = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				evo = new EmployeeVO();
				evo.setId(rs.getString("id"));
				evo.setDeptNo(rs.getInt("deptno"));
				evo.setPositionNo(rs.getInt("positionno"));
				evo.setName(rs.getString("name"));
				evo.setEmail(rs.getString("email"));
				evo.setBirthday(rs.getString("birthday"));
				evo.setGender(rs.getString("gender"));
				evo.setJoinDate(rs.getString("joindate"));
				evo.setResignDate(rs.getString("resigndate"));
				evo.setResign(rs.getString("resign"));
				evo.setSalary(rs.getInt("salary"));
				evo.setAccountNumber(rs.getString("accountnumber"));
				evo.setBankName(rs.getString("bankname"));
				evo.setPw(rs.getString("pw"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return evo;
	}

	public boolean emDelete(EmployeeVO evo) {
		query = "delete from t_employee where id = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, evo.getId()); // ? 자리수
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

	public boolean emInfoUpdate(EmployeeVO evo) {
		query = "UPDATE t_employee SET name= ?, birthday = to_date(?,'yyyy.mm.dd'), GENDER = ?, EMAIL=?, PW=?, JOINDATE = to_date(?,'yyyy.mm.dd') where id = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, evo.getName());
			pstmt.setString(2, evo.getBirthday());
			pstmt.setString(3, evo.getGender());
			pstmt.setString(4, evo.getEmail());
			pstmt.setString(5, evo.getPw());
			pstmt.setString(6, evo.getJoinDate());
			pstmt.setString(7, evo.getId());
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

	public boolean emResign(EmployeeVO evo) {
		query = "update t_employee set resign = ? where id = ?";
		try {
			pstmt = con.prepareStatement(query);
			System.out.println(evo.getResign());
			System.out.println(evo.getResign().equals("Y"));
			System.out.println(evo.getResign().equals("y"));
			if (evo.getResign().equals("Y") || evo.getResign().equals("y")) {
				pstmt.setString(1, "N");
			} else {
				pstmt.setString(1, "Y");
			}
			pstmt.setString(2, evo.getId());
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

	public boolean emDeUpdate(EmployeeVO evo) {
		query = "update t_employee set DEPTNO=?, POSITIONNO=? where id = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, evo.getDeptNo());
			pstmt.setInt(2, evo.getPositionNo());
			pstmt.setString(3, evo.getId());
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

	public boolean emSalaryUpdate(EmployeeVO evo) {
		query = "update t_employee set BANKNAME=?, ACCOUNTNUMBER=?, SALARY=? where id = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, evo.getBankName());
			pstmt.setString(2, evo.getAccountNumber());
			pstmt.setInt(3, evo.getSalary());
			pstmt.setString(4, evo.getId());
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

	public String deptName(int deptNo) {
		query = "Select department from t_dept where DEPTNO = ?";
		String name = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, deptNo);
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

	public int deptNum(String deptName) {
		query = "Select deptno from T_DEPT where department = ?";
		int num = 0;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, deptName.trim());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			// 직위명을 잘못 입력한 경우 오류메세지 출력
			System.out.print("부서명을 잘못 입력하였습니다.\n");
		} finally {
			DBCon.close(rs, pstmt);
		}

		return num;
	}

}
