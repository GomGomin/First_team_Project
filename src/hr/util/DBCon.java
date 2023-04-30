package hr.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class DBCon { // 연결객체는 리소스를 많이 사용하기 때문에 싱글톤 패턴으로 작성
	private static Connection con;

	private DBCon() { // 생성자 외부 클래스 접근 불가 처리
	}

	public static Connection getConnection() { // Connection 객체를 반환하는 공유 메소드 getConnection
		FileInputStream fis;
		try {
			fis = new FileInputStream("src\\hr\\util\\jdbc.properties");
			Properties prop = new Properties(); // jdbc.properties 파일을 읽어서
			prop.load(fis); // Properties 클래스의 load 메소드를 이용해서 jdbc.properties 파일 로딩

			if (con == null) {
				// 드라이버 로딩
				Class.forName(prop.getProperty("driver")); // driver 로딩
				System.out.println("driver OK");
				con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
						prop.getProperty("password")); // 데이터베이스 연결 시에는 예외 처리 필수
				System.out.println("con OK");
			} // 드라이버 로딩 및 DB 연결 객체 생성
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con; // 드라이버 로딩 및 DB 연결 객체 생성
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(ResultSet rs, PreparedStatement pstmt) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
