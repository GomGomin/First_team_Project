package hr.main;

import java.lang.reflect.Member;
import java.util.InputMismatchException;
import java.util.List;

import hr.dao.EmployeeDAO;
import hr.dao.PositionDAO;
import hr.dao.WorkRecordDAO;
import hr.vo.EmployeeVO;

public class EmployeeMain {

	private EmployeeDAO edao = new EmployeeDAO();
	private EmployeeVO evo = new EmployeeVO();
	private String searchId;

	public void memberCheck() { // 직원조회
		System.out.println("===================직원 이름순 조회 ===================");
		System.out.println(" 1. 재직 중 직원 조회");
		System.out.println(" 2. 퇴사자 포함 조회");
		System.out.println(" 3. 이전으로");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		System.out.println("---------------------------------------------------------------------------------------");
		switch (input) {
		case 1:
			List<EmployeeVO> emNameList = edao.emNameList();
			System.out.println("직원ID | 이름 | 성별 | 이메일 | 부서명 | 직위");
			if (emNameList == null || emNameList.isEmpty()) {
				System.out.println("직원이 없습니다.");
			} else {
				for (EmployeeVO em : emNameList) {
					System.out.println(em.getId() + " | " + em.getName() + " | " + em.getGender() + " | "
							+ em.getEmail() + " | " + edao.deptName(em.getDeptNo()) + " | "
							+ new PositionDAO().positionName(em.getPositionNo()));
				}
			}
			back();
			break;
		case 2:
			List<EmployeeVO> emNameListResign = edao.emNameListResign(); // 퇴사자 포함 조회 - 신규 생성
			System.out.println("직원ID | 이름 | 성별 | 이메일 | 부서명 | 직위");
			if (emNameListResign == null || emNameListResign.isEmpty()) {
				System.out.println("직원이 없습니다.");
			} else {
				for (EmployeeVO em : emNameListResign) {
					System.out.println(em.getId() + " | " + em.getName() + " | " + em.getGender() + " | "
							+ em.getEmail() + " | " + edao.deptName(em.getDeptNo()) + " | "
							+ new PositionDAO().positionName(em.getPositionNo()));
				}
			}
			back();
			break;
		case 3:
			back();
			break;
		default:
			System.out.println(">> 1 ~ 3 중에 하나를 선택해주세요.");
			memberCheck();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 3 중에 하나를 선택해주세요.");
			memberCheck();
		}
	}

	public void back() {
		if (MemberMain.deptNo == 1) {
			adminEmMenu();
		} else {
			new MemberMain().memberMenu();
		}
	}

