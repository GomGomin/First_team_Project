package hr.main;

import java.lang.reflect.Member;
import java.util.InputMismatchException;
import java.util.List;

import hr.dao.*;
import hr.vo.*;

public class DepartmentMain {
	private int input;
	private String YesNo;
	private DepartmentDAO ddao = new DepartmentDAO();
	public static int deptNo;
	

	public void departmentCheck() { // 부서 전체 조회 화면
		List<DepartmentVO> dvoList = ddao.departmentList();

		if (dvoList == null || dvoList.size() < 1) { // 만약에 부서가 없다면 밑에 문장 출력
			System.out.println(">> 등록된 부서가 없습니다.");
		} else { // 그렇지 않다면 부서 전체 목록을 출력
			System.out.println("========== 부서 전체 조회 =========="); // 부서 전체 목록
			System.out.println("| 부서명 |");
			for (DepartmentVO dvo : dvoList) {
				System.out.println("| " + dvo.getDepartmentName() + " |");
			}
			System.out.println("-----------------------------------");
			System.out.println("1. 부서 상세보기 2. 이전으로");
			System.out.print(">> 선택 :");
			try {
			input = MemberMain.scan.nextInt();
			MemberMain.scan.nextLine();

			switch (input) {
			case 1:
				departmentInfo();
				break;
			case 2:
				if (deptNo == 1) {
					adminDeMenu();
					break;
				} else {
					new MemberMain().memberMenu();
					break;
				}
			default: // 그외의 경우 '1~2 입력해주세요' 출력
				System.out.println("==========================");
				System.out.println(">> 1 ~ 2 중에 하나를 입력해주세요");
				departmentCheck();
			}
			}catch(InputMismatchException e) {
				System.out.println("==========================");
				System.out.println(">> 1 ~ 2 중에 하나를 입력해주세요");
				MemberMain.scan.next();
				departmentCheck();
			}
		}
	}

	public void departmentInfo() { // 부서 상세보기
		System.out.println("============ 관리자 부서 상세 보기 ===============");
		System.out.print("부서명: ");
		String departmentName = MemberMain.scan.nextLine();
		DepartmentVO dvo = new DepartmentVO();
		dvo.setDepartmentName(departmentName);
		int result = 0;
		result = ddao.departmentDuplication(dvo);
		if (result == 0) {
			System.out.println("부서가 존재하지 않습니다.");
			if(MemberMain.deptNo ==1 ) {
				adminDeMenu();
			} else {
				departmentCheck();
			}
		} else {
			System.out.print("재직 중인 직원 수 : ");
			System.out.println(ddao.departmentInfo(dvo) + "명");
			System.out.print("직위 : ");
			ddao.departmentSelect(dvo);
			List<DepartmentVO> dvoList = ddao.departmentSelect(dvo);
			int last = dvoList.size()-1;
			int i = 0;
			for (DepartmentVO dvo1 : dvoList) {
				System.out.print(dvo1.getDepartmentName());
				if(i != last) {
					System.out.print(", ");
				}
				i++;
			}
			System.out.println();

			while (true) {
				System.out.println("1번을 누를시 이전 화면으로 돌아갑니다.");
				try {
				input = MemberMain.scan.nextInt();
				MemberMain.scan.nextLine();

				switch (input) {
				case 1:
					departmentCheck();
					break;
				default: //
					System.out.println("==========================");
					System.out.println(">> 1번을 입력해주세요");
				}
			}catch(InputMismatchException e) {
				MemberMain.scan.next();
				System.out.println("==========================");
				System.out.println(">> 1번을 입력해주세요");
			}
			}
		}
	}

