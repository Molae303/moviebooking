package wk.controller;

import java.util.ArrayList;
import java.util.Scanner;

import wk.model.BookingVO;
import wk.model.UserVO;

public class BookingRegisterManager {

	public static Scanner input = new Scanner(System.in);
	public static final int MOVIE_PRICE = 10000;

	// 영화예매하기 (insert)
	public void bookingRegistr(UserVO user) {

		MovieRegisterManager movieManager = new MovieRegisterManager();
		BookingDAO bdao = new BookingDAO();
		BookingVO bvo = new BookingVO();

		int movieCd = 0;
		int quantity = 0;

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

		for (int i = 0; i < quantity; i++) {
			printBookingSeat(movieCd);
			String choiceSeat = null;
			boolean flag = false;

			while (!flag) {
				System.out.print("좌석 선택 (ex:A,10)>> ");
				String regex = "^[A-Z]+,[0-9]+{1,2}$";
				choiceSeat = input.nextLine();
				if (!choiceSeat.matches(regex)) {
					System.out.println("A,10 형식으로 입력부탁드립니다.");
				} else if (bdao.getSeatOverlap(movieCd,choiceSeat)) {
					System.out.println("이미 예매된 좌석입니다.");
				} else {
					bdao.setBookingRegiste(bvo, choiceSeat);
					flag = true;
				}
			}
			
		}

	}

	// 영화 예매내역 보기
	public void bookingList(UserVO user) {

		ArrayList<BookingVO> list = null;
		BookingDAO bdao = new BookingDAO();
		System.out.println(user.getUserName() + "님 예매 내역");
		list = bdao.getBookingList(user);
		System.out.println();
		System.out.println("상세내역 보기");
		System.out.print("번호 (메인메뉴 : -1)>> ");
		int inputNo = Integer.parseInt(input.nextLine());
		if (inputNo == -1) {
			return;
		}
		bdao.getBookingInfo(user, list.get(inputNo - 1).getMovieCd(), list.get(inputNo-1).getScreenDt());
	}

	//예매 취소하기(DELETE)
	public void bookingDelete(UserVO user) {

		int bookingId = 0;
		BookingDAO bdao = new BookingDAO();
		bookingList(user);

		System.out.println("취소할 예매내역의 예약코드를 입력해주세요");
		System.out.print("예약코드 >> ");
		bookingId = Integer.parseInt(input.nextLine());

		bdao.setBookingDelete(user, bookingId);

	}

	// 예매 전체목록 보기 ( 관리자 전용 )
	public void bookingTotalList() {

		BookingDAO bdao = new BookingDAO();

		bdao.getBookingTotalList();

	}

	
	//선택한 영화의 좌석 출력
	public void printBookingSeat(int movieCd) {

		ArrayList<String> list = null;

		BookingDAO bdao = new BookingDAO();

		int[][] seatArr = new int[10][10];
		list = bdao.setBookingSeat(movieCd);

		for (String data : list) {
			String[] seat = data.split(",");
			seatArr[(int) (seat[0].charAt(0) - 65)][Integer.parseInt(seat[1]) - 1] = 1;
		}

		System.out.print("\t");
		for (int i = 0; i < seatArr[0].length; i++) {
			System.out.print(" " + (i + 1));
		}
		System.out.println();
		for (int i = 0; i < seatArr.length; i++) {
			System.out.print((char) (i + 65) + "\t");
			for (int j = 0; j < seatArr[i].length; j++) {
				if (seatArr[i][j] == 0) {
					System.out.print(" □");
				} else {
					System.out.print(" ■");
				}
			}
			System.out.println();
		}
	}

}
