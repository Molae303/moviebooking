package wk;

import java.util.ArrayList;
import java.util.Scanner;

import wk.controller.BookingRegisterManager;
import wk.controller.MovieListApi;
import wk.controller.MovieRegisterManager;
import wk.controller.UserRegisterManager;
import wk.model.MovieVO;
import wk.model.UserVO;
import wk.view.ADMIN_CHOICE;
import wk.view.INDEX_CHOICE;
import wk.view.MenuViewer;
import wk.view.USER_CHOICE;

public class MovieBookingMain {

	public static UserVO user = null;
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		indexMenu();

		System.out.println("THE END");
	}

	public static void indexMenu() {
		int choiceNum = 0;
		boolean exitFlag = false;
		UserRegisterManager userManager = new UserRegisterManager();

		while (!exitFlag) {
			MenuViewer.indexMenuView();
			choiceNum = Integer.parseInt(input.nextLine());

			switch (choiceNum) {

			case INDEX_CHOICE.LOGIN:
				// id pw 값을 받아서 로그인을 확인하고 user객체를 리턴받는다. 로그인 성공시 userMenuView함수를 호출 , user객체가
				// null일경우 로그인 실패.
				user = userManager.userLogin();
				if (user != null) {
					userMenu();
				}
				break;
			case INDEX_CHOICE.REGISTER:
				userManager.userRegistr();
				break;
			case INDEX_CHOICE.EXIT:
				exitFlag = true;
				break;
			default:
				System.out.println("해당 메뉴 번호만 입력하세요.");
			}
		}
	}

	
	//유저 메뉴
	public static void userMenu() {

		int choiceNum = 0;
		boolean exitFlag = false;
		MovieRegisterManager movieManager = new MovieRegisterManager();
		BookingRegisterManager bookingManager = new BookingRegisterManager();
		UserRegisterManager userManager = new UserRegisterManager();
		
		while (!exitFlag) {
			MenuViewer.userMenuView();
			choiceNum = Integer.parseInt(input.nextLine());

			switch (choiceNum) {
			case USER_CHOICE.MOVIE_LIST:
				movieManager.movieTotalList();
				break;
			case USER_CHOICE.BOOKING_INSERT:
				bookingManager.bookingRegistr(user);
				break;
			case USER_CHOICE.BOOKING_LIST:
				bookingManager.bookingList(user);
				break;
			case USER_CHOICE.BOOKING_DELETE:
				bookingManager.bookingDelete(user);
				break;
			case USER_CHOICE.USER_UPDATE:
				userManager.userUpdate();;
				break;
			case USER_CHOICE.ADMIN_MENU:
				if (user.isAdmin()) {
					adminMenu();
				}else {
					System.out.println("관리자계정이 아닙니다.");
				}
				break;
			case USER_CHOICE.LOGOUT:
				exitFlag = true;
				break;
			default:
				System.out.println("해당 메뉴 번호만 입력하세요.");
			}
		}
	}

	//관리자 메뉴
	public static void adminMenu() {
		
		MovieRegisterManager movieManager = new MovieRegisterManager();
		BookingRegisterManager bookingManager = new BookingRegisterManager();
		UserRegisterManager userManager = new UserRegisterManager();
		
		ArrayList<MovieVO> list = null;
		int choiceNum = 0;
		boolean exitFlag = false;
		
		while (!exitFlag) {
			MenuViewer.adminMenuView();
			choiceNum = Integer.parseInt(input.nextLine());
			switch(choiceNum) {
			case  ADMIN_CHOICE.MOVIE_LIST_LOAD : 
				list = MovieListApi.apiConnection();
				break; 
			case  ADMIN_CHOICE.MOVIE_LIST : 
				movieManager.movieTotalList();
				break; 
			case  ADMIN_CHOICE.MOVIE_INSERT :
				if(list != null) {
				movieManager.movieRegistr(list);
				}else{
					System.out.println("추가할 영화목록이 없습니다.");
				}
				break; 
			case  ADMIN_CHOICE.MOVIE_UPDATE : 
				movieManager.movieUpdate();
				break; 
			case  ADMIN_CHOICE.MOVIE_DELETE :
				movieManager.movieDelete();
				break; 
			case  ADMIN_CHOICE.BOOKING_LIST : 
				bookingManager.bookingTotalList();
				break; 
			case  ADMIN_CHOICE.USER_LIST : 
				userManager.userTotalList();
				break; 
			case  ADMIN_CHOICE.MAIN : 
				exitFlag = true;
				break; 
			
			}
		}

	}

}
