package hr.main;

import java.util.InputMismatchException;
import java.util.List;

import hr.dao.SalaryDAO;
import hr.vo.SalaryVO;

public class SalaryMain {

	private SalaryDAO sdao = new SalaryDAO();
	private String inputYear;
	private String inputMonth;
	private String searchId;
	private int input;

	public void selectDateSalary() {// 급여지급날짜선택
		
		List<SalaryVO> salarylist = sdao.salaryInfo(MemberMain.myId); // SalaryDAO 클래스의 selelctAll() 메소드를 호출하여
		// 전체 설문 목록을 넘겨 받는다
		if (salarylist == null || salarylist.size() < 1) { // 만약에 급여 지급 내역이 없다면
			System.out.println("급여 지급 내역이 없습니다.");
			new MemberMain().memberMenu();
		} else { // 그렇지 않다면
			System.out.println();
			System.out.println(" 지급일 | 직원ID | 지급액 | 등록자ID");
			for (SalaryVO svo : salarylist) {
				System.out.println(svo.getPaymentDate() + " | " + svo.getId() + " | " + svo.getPayment() + " | " + svo.getInsertId());
			}
		}
		System.out.println("========== 조회할 연도와 월을 입력해주세요==========");
		System.out.print("   연도 : ");
		inputYear = MemberMain.scan.nextLine();
		System.out.print("   월 : ");
		inputMonth = MemberMain.scan.nextLine();
		
		SalaryVO svo2 = new SalaryVO();
		for (SalaryVO svo : salarylist) { // 년도 월 체크
			if (svo.getPaymentDate().substring(0, 4).equals(inputYear)
					&& svo.getPaymentDate().substring(5, 7).equals(inputMonth)) {
				svo2 = svo;
				mySalaryInfo(svo2);
				return;
			}
		}
		System.out.println("   일치하는 값이 없습니다.");
		System.out.println();// 메인 메뉴 표시
		new MemberMain().memberMenu();
	}

	public void mySalaryInfo(SalaryVO svo2) {// 내급여지급상세보기

//		List<SalaryVO> salarylist = sdao.salaryInfo(MemberMain.myId);
//		System.out.println();
//		for (SalaryVO svo : salarylist) {
//			if (svo.getPaymentDate().substring(0, 4).equals(inputYear)
//					&& svo.getPaymentDate().substring(5, 7).equals(inputMonth)) {
				System.out.println("아이디 : " + svo2.getId());
				System.out.println("계좌번호 : " + svo2.getAccountNumber());
				System.out.println("은행 : " + svo2.getBankName());
				System.out.println("지급액 : " + svo2.getPayment());
				System.out.println("지급일 : " + svo2.getPaymentDate());
//			}
//		}
		System.out.println();
		new MemberMain().memberMenu();
	}

	public void adminSalaryMenu() {// 관리자급여지급내역메뉴
		System.out.println("=============관리자 급여 지급 내역 메뉴=============");
		System.out.println(" 1.관리자 지급 내역 조회");
		System.out.println(" 2.관리자 급여 지급 내역 등록");
		System.out.println(" 3.관리자 급여 지급 내역 관리");
		System.out.println(" 4.이전으로");
		System.out.print("선택 : ");
		try {
		input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			new SalaryMain().adminSalaryCheck();
			break;
		case 2:
			new SalaryMain().adminSalaryInsert();
			break;
		case 3:
			new SalaryMain().adminSalaryManage();
			break;
		case 4:
			new MemberMain().adminMenu();
			break;
		default:
			System.out.println(">> 1 ~ 4 사이의 번호를 입력해주세요.");
			adminSalaryMenu();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 4 사이의 번호를 입력해주세요.");
			adminSalaryMenu();
		}
	}

	public void adminSalaryCheck() {// 관리자 급여 지급 내역 조회화면
		System.out.println("=============관리자 급여 지급 내역 조회=============");
		System.out.println(" 1.관리자 급여 지급 내역 전체 조회(날짜순)");
		System.out.println(" 2.관리자 급여 지급 내열 연도,월별 조회");
		System.out.println(" 3.이전으로");
		System.out.print("선택 : ");
		try {
		input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			new SalaryMain().adminSalaryList();
			break;
		case 2:
			new SalaryMain().adminSalaryDateCheck();
			break;
		case 3:
			new SalaryMain().adminSalaryMenu(); // 이전으로
			break;

		default:
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminSalaryCheck();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminSalaryCheck();
		}
	}

