package hr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.util.DBCon;
import hr.vo.SalaryVO;

public class SalaryDAO {

	private Connection con = DBCon.getConnection();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;

	public List<SalaryVO> salaryInfo(String id) { // 급여지급상세보기
		query = "SELECT id, accountnumber, bankname, payment, TO_CHAR(paymentdate, 'YYYY.MM.DD') paymentdate, insertid"
				+ " FROM t_salary  WHERE id=?";
		List<SalaryVO> salarylist = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				SalaryVO svo = new SalaryVO(rs.getString("id"), rs.getString("accountNumber"), rs.getString("bankName"),
						rs.getInt("payment"), rs.getString("paymentDate"), rs.getString("insertid"));
				salarylist.add(svo);
			} // 실행 결과가 있으면 로그인 성공
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return salarylist;
	}

	public List<SalaryVO> salarySelectAll() {// 관리자 급여지급내역전체조회(날짜순)
		query = "SELECT id, accountnumber, bankname, payment, TO_CHAR(paymentdate, 'YYYY.MM.DD') paymentdate, insertid "
				+ "FROM t_salary ORDER BY paymentDate DESC ";

		List<SalaryVO> salarylist = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SalaryVO svo = new SalaryVO(rs.getString("id"), rs.getString("accountNumber"), rs.getString("bankName"),
						rs.getInt("payment"), rs.getString("paymentDate"), rs.getString("insertId"));
				salarylist.add(svo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}

		return salarylist;
	}

	public List<SalaryVO> salarySelectDate() {// 관리자급여지급내역 연도,월별조회
		query = "SELECT id, accountnumber, bankname, payment, TO_CHAR(paymentdate, 'YYYY.MM.DD') paymentdate, insertid "
				+ "FROM t_salary ORDER BY paymentDate DESC ";

		List<SalaryVO> salarylist = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SalaryVO svo = new SalaryVO(rs.getString("id"), rs.getString("accountNumber"), rs.getString("bankName"),
						rs.getInt("payment"), rs.getString("paymentDate"), rs.getString("insertId"));
				salarylist.add(svo);
			} // 실행 결과가 있으면 로그인 성공
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return salarylist;
	}

	public boolean salaryInsert(SalaryVO svo) {// 관리자 급여 지급 내역 등록
		query = "INSERT INTO t_salary(id, accountnumber, bankname, payment, paymentdate, insertid) "
				+ "VALUES(?, ?, ?, ?, TO_DATE(?, 'yyyy.mm.dd'), ?)";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, svo.getId());
			pstmt.setString(2, svo.getAccountNumber());
			pstmt.setString(3, svo.getBankName());
			pstmt.setInt(4, svo.getPayment());
			pstmt.setString(5, svo.getPaymentDate());
			pstmt.setString(6, svo.getInsertId());

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

	public List<SalaryVO> selectSalary(String serachId) {// 관리자 급여지급 내역 관리화면
		query = "SELECT id, accountnumber, bankname, payment, TO_CHAR(paymentdate, 'YYYY.MM.DD') paymentdate, insertid "
				+ "FROM t_salary WHERE id = ? ORDER BY paymentDate DESC ";
		List<SalaryVO> salarylist = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, serachId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SalaryVO svo = new SalaryVO();

				svo.setId(rs.getString("id"));
				svo.setAccountNumber(rs.getString("accountNumber"));
				svo.setBankName(rs.getString("bankName"));
				svo.setPayment(rs.getInt("payment"));
				svo.setPaymentDate(rs.getString("paymentDate"));
				svo.setInsertId(rs.getString("insertid"));
				salarylist.add(svo); // salarylist에 저장
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return salarylist; // salarylist 객체를 반환
	}

	public boolean salaryUpdate(SalaryVO svo, String newDate) {// 관리자 급여지급내역 수정화면
		query = "UPDATE t_salary SET BANKNAME=?, ACCOUNTNUMBER=?, PAYMENT=?, PAYMENTDATE=TO_DATE(?, 'yyyy.mm.dd') "
				+ "WHERE ID=? AND PAYMENTDATE = TO_DATE(?, 'yyyy.mm.dd')";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, svo.getBankName());
			pstmt.setString(2, svo.getAccountNumber());
			pstmt.setInt(3, svo.getPayment());
			pstmt.setString(4, newDate);
			pstmt.setString(5, svo.getId());
			pstmt.setString(6, svo.getPaymentDate());

			int result = pstmt.executeUpdate(); // update 쿼리를 실행하고 결과를 받아 성공 여부 확인
			System.out.println(result);
			if (result == 1) { // 1행이 변경되면 설문 변경 성공
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}

	public boolean salaryDelete(SalaryVO svo) {// 관리자 급여지급 내역 삭제화면
		query = "DELETE FROM t_salary WHERE id=? AND PAYMENTDATE = TO_DATE(?, 'yyyy.mm.dd')";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, svo.getId());
			pstmt.setString(2, svo.getPaymentDate());

			int result = pstmt.executeUpdate(); // delete 쿼리를 실행하고 결과를 받아 성공 여부 확인
			if (result > 0) { // 1행이 변경되면 설문 삭제 성공
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