	public void adminDeMenu() { // 관리자 부서 메뉴
		deptNo = 1;
		System.out.println("=================== 관리자 부서 메뉴 ==================="); // 부서 전체 목록
		System.out.println("1.부서 전체 조회 화면 2.관리자 부서 등록 화면 3.관리자 부서 관리 화면 4.이전으로 "); // 부서 전체 목록
		System.out.print("선택 :");
		try {
		input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();

		switch (input) {
		case 1:
			departmentCheck();
			break; // 1.설문 목록 출력
		case 2:
			adminDeInsert();
			break;
		case 3:
			adminDeInfo();
			break;
		case 4:
			new MemberMain().adminMenu();
			break;
		default: // 그외의 경우 '1~4 입력해주세요' 출력
			System.out.println("==========================");
			System.out.println(">> 1 ~ 4 중에 하나를 입력해주세요");
			adminDeMenu();
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 4 중에 하나를 입력해주세요");
			adminDeMenu();
		}
	}

	public void adminDeInsert() { // 관리자 부사 등록
		System.out.println();
		DepartmentVO dvo = new DepartmentVO();
		System.out.println("============ 관리자 부서 등록 화면 ===============");
		System.out.print("부서명: ");
		String departmentName = MemberMain.scan.nextLine();
		dvo.setDepartmentName(departmentName);
		int result = 0;
		result = ddao.departmentDuplication(dvo);
		if (result >= 1) {
			System.out.println("동일한 부서가 존재합니다.");
			System.out.println(result);
		} else {
			ddao.departmentInsert(dvo);
			System.out.println("부서가 등록되었습니다.");
		}
		adminDeMenu();
	}

//	public void adminDeManage() { // 관리자 부서 관리
//		System.out.println("============ 관리자 부서 관리 화면 ===============");
//		System.out.println("1.관리자 부서 상세보기 2.이전으로 ");
//		System.out.print("선택 :");
//		input = MemberMain.scan.nextInt();
//		MemberMain.scan.nextLine();
//
//		switch (input) {
//		case 1:
//			adminDeInfo();
//			break;
//		case 2:
//			adminDeMenu();
//			break;
//		default: // 그외의 경우 '1~2 입력해주세요' 출력
//			System.out.println("==========================");
//			System.out.println(">> 1 ~ 2 중에 하나를 입력해주세요");
//			adminDeManage();
//		}
//	}

	public void adminDeInfo() { // 관리자 부서 상세 보기
		DepartmentVO dvo = new DepartmentVO();
		System.out.println("============ 관리자 부서 상세 보기 ===============");
		System.out.print("부서명: ");
		String departmentName = MemberMain.scan.nextLine();
		dvo.setDepartmentName(departmentName);
		int result = 0;
		result = ddao.departmentDuplication(dvo);
		if (result == 0) {
			System.out.println("부서가 존재하지 않습니다.");
			adminDeMenu();
		} else {
			List<DepartmentVO> dvoList = ddao.departmentSelect(dvo);
			ddao.departmentSelect(dvo);
			int last = dvoList.size()-1;
			int i = 0;
			for (DepartmentVO dvo1 : dvoList) {
				System.out.print(dvo1.getDepartmentName());
			if(i != last){
				System.out.print(", ");
			}
			i++;
			}
			System.out.println();
			while(true) {
			System.out.println("========================================");
			System.out.println("1.관리자 부서명 변경 2.관리자 부서 삭제 3.이전으로");
			System.out.print("선택 :");
			try {
			input = MemberMain.scan.nextInt();
			MemberMain.scan.nextLine();
			switch (input) {
			case 1:
				adminDeUpdate(departmentName);
				break;
			case 2:
				adminDeDelete(dvo);
				break;
			case 3:
				adminDeMenu();
				break;
			default: // 그외의 경우 '1~3 입력해주세요' 출력
				System.out.println("==========================");
				System.out.println(">> 1 ~ 3 중에 하나를 입력해주세요");
			}
		}		catch(InputMismatchException e) {
				MemberMain.scan.next();
				System.out.println("==========================");
				System.out.println(">> 1 ~ 3 중에 하나를 입력해주세요");
			}
			}
	}
	}

	public void adminDeUpdate(String departmentName) { // 관리자 부서 변경
		System.out.println("============ 관리자 부서명 변경 화면 ===============");

		System.out.print("부서명 : ");
		String changeName = MemberMain.scan.nextLine();
		while (true) {
		System.out.println("● 해당 부서명을 변경하시겠습니까? Y/N");
		YesNo = MemberMain.scan.nextLine();
		if (YesNo.equals("Y") || YesNo.equals("y")) {
			ddao.departmentUpdate(departmentName, changeName);
			System.out.println("부서명이 변경되었습니다.");
			break;
		} else if (YesNo.equals("N") || YesNo.equals("n")) {
			System.out.println("취소되었습니다.");
			break;
		} else {
			System.out.println("y 또는 n을 입력해주세요.");
		}
		}
		adminDeMenu();
	}

	public void adminDeDelete(DepartmentVO dvo) {
		System.out.println("============ 관리자 부서 삭제 화면 ===============");
		
		while(true) {
		System.out.println("● 해당 부서를 삭제하시겠습니까? Y/N");
		YesNo = MemberMain.scan.nextLine();
		if (YesNo.equals("Y") || YesNo.equals("y")) {
			ddao.departmentDelete(dvo);
			System.out.println("부서가 삭제되었습니다.");
			break;
		} else if (YesNo.equals("N") || YesNo.equals("n")) {
			System.out.println("취소되었습니다.");
			break;
		} else {
			System.out.println("y 또는 n을 입력해주세요.");
		}
		}
		adminDeMenu();
}
	
}