	public void adminSalaryList() {// 관리자 급여지급내역전체조회(날짜순)
		System.out.println("=========관리자 급여 지급 내역 전체 조회 화면 (날짜순)=========");
		List<SalaryVO> salarylist = sdao.salarySelectAll(); // SalaryDAO 클래스의 selelctAll() 메소드를 호출하여
		// 전체 설문 목록을 넘겨 받는다
		if (salarylist == null || salarylist.size() < 1) { // 만약에 급여 지급 내역이 없다면
			System.out.println("급여 지급 내역이 없습니다.");
			adminSalaryMenu();
		} else { // 그렇지 않다면
			System.out.println();
			System.out.println(" 지급일 | 직원ID | 은행명 | 지급계좌 | 지급액 | 등록자ID");
			for (SalaryVO svo : salarylist) {
				System.out.println(svo.getPaymentDate() + " | " + svo.getId() + " | " + svo.getBankName() + " | "
						+ svo.getAccountNumber() + " | " + svo.getPayment() + " | " + svo.getInsertId());
			}
			System.out.println();
			adminSalaryCheck();
		}
	}

	public void adminSalaryDateCheck() {// 관리자급여지급내역 연도,월별조회
		System.out.println("=========관리자 급여 지급 내역 연도, 월별 조회 화면=========");
		System.out.print("   연도 : ");
		inputYear = MemberMain.scan.nextLine();
		System.out.print("   월 : ");
		inputMonth = MemberMain.scan.nextLine();
		List<SalaryVO> salarylist = sdao.salarySelectDate(); // SalaryDAO 클래스의 selelctAll() 메소드를

		System.out.println();
		boolean tf = false;

		System.out.println(" 지급일 | 직원ID | 은행명 | 지급계좌 | 지급액 | 등록자ID");
		for (SalaryVO svo : salarylist) {
			if (svo.getPaymentDate().substring(0, 4).equals(inputYear)
					&& svo.getPaymentDate().substring(5, 7).equals(inputMonth)) {

				System.out.println(svo.getPaymentDate() + " | " + svo.getId() + " | " + svo.getBankName() + " | "
						+ svo.getAccountNumber() + " | " + svo.getPayment() + " | " + svo.getInsertId());
				tf = true;
			}
		}
		if (tf == false) { // 만약에 급여 지급 내역이 없다면
			System.out.println("급여 지급 내역이 없습니다.");
		}
		adminSalaryCheck();
	}

	public void adminSalaryInsert() {// 관리자 급여 지급 내역 등록화면
		System.out.println();
		SalaryVO svo = new SalaryVO(); // 설문 등록에 필요한 데이터들을 입력받아서 SurveyVO 객체의 각 필드에 저장하고
		System.out.println("==========관리자 급여 지급 내역 등록 화면========");
		System.out.print("   직원ID : ");
		String id = MemberMain.scan.nextLine();
		svo.setId(id);

		System.out.print("   은행명 : ");
		String bankName = MemberMain.scan.nextLine();
		svo.setBankName(bankName);

		System.out.print("   지급계좌 : ");
		String accountNumber = MemberMain.scan.nextLine();
		svo.setAccountNumber(accountNumber);

		System.out.print("   지급액 : ");
		int payment = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		svo.setPayment(payment);

		System.out.print("   지급일(yyyy.MM.dd) : ");
		String paymentDate = MemberMain.scan.nextLine();
		if (paymentDate.length() != 10 || paymentDate.replace(".", "").length() != 8 || paymentDate.indexOf(".") != 4
				|| paymentDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminSalaryInsert();
			return;
		}
		if(Integer.parseInt(paymentDate.substring(0, 2))>=19) {
			svo.setPaymentDate(paymentDate);
		} else {
			System.out.println("날짜를 확인해주세요.");
			adminSalaryInsert();
			return;
		}
		svo.setInsertId(MemberMain.myId);
		while(true) {
		System.out.println("● 해당 급여 지급 정보를 등록하시겠습니까? y / n");
		System.out.print("선택 : ");
		String yesNo = MemberMain.scan.nextLine();

		if (yesNo.equalsIgnoreCase("y")) { //
			boolean result = sdao.salaryInsert(svo);
			if (result == true) { // 결과를 넘겨받아 설문 등록에 성공한 경우
				System.out.println(" 급여 지급 내역 등록되었습니다.");
				break;
			}
		} else if (yesNo.equalsIgnoreCase("n")) {
			System.out.println("취소되었습니다.");
			break;
		} else {
			System.out.println("y 또는 n를 입력해주세요.");
		}
		}
		adminSalaryMenu();
	}

