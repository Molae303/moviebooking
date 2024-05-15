package wk.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wk.model.MovieVO;

public class MovieDAO {

	// 영화등록
	public void setMovieRegiste(MovieVO mvo, String screenDt) {

		String sql = "INSERT INTO movie VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mvo.getMovieCd());
			pstmt.setString(2, mvo.getMovieNm());
			pstmt.setString(3, mvo.getOpenDt());
			pstmt.setString(4, mvo.getGenreAlt());
			pstmt.setString(5, mvo.getRepNationNm());
			pstmt.setString(6, mvo.getDirector());
			pstmt.setString(7, screenDt);

			int i = pstmt.executeUpdate();

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
				if (pstmt != null) {
					pstmt.close();
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

		String sql = "UPDATE movie SET screenDt = ? WHERE movieCd = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, screenDt);
			pstmt.setInt(2, movieCd);

			int i = pstmt.executeUpdate();

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
				if (pstmt != null) {
					pstmt.close();
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

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM movie WHERE movieCd = ?");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, movieCd);

			int i = pstmt.executeUpdate();

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
				if (pstmt != null) {
					pstmt.close();
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

			System.out.println("영화코드\t\t영화명\t\t개봉연도\t\t장르\t국가\t감독\t상영일");
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
