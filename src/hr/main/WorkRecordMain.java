package hr.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;

import hr.dao.EmployeeDAO;
import hr.dao.WorkRecordDAO;
import hr.vo.EmployeeVO;
import hr.vo.WorkRecordVO;

public class WorkRecordMain {

	private WorkRecordDAO wdao = new WorkRecordDAO();
	private WorkRecordVO wvo = new WorkRecordVO();
	private EmployeeVO evo = new EmployeeVO();
	private EmployeeDAO edao = new EmployeeDAO();
	private String deName;
	private String searchId;
	private int workNo;
	private int input;
	String yn;

	public void workRecordCheck() {
		System.out.println("============ 근무기록 조회 메뉴 ============");
		System.out.println();
		System.out.println(" 1. 직원별 조회     2. 부서별 조회     3. 이전으로");
		System.out.println();
		System.out.print("선택 : ");
		try {
		input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		System.out.println();
		switch (input) {
		case 1:
			workRecordEmCheck();
			break;
		case 2:
			workRecordDeCheck();
			break;
		case 3:
			if (MemberMain.deptNo == 1) {
				adminWorkRecordMenu();
			} else {
				new MemberMain().memberMenu();
			}
			break;
		default:
			System.out.println(">> 1 ~ 3 중에 하나를 선택해주세요.");
			workRecordCheck();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 3 중에 하나를 선택해주세요.");
			workRecordCheck();
		}
	}

	public void workRecordEmCheck() {
		System.out.println("============ 근무기록 직원별 조회 ==============");
		System.out.println();
		System.out.print("> 직원ID : ");
		searchId = MemberMain.scan.nextLine();
		System.out.println();
		
		evo = edao.emInfo(searchId);
		if (evo == null) {
			System.out.println("존재하지 않는 직원ID입니다.");
			workRecordCheck();
			return;
		}
		
		if (wdao.workRecordSelectName(evo.getName()).size() > 0) {
			System.out.println("직원ID | 이름 | 부서 | 직위 | 근무시작일 | 근무종료일");

			for (WorkRecordVO wrvo : wdao.workRecordSelectName(evo.getName())) {
				System.out.println(wrvo.getId() + " | " + evo.getName() + " | " + wrvo.getDepartment() + " | "
						+ wrvo.getPosition() + " | " + wrvo.getStartWorkingDate() + " | " + wrvo.getEndWorkingDate());
			}
		} else {
			System.out.println(">> 근무기록이 없습니다.");
		}
		workRecordCheck();
	}

	public void workRecordDeCheck() {
		System.out.println("============ 근무기록 부서별 조회 ==============");
		System.out.println();
		System.out.print("> 부서명 : ");
		deName = MemberMain.scan.nextLine();
		System.out.println();

		if (wdao.deptToDeptNo(deName) == 0) {
			System.out.println("존재하지 않는 부서입니다.");
			workRecordCheck();
			return;
		}

		if (wdao.workRecordSelectDe(deName).size() > 0) {
			System.out.println("직원ID | 이름 | 부서 | 직위 | 근무시작일 | 근무종료일");

			for (WorkRecordVO wrvo : wdao.workRecordSelectDe(deName)) {
				evo = edao.emInfo(wrvo.getId());
				System.out.println(wrvo.getId() + " | " + evo.getName() + " | " + deName + " | " + wrvo.getPosition()
						+ " | " + wrvo.getStartWorkingDate() + " | " + wrvo.getEndWorkingDate());
			}
		} else {
			System.out.println(">> 근무기록이 없습니다.");
		}
		workRecordCheck();
	}

	public void adminWorkRecordMenu() {
		System.out.println("============ 근무기록 메뉴 ============");
		System.out.println(" 1. 근무기록 조회     2. 근무기록 등록     3. 근무기록 관리     4. 이전으로");
		System.out.print("선택 : ");
		try {
		input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			workRecordCheck();
			break;
		case 2:
			adminWorkRecordInsert();
			break;
		case 3:
			adminWorkRecordManage();
			break;
		case 4:
			new MemberMain().adminMenu();
			break;
		default:
			System.out.println(">> 1 ~ 4 중에 하나를 선택해주세요.");
			adminWorkRecordMenu();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 4 중에 하나를 선택해주세요.");
			adminWorkRecordMenu();
		}
	}

	public void adminWorkRecordInsert() {
		System.out.println("============ 근무기록 등록 =============");
		System.out.print("직원 ID : ");
		String insertId = MemberMain.scan.nextLine();
		evo = edao.emInfo(insertId);
		if (evo == null) {
			System.out.println("존재하지 않는 직원ID입니다.");
			adminWorkRecordMenu();
			return;
		}
		wvo.setId(insertId);

		System.out.print("부서 : ");
		String insertDept = MemberMain.scan.nextLine();
		if (wdao.deptToDeptNo(insertDept) == 0) {
			System.out.println("존재하지 않는 부서입니다.");
			adminWorkRecordMenu();
			return;
		}
		wvo.setDepartment(insertDept);

		System.out.print("직위 : ");
		String insertPosition = MemberMain.scan.nextLine();
		if (wdao.positionToPositionNo(insertPosition) == 0) {
			System.out.println("존재하지 않는 직위입니다.");
			adminWorkRecordMenu();
			return;
		}
		wvo.setPosition(insertPosition);

		System.out.print("근무시작일(yyyy.MM.dd) : ");
		String startWorkDate = MemberMain.scan.nextLine();

		if (startWorkDate.length() != 10 || startWorkDate.replace(".", "").length() != 8
				|| startWorkDate.indexOf(".") != 4 || startWorkDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminWorkRecordInsert();
			return;
		}
		if(Integer.parseInt(startWorkDate.substring(0, 2))>=19) {
			wvo.setStartWorkingDate(startWorkDate);
		} else {
			System.out.println("날짜를 확인해주세요.");
			adminWorkRecordInsert();
			return;
		}
		System.out.println("========================================");
		while(true) {
		System.out.println("● 해당 근무기록 정보를 등록하시겠습니까? Y / N");
		yn = MemberMain.scan.nextLine();
		if (yn.equalsIgnoreCase("y")) {
			System.out.println();
			if (wdao.workRecordInsert(wvo)) {
				System.out.println("등록이 완료되었습니다.");
				break;
			} else {
				System.out.println("등록에 실패하였습니다.");
				break;
			}
		} else if (yn.equalsIgnoreCase("n")) {
			System.out.println("취소되었습니다.");
			adminWorkRecordMenu();
			break;
		} else {
			System.out.println("Y 또는 N을 입력해주세요.");
		}
		}
		adminWorkRecordMenu();
	}

	public void adminWorkRecordManage() {
		System.out.println("============ 근무기록 관리 =============");

		System.out.print("직원 ID : ");
		searchId = MemberMain.scan.nextLine();
		evo = edao.emInfo(searchId);
		if (evo == null) {
			System.out.println("존재하지 않는 직원ID입니다.");
			adminWorkRecordMenu();
			return;
		}

		if (wdao.workRecordSelectid(searchId).size() > 0) {
			System.out.println("근무기록번호 | 직원ID | 이름 | 부서 | 직위 | 근무시작일 | 근무종료일");

			for (WorkRecordVO wvo : wdao.workRecordSelectid(searchId)) {
				evo = edao.emInfo(searchId);
				System.out.println(wvo.getWorkRecordNo() + " | " + searchId + " | " + evo.getName() + " | "
						+ wvo.getDepartment() + " | " + wvo.getPosition() + " | " + wvo.getStartWorkingDate() + " | "
						+ wvo.getEndWorkingDate());
			}
			System.out.println("--------------------------------------------------------------");
		} else {
			System.out.println("해당 직원 ID에 대한 근무기록이 없습니다.");
			adminWorkRecordMenu();
			return;
		}
		System.out.println("수정 또는 삭제하실 근무기록번호를 입력해주세요.");
		System.out.print("근무기록번호 : ");

		workNo = MemberMain.scan.nextInt(); // 필드에서 선언한 근무기록번호 workNo
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		System.out.println();
		wvo = wdao.workRecordSelectNum(workNo);
		if (wvo == null) {
			System.out.println("일치하는 정보가 없습니다.");
			adminWorkRecordMenu();
		} else {
			adminWorkInfo();
		}
	}

	public void adminWorkInfo() {
		System.out.println("============ 근무기록 상세보기 =============");
		System.out.println("직원 ID : " + wvo.getId());
		evo = edao.emInfo(wvo.getId());
		System.out.println("이름 : " + evo.getName());
		System.out.println("부서 : " + wvo.getDepartment());
		System.out.println("직위 : " + wvo.getPosition());
		System.out.println("근무시작일 : " + wvo.getStartWorkingDate());
		System.out.println("근무종료일 : " + wvo.getEndWorkingDate());
		System.out.println("---------------------------------------------");
		System.out.println("1.수정   2.삭제   3. 이전으로");
		System.out.print("선택 : ");
		try {
		input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine(); // enter값 건너뛰기
		switch (input) {
		case 1:
			adminWorkRecordUpdate();
			break;
		case 2:
			adminWorkDelete();
			break;
		case 3:
			adminWorkRecordMenu();
			break;
		default:
			System.out.println("==============================================");
			System.out.println(">> 1 ~ 3 중에 하나를 선택해주세요.");
			adminWorkInfo();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println("==============================================");
			System.out.println(">> 1 ~ 3 중에 하나를 선택해주세요.");
			adminWorkInfo();
		}
	}

	public void adminWorkRecordUpdate() {
		System.out.println("============ 근무기록 수정 =============");

		System.out.print("부서 : ");
		String updateDept = MemberMain.scan.nextLine();
		if (wdao.deptToDeptNo(updateDept) == 0) {
			System.out.println("존재하지 않는 부서입니다.");
			adminWorkInfo();
			return;
		}
		wvo.setDepartment(updateDept);

		System.out.print("직위 : ");
		String updatePosition = MemberMain.scan.nextLine();
		if (wdao.positionToPositionNo(updatePosition) == 0) {
			System.out.println("존재하지 않는 직위입니다.");
			adminWorkInfo();
			return;
		}
		wvo.setPosition(updatePosition);

		System.out.print("근무시작일(yyyy.MM.dd) : ");
		String startWorkDate = MemberMain.scan.nextLine();

		if (startWorkDate.length() != 10 || startWorkDate.replace(".", "").length() != 8
				|| startWorkDate.indexOf(".") != 4 || startWorkDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminWorkRecordUpdate();
			return;
		}
		wvo.setStartWorkingDate(startWorkDate);

		System.out.print("근무종료일(yyyy.MM.dd) : ");
		String endWorkDate = MemberMain.scan.nextLine();

		if (endWorkDate.length() != 10 || endWorkDate.replace(".", "").length() != 8 || endWorkDate.indexOf(".") != 4
				|| endWorkDate.lastIndexOf(".") != 7) {
			System.out.println("형식이 맞지 않습니다.");
			adminWorkRecordUpdate();
			return;
		}

		SimpleDateFormat fDate = new SimpleDateFormat("yyyy.MM.dd");
		try {
			if (fDate.parse(endWorkDate).before(fDate.parse(startWorkDate))) {
				System.out.print("근무종료일이 근무시작일 이전일 수는 없습니다. ");
				System.out.println("근무종료일과 근무시작일을 확인해주시고 다시 입력해주세요.");
				adminWorkRecordUpdate();
				return;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		wvo.setEndWorkingDate(endWorkDate);

		System.out.println("==========================================");
		while(true) {
		System.out.println("● 해당 근무기록 정보를 수정하시겠습니까? Y / N");
		yn = MemberMain.scan.nextLine();
		if (yn.equalsIgnoreCase("y")) {
			System.out.println();
			boolean result = wdao.workRecordUpdate(wvo, workNo);
			if (result) {
				System.out.println("수정이 완료되었습니다.");
				break;
			} else {
				System.out.println("수정에 실패하였습니다.");
				break;
			}
		} else if (yn.equalsIgnoreCase("n")) {
			System.out.println("취소되었습니다.");
			adminWorkInfo();
			break;
		} else {
			System.out.println("Y 또는 N을 입력해주세요.");
		}
		}
		adminWorkInfo();
	}

	public void adminWorkDelete() {
		System.out.println("============ 근무기록 삭제 =============");
		while(true) {
		System.out.println("● 해당 근무기록 정보를 삭제하시겠습니까? Y / N");
		yn = MemberMain.scan.nextLine();
		if (yn.equalsIgnoreCase("y")) {
			boolean result = wdao.workRecordDelete(workNo);
			if (result) {
				System.out.println("삭제되었습니다.");
				break;
			} else {
				System.out.println("삭제에 실패했습니다.");
				break;
			}
		} else if (yn.equalsIgnoreCase("n")) {
			System.out.println("취소되었습니다.");
			adminWorkRecordManage();
			return;
		} else {
			System.out.println("Y 또는 N을 입력해주세요.");
		}
		}
		adminWorkRecordMenu();
	}
}