	public void adminSalaryManage() {// 관리자 급여지급 내역 관리화면
		System.out.println("=========급여 지급내역 관리=========");
		System.out.print("직원 ID : ");
		searchId = MemberMain.scan.nextLine();
		List<SalaryVO> salarylist = sdao.selectSalary(searchId);
		// 이후로는 직원은 존재하는 것이기 때문에 salarylist 값으로 판단
		if (sdao.selectSalary(searchId).size() > 0) {
			System.out.println(" 지급일 | 직원ID | 은행명 | 지급계좌 | 지급액 | 등록자ID");

			for (SalaryVO svo : salarylist) {
				System.out.println(svo.getPaymentDate() + " | " + svo.getId() + " | " + svo.getBankName() + " | "
						+ svo.getAccountNumber() + " | " + svo.getPayment() + " | " + svo.getInsertId());
			}
		} else {
			System.out.println("해당 직원 ID에 대한 근무기록이 없습니다.");
			adminSalaryMenu();
			return;
		}
		System.out.println("----------------------------------");
		System.out.print("   지급연도 : ");
		inputYear = MemberMain.scan.nextLine();
		System.out.print("   지급월 : ");
		inputMonth = MemberMain.scan.nextLine();
		SalaryVO svo2 = new SalaryVO();
		for (SalaryVO svo : salarylist) { // 년도 월 체크
			if (svo.getPaymentDate().substring(0, 4).equals(inputYear)
					&& svo.getPaymentDate().substring(5, 7).equals(inputMonth)) {
				svo2 = svo;
				adminSalaryInfo(svo2);
				return;
			}
		}
		System.out.println("   일치하는 값이 없습니다.");
		System.out.println();// 메인 메뉴 표시
		adminSalaryMenu();
	}

	public void adminSalaryInfo(SalaryVO svo2) {// 관리자 급여지급내역 상세보기 화면
//		List<SalaryVO> salarylist = sdao.salaryInfo(searchId);
//		for (SalaryVO svo : salarylist) {
//			if (svo.getPaymentDate().substring(0, 4).equals(inputYear)
//					&& svo.getPaymentDate().substring(5, 7).equals(inputMonth)) {

		System.out.println("직원 ID : " + svo2.getId());
		System.out.println("은행 : " + svo2.getBankName());
		System.out.println("지급계좌: " + svo2.getAccountNumber());
		System.out.println("지급액 : " + svo2.getPayment());
		System.out.println("지급일 : " + svo2.getPaymentDate());
		System.out.println("등록자ID : " + svo2.getInsertId());
//			}
//		}

		System.out.println();
		System.out.println("----------------------------------");
		System.out.print(" 1.수정");
		System.out.print(" 2.삭제");
		System.out.println(" 3.이전으로");
		System.out.print("선택 : ");
		try {
		input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			new SalaryMain().adminSalaryUpdate(svo2);
			break;
		case 2:
			new SalaryMain().adminSalaryDelete(svo2);
			break;
		case 3:
			new SalaryMain().adminSalaryMenu(); // 이전으로
			break;
		default:
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminSalaryInfo(svo2);
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminSalaryInfo(svo2);
		}
	}

	public void adminSalaryUpdate(SalaryVO svo2) {// 관리자 급여지급내역 수정화면
		System.out.println("==============급여지급 내역수정=============");
		System.out.print(" 은행 : ");
		svo2.setBankName(MemberMain.scan.nextLine());
		System.out.print(" 지급계좌 : ");
		svo2.setAccountNumber(MemberMain.scan.nextLine());
		System.out.print(" 지급액 : ");
		svo2.setPayment(MemberMain.scan.nextInt());
		MemberMain.scan.nextLine();
		System.out.print(" 지급일 : ");
		String newDate = MemberMain.scan.nextLine();
		
		if (newDate.length() != 10 || newDate.replace(".", "").length() != 8 || newDate.indexOf(".") != 4
				|| newDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminSalaryUpdate(svo2);
			return;
		}
		while(true) {
		System.out.println("● 해당 급여 지급 정보를 수정하시겠습니까? y / n");
		System.out.print("선택 : ");
		String yesNo = MemberMain.scan.nextLine();
		if (yesNo.equalsIgnoreCase("y")) { //
			boolean result = sdao.salaryUpdate(svo2, newDate);
			if (result == true) { // 결과를 넘겨받아 설문 등록에 성공한 경우
				System.out.println(" 급여 지급 내역 수정되었습니다.");
				svo2.setPaymentDate(newDate);
				adminSalaryInfo(svo2);
				break;
			} else {
				System.out.println(" 급여 지급 내역 수정이 실패하였습니다.\n 다시 시도해주세요.");
				adminSalaryUpdate(svo2);
				break;
			}
		} else if (yesNo.equalsIgnoreCase("n")) {
			adminSalaryManage();
			break;
		} else {
			System.out.println(" y/n 중에 하나를 입력해주세요.");
		}
		}
	}

	public void adminSalaryDelete(SalaryVO svo2) {// 관리자 급여지급 내역 삭제화면
		
		System.out.println("===========급여지급내역 삭제===========");
		while(true){
		System.out.println("● 해당 급여 지급 정보를 삭제하시겠습니까? Y / N");
		String yesNo = MemberMain.scan.nextLine();
		if (yesNo.equalsIgnoreCase("y")) {
			boolean result = sdao.salaryDelete(svo2);
			if (result) {
				System.out.println("삭제되었습니다.");
				break;
			} else {
				System.out.println("삭제에 실패했습니다.");
				break;
			}
		} else if (yesNo.equalsIgnoreCase("n")) {
			adminSalaryManage();
			break;
		} else {
			System.out.println("Y 또는 N을 입력해주세요.");
			}
		}
		adminSalaryManage();
	}
}