	public void adminEmMenu() {
		System.out.println("===================직원 메뉴(관리자 ID) ===================");
		System.out.println(" 1.조회     2.등록     3.직원 관리     4.메뉴화면으로");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			memberCheck();
			break;
		case 2:
			adminEmInsert();
			break;
		case 3:
			adminEmManage();
			break;
		case 4:
			new MemberMain().adminMenu();
			break;
		default:
			System.out.println(">> 1 ~ 4 중에 하나를 선택해주세요.");
			adminEmMenu();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 4 중에 하나를 선택해주세요.");
			adminEmMenu();
		}
	}

	public void adminEmInsert() {
		boolean deptNameflag; // 부서 or 직위를 잘못 입력한 경우 체크
		boolean poNameflag;
		System.out.println("=================== 직원 등록 ===================");
		System.out.print(" 이름 : ");
		evo.setName(MemberMain.scan.nextLine());
		System.out.print(" 생년월일(yyyy.MM.dd) : ");
		String birthDate = MemberMain.scan.nextLine();
		if (birthDate.length() != 10 || birthDate.replace(".", "").length() != 8 || birthDate.indexOf(".") != 4
				|| birthDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminEmInsert();
			return;
		}
		if(Integer.parseInt(birthDate.substring(0, 2))>=19) {
			evo.setBirthday(birthDate);
		} else {
			System.out.println("날짜를 확인해주세요.");
			adminEmInsert();
			return;
		}
		System.out.print(" 성별(F/M) : ");
		String gender = MemberMain.scan.nextLine();
		if (gender.length() != 1) {
			System.out.println("성별 입력은 문자 하나만 가능합니다.");
			adminEmInsert();
			return;
		}
		evo.setGender(gender);

		System.out.print(" 이메일 : ");
		evo.setEmail(MemberMain.scan.nextLine());

		System.out.print(" 부서명 : ");
		String deptName = MemberMain.scan.nextLine();
		int deptNum = new WorkRecordDAO().deptToDeptNo(deptName); // 부서명 조회해서 부서 번호 받기
		if (deptNum == 0) {
			deptNameflag = false;
		} else {
			evo.setDeptNo(deptNum);
			deptNameflag = true;
		}

		System.out.print(" 직위 : ");
		String PositionName = MemberMain.scan.nextLine();
		int poNo = new PositionDAO().positionNum(PositionName);
		if (poNo == 0) {
			poNameflag = false;
		} else {
			evo.setPositionNo(poNo); // 직위명 조회해서 직위번호 받기
			poNameflag = true;
		}

		System.out.print(" 입사일(yyyy.MM.dd) : ");
		String startDate = MemberMain.scan.nextLine();
		if (startDate.length() != 10 || startDate.replace(".", "").length() != 8 || startDate.indexOf(".") != 4
				|| startDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminEmInsert();
			return;
		}
		if(Integer.parseInt(birthDate.substring(0, 2))>=19) {
			evo.setJoinDate(startDate);
		} else {
			System.out.println("날짜를 확인해주세요.");
			adminEmInsert();
			return;
		}
		System.out.print(" 연봉(숫자만 입력/단위는 원) : ");
		evo.setSalary(MemberMain.scan.nextInt());
		MemberMain.scan.nextLine();

		System.out.print(" 은행명 : ");
		evo.setBankName(MemberMain.scan.nextLine());

		System.out.print(" 계좌번호 : ");
		evo.setAccountNumber(MemberMain.scan.nextLine());

		System.out.print(" 직원 ID : ");
		evo.setId(MemberMain.scan.nextLine());

		System.out.print(" 비밀번호 : ");
		evo.setPw(MemberMain.scan.nextLine());

		if (deptNameflag == true && poNameflag == true) { // 부서 및 직위가 정상적으로 입력된 경우
			EmployeeVO duplicate = edao.emInfo(evo.getId()); // 중복 여부 확인
			if (duplicate == null) { // 중복이 아닌 경우
				while (true) { // y or n 값을 받을 때까지 반복
					System.out.println("==============================================");
					System.out.println("● 정보를 등록하시겠습니까? Y / N");
					String re = MemberMain.scan.next();
					if (re.equals("y") || re.equals("Y")) {
						boolean result = edao.emInsert(evo);
						if (result) {
							System.out.println("직원 등록이 완료되었습니다.");
						} else {
							System.out.println("직원 등록에 실패하였습니다. 다시 시도해주세요.");
						}
						break;
					} else if (re.equals("n") || re.equals("N")) {
						System.out.println("취소되었습니다.");
						break;
					} else {
						System.out.println("Y와 N 중 하나를 입력해주세요.");
					}
				}
			} else { // 중복인 경우
				System.out.println("이미 존재하는 직원ID입니다.");
			}
		} else {
			System.out.println("부서명 및 직위명을 정확히 입력해주세요.");
		}
		adminEmMenu();
	}

	public void adminEmManage() {
		System.out.println("===================직원 정보 및 관리 ===================");
		System.out.println(" 1.직원 ID검색     2.이전으로 ");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			System.out.println("-------------------------------------------------------");
			System.out.print("● 직원 아이디를 입력해주세요\n");
			searchId = MemberMain.scan.nextLine();
			System.out.println("-------------------------------------------------------");
			EmployeeVO evo2 = edao.emInfo(searchId); // 직원 ID 확인
			if (evo2 == null) {
				System.out.println("ID를 확인해주세요.");
				adminEmManage();
			} else {
				adminEmInfo(evo2);
			}
			break;
		case 2:
			back();
			break;
		default:
			System.out.println("1~2 사이의 번호를 입력해주세요.");
			adminEmManage();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println("1~2 사이의 번호를 입력해주세요.");
			adminEmManage();
		}

	}

	public void adminEmInfo(EmployeeVO evo2) {
		evo2 = edao.emInfo(searchId);
		System.out.println("=================== 직원 정보 ===================");
		System.out.println("이름 : " + evo2.getName());
		System.out.println("생년월일 : " + evo2.getBirthday());
		System.out.println("성별 : " + evo2.getGender());
		System.out.println("부서 : " + edao.deptName(evo2.getDeptNo())); // 부서명 확인 메소드 사용
		System.out.println("직위 : " + new PositionDAO().positionName(evo2.getPositionNo())); // 직위명 확인 메소드 사용
		System.out.println("입사일 : " + evo2.getJoinDate());
		System.out.println("연봉 : " + evo2.getSalary());
		System.out.println("아이디 : " + evo2.getId());
		System.out.println("비밀번호 : " + evo2.getPw());
		System.out.println("재직여부 : " + evo2.getResign());
		System.out.println("--------------------------------------------------");
		System.out.println(" 1.수정     2. 삭제     3. 이전으로 ");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		switch (input) {
		case 1:
			adminEmUpdate(evo2); // 수정 메뉴로 이동
			break;
		case 2:
			adminEmDelete(evo2); // 삭제 메뉴로 이동
			break;
		case 3:
			adminEmManage();
			break;
		default:
			System.out.println("1~3 사이의 번호를 선택해주세요. ");
			adminEmInfo(evo2);
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println("1~3 사이의 번호를 선택해주세요. ");
			adminEmInfo(evo2);
		}
	}

	public void adminEmUpdate(EmployeeVO evo2) {
		System.out.println("======================= 직원 정보 수정 ========================");
		System.out.println("1.상세정보 변경     2.퇴사처리     3.부서 및 직위 변경     4.급여정보 변경     5.이전으로");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			adminEmInfoUpdate(evo2); // 직원 상세정보 변경으로 이동
			break;
		case 2:
			adminEmResign(evo2); // 퇴사처리로 이동
			break;
		case 3:
			adminEmDeUpdate(evo2); // 부서 및 지위 변경으로 이동
			break;
		case 4:
			adminEmSalaryUpdate(evo2); // 급여정보 변경으로 이동
			break;
		case 5:
			adminEmInfo(evo2); // 직원 상세보기로 돌아가기
			break;
		default:
			System.out.println("1~5 사이의 번호를 선택해주세요. ");
			adminEmUpdate(evo2);
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println("1~5 사이의 번호를 선택해주세요. ");
			adminEmUpdate(evo2);
		}
	}

	public void adminEmDelete(EmployeeVO evo2) {
		System.out.println("==================관리자 직원 삭제 화면 ==================");
		System.out.println("직원ID : " + evo2.getId());
		while (true) { // y or n 값을 받을 때까지 반복
			System.out.println("==============================================");
			System.out.println("● 해당 직원을 삭제하시겠습니까? Y / N");
			String re = MemberMain.scan.next();
			if (re.equals("y") || re.equals("Y")) {
				boolean result = edao.emDelete(evo2);
				if (result) {
					System.out.println("직원 정보 삭제에 성공하였습니다.");
				} else {
					System.out.println("직원 정보 삭제에 실패하였습니다. 다시 시도해주세요.");
				}
				break;
			} else if (re.equals("n") || re.equals("N")) {
				System.out.println("취소되었습니다.");
				break;
			} else {
				System.out.println("Y와 N 중 하나를 입력해주세요.");
			}
		}
		adminEmManage();
	}

	public void adminEmInfoUpdate(EmployeeVO evo2) {
		System.out.println("=================== 상세 정보 변경 ===================");
		System.out.println("변경할 정보를 입력해주세요.");
		System.out.print(" 이름 : ");
		evo2.setName(MemberMain.scan.nextLine());

		System.out.print(" 생년월일(yyyy.MM.dd) : ");
		String birthDate = MemberMain.scan.nextLine();
		if (birthDate.length() != 10 || birthDate.replace(".", "").length() != 8 || birthDate.indexOf(".") != 4
				|| birthDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminEmInfoUpdate(evo2);
			return;
		}
		evo2.setBirthday(birthDate);

		System.out.print(" 성별(F/M) : ");
		String gender = MemberMain.scan.nextLine();
		if (gender.length() != 1) {
			System.out.println("성별 입력은 문자 하나만 가능합니다.");
			adminEmInfoUpdate(evo2);
			return;
		}
		evo2.setGender(gender);

		System.out.print(" 이메일 : ");
		evo2.setEmail(MemberMain.scan.nextLine());
		System.out.print(" 비밀번호 : ");
		evo2.setPw(MemberMain.scan.nextLine());

		System.out.print(" 입사일(yyyy.MM.dd) : ");
		String joinDate = MemberMain.scan.nextLine();
		if (joinDate.length() != 10 || joinDate.replace(".", "").length() != 8 || joinDate.indexOf(".") != 4
				|| joinDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminEmInfoUpdate(evo2);
			return;
		}
		evo2.setJoinDate(joinDate);

		System.out.println("======================================================");
		while (true) { // y or n 값을 받을 때까지 반복
			System.out.println("==============================================");
			System.out.println("● 정보를 변경하시겠습니까? Y / N");
			String re = MemberMain.scan.next();
			if (re.equals("y") || re.equals("Y")) {
				boolean result = edao.emInfoUpdate(evo2);
				if (result) {
					System.out.println("상세 정보 변경에 성공하였습니다.");
				} else {
					System.out.println("정보 변경에 실패하였습니다. 다시 시도해주세요.");
				}
				break;
			} else if (re.equals("n") || re.equals("N")) {
				System.out.println("취소되었습니다.");
				break;
			} else {
				System.out.println("Y와 N 중 하나를 입력해주세요.");
			}
		}
		adminEmInfo(evo2);
	}

	public void adminEmResign(EmployeeVO evo2) {
		System.out.println("=================== 퇴사처리 ===================");
		System.out.println("1.퇴사처리     2. 퇴사취소     3.이전으로");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			if (evo2.getResign().equals("N") || evo2.getResign().equals("n")) {
				System.out.println("이미 퇴사 처리된 직원입니다.");
				adminEmResign(evo2);
			} else {
				boolean result = edao.emResign(evo2);
				if (result) {
					System.out.println("퇴사 처리에 성공하였습니다.");
				} else {
					System.out.println("퇴사 처리에 실패하였습니다. 다시 시도해주세요.");
				}
			}
			break;
		case 2:
			if (evo2.getResign().equals("Y") || evo2.getResign().equals("y")) {
				System.out.println("재직 중인 직원입니다.");
				adminEmResign(evo2);
			} else {
				boolean result = edao.emResign(evo2);
				if (result) {
					System.out.println("퇴사 취소에 성공하였습니다.");
				} else {
					System.out.println("퇴사 취소에 실패하였습니다. 다시 시도해주세요.");
				}
			}
			break;
		case 3:
			break;
		default:
			System.out.println("1~3 사이의 번호를 선택해주세요. ");
			adminEmResign(evo2);
			return;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println("1~3 사이의 번호를 선택해주세요. ");
			adminEmResign(evo2);
			}
		adminEmInfo(evo2);
	}

	public void adminEmDeUpdate(EmployeeVO evo2) {
		boolean deptNameflag; // 부서 or 직위를 잘못 입력한 경우 체크
		boolean poNameflag;
		System.out.println("==================부서 및 직위 변경===================");
		System.out.println("변경할 정보를 입력해주세요.");
		System.out.print(" 부서 : ");
		String deptName = MemberMain.scan.nextLine();
		int deptNum = edao.deptNum(deptName);
		if (deptNum == 0) { // 해당 부서명이 부서 테이블에 없는 경우 0을 반환
			deptNameflag = false;
		} else {
			evo2.setDeptNo(deptNum);
			deptNameflag = true;
		}
		System.out.print(" 직위 : ");
		int poNo = new PositionDAO().positionNum(MemberMain.scan.nextLine()); // 받아온 부서명으로 지위 번호를 검색하여 초기화
		if (poNo == 0) { // 해당 직위명이 직위 테이블에 없는 경우 0을 반환
			poNameflag = false;
		} else {
			evo2.setPositionNo(poNo); // 받아온 직위번호를 evo객체에 set
			poNameflag = true;
		}
		System.out.println("======================================================");
		if (deptNameflag == true && poNameflag == true) {
			while (true) { // y or n 값을 받을 때까지 반복
				System.out.println("==============================================");
				System.out.println("● 정보를 변경하시겠습니까? Y / N");
				String re = MemberMain.scan.next();
				if (re.equals("y") || re.equals("Y")) {
					boolean result = edao.emDeUpdate(evo2);
					if (result) {
						System.out.println("부서 및 직위 정보 변경에 성공하였습니다.");
					} else {
						System.out.println("부서 및 직위 정보 변경에 실패하였습니다. 다시 시도해주세요.");
					}
					break;
				} else if (re.equals("n") || re.equals("N")) {
					System.out.println("취소되었습니다.");
					break;
				} else {
					System.out.println("Y와 N 중 하나를 입력해주세요.");
				}
			}
			adminEmInfo(evo2);
		} else {
			System.out.println("부서 및 직위 정보를 정확히 입력해주세요.");
			adminEmDeUpdate(evo2);
		}
	}

	public void adminEmSalaryUpdate(EmployeeVO evo2) {
		System.out.println("====================급여 정보 변경====================");
		System.out.println("변경할 정보를 입력해주세요.");
		System.out.print(" 은행명 : ");
		evo2.setBankName(MemberMain.scan.nextLine());
		System.out.print(" 계좌번호 : ");
		evo2.setAccountNumber(MemberMain.scan.nextLine());
		System.out.print(" 연봉(숫자만 입력/단위는 원) : ");
		evo2.setSalary(MemberMain.scan.nextInt());
		MemberMain.scan.nextLine();
		System.out.println("======================================================");
		while (true) { // y or n 값을 받을 때까지 반복
			System.out.println("==============================================");
			System.out.println("● 정보를 변경하시겠습니까? Y / N");
			String re = MemberMain.scan.next();
			if (re.equals("y") || re.equals("Y")) {
				boolean result = edao.emSalaryUpdate(evo2);
				if (result) {
					System.out.println("급여 정보 정보 변경에 성공하였습니다.");
				} else {
					System.out.println("급여 정보 변경에 실패하였습니다. 다시 시도해주세요.");
				}
				break;
			} else if (re.equals("n") || re.equals("N")) {
				System.out.println("취소되었습니다.");
				break;
			} else {
				System.out.println("Y와 N 중 하나를 입력해주세요.");
			}
		}
		adminEmInfo(evo2);
	}
}