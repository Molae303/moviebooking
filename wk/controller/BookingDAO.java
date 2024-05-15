package wk.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import wk.model.BookingVO;
import wk.model.MovieVO;
import wk.model.UserVO;

public class BookingDAO {

	public void setBookingRegiste(BookingVO bvo, int quantity) {

		String sql = "INSERT INTO booking VALUES(booking_seq.nextval, ?, ?, SYSDATE, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection();
			for (int i = 0; i < quantity; i++) {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, bvo.getUserId());
				pstmt.setInt(2, bvo.getMovieCd());
				pstmt.setInt(3, bvo.getPrice());

				int result = pstmt.executeUpdate();

				if (result == 1) {
					System.out.println(bvo.getMovieCd() + "영화 예매 완료");
					System.out.println("영화 예매 성공");
				} else {
					System.out.println("영화 예매 실패");
				}
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

	//예매목록 보기 
	public void getBookingList(UserVO user) {
		
		int bookingId = 0;
		Date bookingDate = null;
		String userName = null;
		int movieCd = 0;
		String movieNm = null;
		String screenDt = null;
		int price = 0;
		int quantity = 0;
		int totalPrice = 0;
		
		String sql = "SELECT B.bookingdate, U.username, M.movieCd, M.movienm, M.screendt, SUM(B.price)as price ,COUNT(*) as quantity\r\n"
				+ "FROM BOOKING B INNER JOIN USERTBL U ON B.userId = U.userId\r\n"
				+ "INNER JOIN MOVIE M ON B.movieCd = M.movieCd\r\n"
				+ "WHERE B.userId = ?\r\n"
				+ "GROUP BY B.bookingdate, U.username, M.movieCd, M.movienm, M.screendt";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			rs = pstmt.executeQuery();

			System.out.println("예매일\t\t고객명\t영화코드\t\t영화명\t\t상영일\t\t가격\t예매갯수");

			while (rs.next()) {
				bookingDate = rs.getDate("bookingDate");
				userName = rs.getString("userName");
				movieCd = rs.getInt("movieCd");
				movieNm = rs.getString("movieNm");
				screenDt = rs.getString("screenDt");
				price = rs.getInt("price");
				quantity = rs.getInt("quantity");
				System.out.println(bookingDate +"\t"+ userName +"\t"+ movieCd +"\t"+ movieNm +"\t\t"+ screenDt +"\t"+ price +"\t"+ quantity);
				totalPrice += price;
			}
			System.out.println("총 급액 : " + totalPrice);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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

	
	//예매내역 삭제
	public void setBookingDelete(UserVO user, int movieCd) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM booking WHERE userId=? AND movieCd=?");
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, user.getUserId());
			pstmt.setInt(2, movieCd);
			
			int i = pstmt.executeUpdate();

			if (i >= 1) {
				System.out.println("예매내역 취소 완료");
				System.out.println("예매내역 취소 성공");
			} else {
				System.out.println("예매내역 취소 실패");
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

	
	//예매내역 전체 확인 (admin)
	public void getBookingTotalList() {
		
		int bookingId = 0;
		Date bookingDate = null;
		String userName = null;
		int movieCd = 0;
		String movieNm = null;
		String screenDt = null;
		int price = 0;
		int quantity = 0;
		int totalPrice = 0;
		
		String sql = "SELECT B.bookingdate, U.username, M.movieCd, M.movienm, M.screendt, SUM(B.price)as price ,COUNT(*) as quantity\r\n"
				+ "FROM BOOKING B INNER JOIN USERTBL U ON B.userId = U.userId\r\n"
				+ "INNER JOIN MOVIE M ON B.movieCd = M.movieCd\r\n"
				+ "GROUP BY B.bookingdate, U.username, M.movieCd, M.movienm, M.screendt";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			System.out.println("예매일\t\t고객명\t영화코드\t\t영화명\t\t상영일\t\t가격\t예매갯수");

			while (rs.next()) {
				bookingDate = rs.getDate("bookingDate");
				userName = rs.getString("userName");
				movieCd = rs.getInt("movieCd");
				movieNm = rs.getString("movieNm");
				screenDt = rs.getString("screenDt");
				price = rs.getInt("price");
				quantity = rs.getInt("quantity");
				System.out.println(bookingDate +"\t"+ userName +"\t"+ movieCd +"\t"+ movieNm +"\t\t"+ screenDt +"\t"+ price +"\t"+ quantity);
				totalPrice += price;
			}
			System.out.println("총 예매급액 : " + totalPrice);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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


}
