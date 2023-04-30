package hr.main;

import java.util.InputMismatchException;
import java.util.List;

import hr.dao.NoticeDAO;
import hr.vo.NoticeVO;

public class NoticeMain {

	private NoticeDAO ndao = new NoticeDAO();
	private NoticeVO nvo = new NoticeVO();
	private String search;
	private int noticeNumber;

	public void noticeCheck() {
		System.out.println("================= 공지사항 조회 메뉴 ==================");
		System.out.println("1. 전체 목록\n2. 글제목 검색\n3. 이전으로");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			noticeList();
			break;
		case 2:
			noticeSearch();
			break;
		case 3:
			if (MemberMain.deptNo == 1) {
				adminNoticeMenu();
			} else {
				new MemberMain().memberMenu();
			}
			break;
		default:
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			noticeCheck();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			noticeCheck();
		}
	}

	public void noticeList() {
		System.out.println("================= 공지사항 전체 목록 ==================");
		System.out.println("| 글번호 | 글제목 | 작성일 | 작성자 |");
		List<NoticeVO> noticeList = ndao.noticeSelectAll();
		for (NoticeVO nvo : noticeList) {
			System.out.println("| " + nvo.getNoticeNumber() + " | " + nvo.getNoticeTitle() + " | " + nvo.getNoticeDate()
					+ " | " + nvo.getNoticeWriter() + " |");
		}
		System.out.println("---------------------------------------------------------------------------------------");
		System.out.println("1. 글 상세보기     2. 이전으로");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			System.out.println("---------------------------------------------------------------------------------");
			System.out.print("글 번호 선택 : ");
			noticeNumber = MemberMain.scan.nextInt();
			MemberMain.scan.nextLine();
			boolean numCheck = false; // 글 목록에 해당하는 글 번호를 선택했는지 체크
			for (NoticeVO nvo2 : noticeList) { // noticeVO 객체의 noticenumber 값 중 하나라도 일치하면 numCheck값 true로 변경
				if (nvo2.getNoticeNumber() == noticeNumber) {
					numCheck = true;
				}
			}
			if (numCheck == true) { // numCheck가 true인 경우에만 상세 정보로 넘김
				if (MemberMain.deptNo == 1) {
					adminNoticeInfo();
				} else {
					memberNoticeInfo();
				}
			} else {
				System.out.println("글 번호를 확인해주세요.");
				noticeList();
			}
			break;
		case 2:
			noticeCheck();
			break;
		default:
			System.out.println(">> 1 ~ 2 사이의 번호를 입력해주세요.");
			noticeList();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 2 사이의 번호를 입력해주세요.");
			noticeList();
		}
	}

	public void noticeSearch() {
		System.out.println("1. 검색     2. 이전으로  ");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			System.out.println("================= 공지사항 글제목 검색  ================= ");
			System.out.print("> 검색어 : ");
			search = MemberMain.scan.nextLine();
			List<NoticeVO> notiveList = ndao.noticeSearchList(search);
			if (notiveList == null || notiveList.isEmpty()) {
				System.out.println("검색 결과가 없습니다.");
				noticeSearch();
			} else {
				noticeSearchResult(notiveList);
			}
			break;
		case 2:
			noticeCheck();
			break;
		default:
			System.out.println(">> 1 ~ 2 사이의 번호를 입력해주세요.");
			noticeSearch();
			break;
		}
		}catch (InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 2 사이의 번호를 입력해주세요.");
			noticeSearch();
		}
	}

	public void noticeSearchResult(List<NoticeVO> noticeList) {

		System.out.println("================= 공지사항 검색 목록 ==================");
		System.out.println("| 글번호 | 글제목 | 작성일 | 작성자 |");
		for (NoticeVO nvo : noticeList) {
			System.out.println("| " + nvo.getNoticeNumber() + " | " + nvo.getNoticeTitle() + " | " + nvo.getNoticeDate()
					+ " | " + nvo.getNoticeWriter() + " |");
		}
		System.out.println("---------------------------------------------------------------------------------------");
		System.out.println("1. 글 상세보기     2. 이전으로");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			System.out
					.println("---------------------------------------------------------------------------------------");
			System.out.print("글 번호 선택 : ");
			noticeNumber = MemberMain.scan.nextInt();
			MemberMain.scan.nextLine();
			boolean numCheck = false; // 글 목록에 해당하는 글 번호를 선택했는지 체크
			for (NoticeVO nvo2 : noticeList) { // noticeVO 객체의 noticenumber 값 중 하나라도 일치하면 numCheck값 true로 변경
				if (nvo2.getNoticeNumber() == noticeNumber) {
					numCheck = true;
				}
			}
			if (numCheck == true) { // numCheck가 true인 경우에만 상세 정보로 넘김
				if (MemberMain.deptNo == 1) {
					adminNoticeInfo();
				} else {
					memberNoticeInfo();
				}
			} else {
				System.out.println("글 번호를 확인해주세요.");
				noticeSearchResult(noticeList);
			}
			break;
		case 2:
			noticeSearch();
			break;
		default:
			System.out.println("1번 혹은 2번을 입력해주세요.");
			noticeSearchResult(noticeList);
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println("1번 혹은 2번을 입력해주세요.");
			noticeSearchResult(noticeList);
		}
	}

	public void memberNoticeInfo() {
		nvo = ndao.noticeInfo(noticeNumber);
		System.out.println("==================공지사항 제목=================");
		System.out.println("글 번호 : " + nvo.getNoticeNumber());
		System.out.println("글 제목 : " + nvo.getNoticeTitle());
		System.out.println("글 내용 : " + nvo.getNoticeContents());
		System.out.println("작성자 : " + nvo.getNoticeWriter());
		System.out.println("작성일 : " + nvo.getNoticeDate());
		System.out.println("=================================================");
		noticeCheck();
	}

	public void adminNoticeMenu() {
		System.out.println("==================== 공지사항 메뉴 ====================");
		System.out.println("1. 공지사항 조회 및 관리     2. 공지사항 등록     3.이전 메뉴로 가기");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			noticeCheck();
			break;
		case 2:
			adminNoticeInsert();
			break;
		case 3:
			new MemberMain().adminMenu();
			break;
		default:
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminNoticeMenu();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminNoticeMenu();
		}
	}

	public void adminNoticeInfo() {
		nvo = ndao.noticeInfo(noticeNumber);
		System.out.println("==================== 공지사항 상세보기 ====================");
		System.out.println("글 번호 : " + nvo.getNoticeNumber());
		System.out.println("글 제목 : " + nvo.getNoticeTitle());
		System.out.println("글 내용 : " + nvo.getNoticeContents());
		System.out.println("작성일 : " + nvo.getNoticeDate());
		System.out.println("작성자 : " + nvo.getNoticeWriter());
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("1.수정     2.삭제     3. 이전 메뉴로 가기");
		System.out.print("선택 : ");
		try {
		int input = MemberMain.scan.nextInt();
		MemberMain.scan.nextLine();
		switch (input) {
		case 1:
			adminNoticeUpdate(nvo);
			break;
		case 2:
			adminNoticeDelete(nvo);
			break;
		case 3:
			noticeCheck();
			break;
		default:
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminNoticeInfo();
			break;
		}
		}catch(InputMismatchException e) {
			MemberMain.scan.next();
			System.out.println(">> 1 ~ 3 사이의 번호를 입력해주세요.");
			adminNoticeInfo();
		}
	}

	public void adminNoticeUpdate(NoticeVO nvo) {
		System.out.println("==================== 공지사항 수정 ====================");
		System.out.print(" 1. 글제목 : ");
		nvo.setNoticeTitle(MemberMain.scan.nextLine());
		System.out.print(" 2. 글내용 : ");
		nvo.setNoticeContents(MemberMain.scan.nextLine());
		while (true) { // y or n 값을 받을 때까지 반복
			System.out.println("==============================================");
			System.out.println("● 수정하시겠습니까? Y / N");
			String re = MemberMain.scan.next();
			if (re.equals("y") || re.equals("Y")) {
				boolean result = ndao.noticeUpdate(nvo);
				if (result) {
					System.out.println("공지사항 수정이 완료되었습니다.");
				} else {
					System.out.println("공지사항 수정이 실패하였습니다. 다시 시도해주세요.");
				}
				break;
			} else if (re.equals("n") || re.equals("N")) {
				System.out.println("취소되었습니다.");
				break;
			} else {
				System.out.println("Y와 N 중 하나를 입력해주세요.");
			}
		}
		adminNoticeInfo();
	}

	public void adminNoticeDelete(NoticeVO nvo) {
		System.out.println("==================== 공지사항 삭제 ====================");

		while (true) { // y or n 값을 받을 때까지 반복
			System.out.println("● 정보를 삭제하시겠습니까? Y / N");
			String re = MemberMain.scan.next();
			if (re.equals("y") || re.equals("Y")) {
				boolean result = ndao.noticeDelete(nvo);
				if (result) {
					System.out.println("공지사항 삭제가 완료되었습니다.");
				} else {
					System.out.println("공지사항 삭제가 실패하였습니다. 다시 시도해주세요.");
				}
				noticeCheck();
				break;
			} else if (re.equals("n") || re.equals("N")) {
				System.out.println("취소되었습니다.");
				adminNoticeInfo();
				break;
			} else {
				System.out.println("Y와 N 중 하나를 입력해주세요.");
			}
		}
	}

	public void adminNoticeInsert() {
		System.out.println("================= 관리자 공지사항 등록 =================");
		System.out.print(" 1. 글제목 : ");
		nvo.setNoticeTitle(MemberMain.scan.nextLine());
		System.out.print(" 2. 글내용 : ");
		nvo.setNoticeContents(MemberMain.scan.nextLine());
		while (true) { // y or n 값을 받을 때까지 반복
			System.out.println("==============================================");
			System.out.println("● 정보를 등록하시겠습니까? Y / N");
			String re = MemberMain.scan.next();
			if (re.equals("y") || re.equals("Y")) {
				boolean result = ndao.noticeInsert(nvo, MemberMain.myId);
				if (result) {
					System.out.println("공지사항 등록이 완료되었습니다.");
				}else {
					System.out.println("공지사항 등록이 실패하였습니다. 다시 시도해주세요.");
				}
				break;
			} else if (re.equals("n") || re.equals("N")) {
				System.out.println("취소되었습니다.");
				break;
			} else {
				System.out.println("Y와 N 중 하나를 입력해주세요.");
			}
		}
		adminNoticeMenu();
	}
}