package wk.view;

public class MenuViewer {
	
	//초기메뉴 로그인, 회원가입 선택
	public static void indexMenuView() {
		System.out.println();
		System.out.println("해당번호를 입력하세요.");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료");
		System.out.print("번호 선택 >> ");
	}
	
	//메인메뉴 (로그인후 나오는 화면) 
	public static void userMenuView() {
		System.out.println();
		System.out.println("영화예매 프로그램");
		System.out.println("1. 상영중 영화목록");
		System.out.println("2. 영화 예매하기");
		System.out.println("3. 영화 예매내역 보기");
		System.out.println("4. 영화 예매취소 하기");
		System.out.println("5. 회원정보 수정하기");
		System.out.println("6. 관리자페이지 들어가기");
		System.out.println("7. 로그아웃");
		System.out.print("번호 선택 >> ");
	}
	
	//관리자메뉴 userVO 객체의 isAdmin값이 true 일 경우만 들어갈수있음.
	public static void adminMenuView() {
		System.out.println();
		System.out.println("관리자 메뉴");
		System.out.println("1. 영화리스트 가져오기"); // 웹 API 연결해서 영화 전체목록 가져오기
		System.out.println("2. 상영영화 목록보기");
		System.out.println("3. 상영영화 추가하기");
		System.out.println("4. 상영영화 수정하기");
		System.out.println("5. 상영영화 삭제하기");
		System.out.println("6. 예매 목록보기");
		System.out.println("7. 회원 목록보기");
		System.out.println("8. 메인 메뉴");
		System.out.print("번호 선택 >> ");
	}

}
