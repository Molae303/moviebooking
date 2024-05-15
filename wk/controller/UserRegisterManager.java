package wk.controller;

import java.util.Scanner;

import wk.model.UserVO;

public class UserRegisterManager {

	public static Scanner input = new Scanner(System.in);

	// 회원가입
	public void userRegistr() {

		UserDAO udao = new UserDAO();
		UserVO uvo = null;

		String userId = null;
		String userPw = null;
		String userName = null;
		String userPhone = null;
		String userEmail = null;

		boolean exitFlag = false;

		System.out.println("회원 정보 입력");

		while (!exitFlag) {
			System.out.print("아이디 : ");
			userId = input.nextLine();
			if (udao.getUserIdOverlap(userId)) {
				System.out.println("중복된 아이디 입니다. 다시 입력하세요");
			} else {
				exitFlag = true;
			}
		}
		System.out.print("비밀번호 : ");
		userPw = input.nextLine();
		System.out.print("성함 : ");
		userName = input.nextLine();
		System.out.print("전화번호 : ");
		userPhone = input.nextLine();

		while (exitFlag) {
			System.out.print("이메일 : ");
			userEmail = input.nextLine();
			if (udao.getUserEmailOverlap(userEmail)) {
				System.out.println("중복된 아이디 입니다. 다시입력하세요");
			} else {
				exitFlag = false;
			}
		}
		uvo = new UserVO(userId, userPw, userName, userPhone, userEmail, false);
		udao.setUserRegiste(uvo);
	}

	// 유저 로그인
	public UserVO userLogin() {

		UserDAO udao = new UserDAO();
		UserVO uvo = null;

		System.out.print("아이디 : ");
		String userId = input.nextLine();
		System.out.print("비밀번호 : ");
		String userPw = input.nextLine();

		uvo = udao.getUserLogin(userId, userPw);
		if(uvo != null) {
			System.out.println("로그인 완료");
		}else {
			System.out.println("로그인 실패\n아이디와 비밀번호를 확인해주세요.");
		}
		return uvo;
	}
	
	//유저 정보 수정
	public void userUpdate() {
		
		UserDAO udao = new UserDAO();
		UserVO uvo = null;
		
		String id = null;			//아이디 입력
		String pw = null;			//비밀번호 입력
		String userPw = null;		//수정할 비밀번호
		String userName = null;		//수정할 유저이름
		String userPhone = null;	//수정할 전화번호
		
		boolean exitFlag = false;
		
		while(!exitFlag) {
			
			System.out.println("유저 정보 수정");
			System.out.print("아이디 : ");
			id = input.nextLine();
			System.out.print("비밀번호 : ");
			pw = input.nextLine();
			uvo = udao.getUserLogin(id, pw);
			if(uvo != null) {
				exitFlag = true;
			}else {
				System.out.println("아이디와 비밀번호를 확인해주세요.");
			}
		}
		System.out.println("현재 유저 정보");
		System.out.println(String.format("%-12s %-5s %-12s","비밀번호","이름","전화번호"));
		System.out.println(String.format("%-12s %-5s %-12s", uvo.getUserPw(), uvo.getUserName(), uvo.getUserPhone()));
		System.out.println();
		System.out.print("비밀번호 : ");
		userPw = input.nextLine();
		System.out.print("이름 : ");
		userName = input.nextLine();
		System.out.print("전화번호 : ");
		userPhone = input.nextLine();
		
		uvo.setUserPw(userPw);
		uvo.setUserName(userName);
		uvo.setUserPhone(userPhone);
		
		udao.setUserUpdate(uvo);
		
	}

	public void userTotalList() {
		
		UserDAO udao = new UserDAO();
		
		udao.getUserTotalList();
		
	}


}
