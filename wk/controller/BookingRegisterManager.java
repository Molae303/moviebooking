package wk.controller;

import java.util.Scanner;

import wk.model.BookingVO;
import wk.model.UserVO;

public class BookingRegisterManager {
	
	public static Scanner input = new Scanner(System.in);
	public static final int MOVIE_PRICE = 10000;
	
	//영화예매하기 (insert)
	public void bookingRegistr(UserVO user) {
		
		MovieRegisterManager movieManager = new MovieRegisterManager();
		BookingDAO bdao = new BookingDAO();
		BookingVO bvo = new BookingVO();
		
		int movieCd = 0;
		int quantity = 0;
		int price = 0;
		
		System.out.println("상영중 영화 리스트");
		movieManager.movieTotalList();
		System.out.println();
		
		System.out.print("예매할 영화코드 입력 >> ");
		movieCd = Integer.parseInt(input.nextLine());
		System.out.print("예매할 수량 입력 >> ");
		quantity = Integer.parseInt(input.nextLine());
		
		bvo.setUserId(user.getUserId());
		bvo.setMovieCd(movieCd);
		bvo.setPrice(MOVIE_PRICE);
		
		
		bdao.setBookingRegiste(bvo, quantity);
		
	}

	//영화 예매내역 보기
	public void bookingList(UserVO user) {
		
		BookingDAO bdao = new BookingDAO();
		System.out.println(user.getUserName() + "님 예매 내역");
		bdao.getBookingList(user);
		System.out.println();
	}

	public void bookingDelete(UserVO user) {
		
		int movieCd = 0;
		BookingDAO bdao = new BookingDAO();
		bookingList(user);
		
		System.out.println("취소할 예매내역의 영화코드를 입력해주세요");
		System.out.print("영화코드 >> ");
		movieCd = Integer.parseInt(input.nextLine());
		
		bdao.setBookingDelete(user, movieCd);
		
	}

	//예매 전체목록 보기 ( 관리자 전용 )
	public void bookingTotalList() {
		
		BookingDAO bdao = new BookingDAO();
		
		bdao.getBookingTotalList();
		
	}
	
}
