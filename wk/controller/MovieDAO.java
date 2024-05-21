package wk.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wk.model.MovieVO;

public class MovieDAO {

	// 영화등록
	public void setMovieRegiste(MovieVO mvo, String screenDt) {

//		String sql = "INSERT INTO movie VALUES (?, ?, ?, ?, ?, ?, ?)";
		String sql = "{CALL movie_insert_proc(?,?,?,?,?,?,?)}";
		Connection con = null;
//		PreparedStatement pstmt = null;
		CallableStatement cstmt = null;

		try {
			con = DBUtil.makeConnection();
			cstmt = con.prepareCall(sql);
			cstmt.setInt(1, mvo.getMovieCd());
			cstmt.setString(2, mvo.getMovieNm());
			cstmt.setString(3, mvo.getOpenDt());
			cstmt.setString(4, mvo.getGenreAlt());
			cstmt.setString(5, mvo.getRepNationNm());
			cstmt.setString(6, mvo.getDirector());
			cstmt.setString(7, screenDt);

			int i = cstmt.executeUpdate();

			if (i == 1) {
				System.out.println(mvo.getMovieCd() + "영화 등록 완료.");
				System.out.println("영화 등록 성공");
			} else {
				System.out.println("영화 등록 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null) {
					cstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 상영 영화 상영일 수정하기
	public void setMovieUpdate(int movieCd, String screenDt) {

		String sql = "{CALL movie_update_proc(?,?)}";
		Connection con = null;
		CallableStatement cstmt = null;
		

		try {
			con = DBUtil.makeConnection();
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, screenDt);
			cstmt.setInt(2, movieCd);

			int i = cstmt.executeUpdate();

			if (i == 1) {
				System.out.println(movieCd + "영화 정보 수정 완료.");
				System.out.println("영화 정보 수정 성공");
			} else {
				System.out.println("영화 정보 수정 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null) {
					cstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 상영 영화 삭제하기
	public void setMovieDelete(int movieCd) {

		String sql = "{CALL movie_delete_proc(?)}";

		Connection con = null;
		CallableStatement cstmt = null;

		try {
			con = DBUtil.makeConnection();
			cstmt = con.prepareCall(sql);
			cstmt.setInt(1, movieCd);

			int i = cstmt.executeUpdate();

			if (i == 1) {
				System.out.println("영화 삭제 완료");
				System.out.println("영화 삭제 성공");
			} else {
				System.out.println("영화 삭제 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null) {
					cstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 상영 영화 전체목록보기
	public void getMovieTotalList() {

		String sql = "SELECT * FROM movie";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MovieVO mvo = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			System.out.println(String.format("%-10s %-10s %-10s %-10s %-5s %-10s %-10s", "영화코드", "영화명", "개봉연도", "장르", "국가", "감독", "상영일"));
			while (rs.next()) {
				mvo = new MovieVO(rs.getInt("movieCd"), rs.getString("movieNm"), rs.getString("openDt"),
						rs.getString("genreAlt"), rs.getString("repnationNm"), rs.getString("director"),
						rs.getString("screenDt"));
				System.out.println(mvo.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
