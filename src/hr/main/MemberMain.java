package hr.main;

import java.util.*;

import hr.dao.*;
import hr.vo.*;

public class MemberMain {
	private int input;
	private LoginCheck lchk = new LoginCheck();
	public static Scanner scan = new Scanner(System.in);
	public static String myId;
	public static int deptNo;
	private String myPw;
	private EmployeeDAO edao = new EmployeeDAO();
	private EmployeeVO evo = new EmployeeVO();

	public void menu() {
		System.out.println("============ MAIN ============");
		System.out.println(" 1. 로그인     2. 시스템 종료");
		System.out.print("선택 : ");
		try {
		input = scan.nextInt();
		scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			login();
			break;
		case 2:
			System.out.println(">> 시스템을 종료합니다.");
			scan.close();
			System.exit(0);
		default:
			System.out.println(">> 1 ~ 2 중에 하나를 선택해주세요.");
			menu();
			break;
		}
		}catch(InputMismatchException e){
			System.out.println(">> 1 ~ 2 중에 하나를 선택해주세요.");
			scan.next();
			menu();
		}
	}

	public void login() {
		System.out.println("============  로그인  ============");
		System.out.println("아이디와 비밀번호를 입력해주세요.");
		System.out.print(" 아이디 : ");
		myId = scan.nextLine();
		System.out.print(" 비밀번호 : ");
		myPw = scan.nextLine();
		boolean result = lchk.loginChk(myId, myPw); // 로그인 체크 메소드 호출
		if (result == true) { // 로그인 성공 시 - 관리자이면 adminMenu() 호출 그렇지 않으면 memberMenu() 호출
			evo = edao.emInfo(myId);
			deptNo = evo.getDeptNo();
			if (deptNo == 1) {
				adminMenu();
			} else {
				memberMenu();
			}
		} else { // 로그인 실패 시 - "아이디 또는 비밀번호가 일치하지 않습니다." 출력하고 메인 메뉴 표시
			System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
			menu();
		}
	}

	public void logout() {
		System.out.println(">> 로그아웃이 완료되었습니다. -----");
		myId = null;
		myPw = null;
		menu();
	}

	public void memberMenu() {
		System.out.println("===================M E N U (일반사원 ID) ===================");
		System.out.println(" 1.직원     2.부서     3.급여 지급 내역     4.근무기록     5.공지사항     6.로그아웃");
		System.out.print("선택 : ");
		try {
		input = scan.nextInt();
		scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			new EmployeeMain().memberCheck();
			break;
		case 2:
			new DepartmentMain().departmentCheck();
			break;
		case 3:
			new SalaryMain().selectDateSalary();
			break;
		case 4:
			new WorkRecordMain().workRecordCheck();
			break;
		case 5:
			new NoticeMain().noticeCheck();
			break;
		case 6:
			logout();
		default:
			System.out.println(">> 1 ~ 6 사이의 번호를 입력해주세요.");
			memberMenu();
			break;
		}
		}catch (InputMismatchException e) {
			scan.next();
			System.out.println(">> 1 ~ 6 사이의 번호를 입력해주세요.");
			memberMenu();
		}
	}

	public void adminMenu() {
		System.out.println("===================M E N U (관리자 ID) ===================");
		System.out.println(" 1.직원     2.부서     3.급여 지급 내역     4.근무기록     5.공지사항     6.로그아웃");
		System.out.print("선택 : ");
		try {
		input = scan.nextInt();
		scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			new EmployeeMain().adminEmMenu();
			break;
		case 2:
			new DepartmentMain().adminDeMenu();
			break;
		case 3:
			new SalaryMain().adminSalaryMenu();
			break;
		case 4:
			new WorkRecordMain().adminWorkRecordMenu();
			break;
		case 5:
			new NoticeMain().adminNoticeMenu();
			break;
		case 6:
			logout();
		default:
			System.out.println(">> 1 ~ 6 사이의 번호를 입력해주세요.");
			adminMenu();
			break;
		}
		}catch(InputMismatchException e) {
			scan.next();
			System.out.println(">> 1 ~ 6 사이의 번호를 입력해주세요.");
			adminMenu();
		}
	}

	public static void main(String[] args) {
		new MemberMain().menu();
	}
}