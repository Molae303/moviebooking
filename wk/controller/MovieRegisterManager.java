package wk.controller;

import java.util.ArrayList;
import java.util.Scanner;

import wk.model.MovieVO;

public class MovieRegisterManager {

	public static Scanner input = new Scanner(System.in);
	
	//상영영화 등록.
	public void movieRegistr(ArrayList<MovieVO> list) {
		
		if(list.size() < 1) {
			System.out.println("영화목록이 없습니다.");
			return;
		}
		
		MovieDAO mdao = new MovieDAO();
		MovieVO mvo = null;
		int movieCd = 0;
		String screenDt = null;
		
		System.out.println("전체영화 리스트");
		for(MovieVO data : list) {
			System.out.println(data.toString());
		}
		
		System.out.print("추가하실 영화코드 입력 >> ");
		movieCd = Integer.parseInt(input.nextLine());
		
		for(MovieVO data : list) {
			if(data.getMovieCd() == movieCd) {
				mvo = data;
			}
		}
		if(mvo == null) {
			System.out.println("찾으시는 영화코드가 없습니다.");
			return;
		}
		System.out.print("상영일 입력 >> ");
		screenDt = input.nextLine();
		
		mdao.setMovieRegiste(mvo, screenDt);
	}
	
	//상영 영화 수정
	public void movieUpdate() {
		
		MovieDAO mdao = new MovieDAO();
		
		System.out.println("상영중 영화 리스트");
		movieTotalList();
		System.out.println();
		
		System.out.print("수정할 영화코드 입력 >> ");
		int movieCd = Integer.parseInt(input.nextLine());
		System.out.print("상영일 : ");
		String screenDt = input.nextLine();
		mdao.setMovieUpdate(movieCd, screenDt);
		
		
	}
	
	//상영 영화 삭제
	public void movieDelete() {
		
		MovieDAO mdao = new MovieDAO();
		
		int movieCd;
		
		System.out.println("상영중 영화 리스트");
		mdao.getMovieTotalList();
		System.out.println();
		
		System.out.print("삭제할 영화 영화코드 입력 >> ");
		movieCd = Integer.parseInt(input.nextLine());
		
		mdao.setMovieDelete(movieCd);
		
		System.out.println();
		System.out.println("상영중 영화 리스트");
		mdao.getMovieTotalList();
		System.out.println();
		
	}
	
	
	//상영 영화 목록
	public void movieTotalList() {
		
		MovieDAO mdao = new MovieDAO();
		
		mdao.getMovieTotalList();
	}
	
}
