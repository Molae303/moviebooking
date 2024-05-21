package wk.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import wk.model.BookingVO;
import wk.model.UserVO;

public class BookingDAO {

	// 예매 추가
	public void setBookingRegiste(BookingVO bvo, String seat) {

		String sql = "{CALL booking_insert_proc(?,?,?,?)}";
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			con = DBUtil.makeConnection();
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, bvo.getUserId());
			cstmt.setInt(2, bvo.getMovieCd());
			cstmt.setInt(3, bvo.getPrice());
			cstmt.setString(4, seat);

			int result = cstmt.executeUpdate();

			if (result == 1) {
				System.out.println(bvo.getMovieCd() + "영화 예매 완료");
				System.out.println("영화 예매 성공");
			} else {
				System.out.println("영화 예매 실패");
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

	// 예매내역 보기
	public ArrayList<BookingVO> getBookingList(UserVO user) {

		ArrayList<BookingVO> list = new ArrayList<>();
		BookingVO bvo = null;
		String userName = null;
		String movieNm = null;
		int price = 0;
		int quantity = 0;
		int totalPrice = 0;
		int no = 0;

		String sql = "SELECT ROWNUM as no, username, movieCd, movienm, screendt, price, quantity\r\n" + "FROM (\r\n"
				+ "    SELECT U.username, M.movieCd, M.movienm, M.screendt, SUM(B.price) as price, COUNT(*) as quantity\r\n"
				+ "    FROM BOOKING B \r\n" + "    INNER JOIN USERTBL U ON B.userId = U.userId\r\n"
				+ "    INNER JOIN MOVIE M ON B.movieCd = M.movieCd\r\n" + "    WHERE B.userId = ?\r\n"
				+ "    GROUP BY U.username, M.movieCd, M.movienm, M.screendt)";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			rs = pstmt.executeQuery();

			System.out.println("번호\t고객명\t영화코드\t\t영화명\t\t상영일\t\t가격\t예매수량");

			while (rs.next()) {
				bvo = new BookingVO();
				no = rs.getInt("no");
				userName = rs.getString("userName");
				bvo.setMovieCd(rs.getInt("movieCd"));
				movieNm = rs.getString("movieNm");
				bvo.setScreenDt(rs.getString("screenDt"));
				price = rs.getInt("price");
				quantity = rs.getInt("quantity");
				System.out.println(no + "\t" + userName + "\t" + bvo.getMovieCd() + "\t" + movieNm + "\t\t"
						+ bvo.getScreenDt() + "\t" + price + "\t" + quantity);
				totalPrice += price;
				list.add(bvo);
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
		return list;
	}

	// 예매내역 삭제
	public void setBookingDelete(UserVO user, int bookingId) {

		String sql = "{CALL booking_delete_proc(?,?)}";

		Connection con = null;
		CallableStatement cstmt = null;

		try {
			con = DBUtil.makeConnection();
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, user.getUserId());
			cstmt.setInt(2, bookingId);

			int i = cstmt.executeUpdate();

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

	// 예매내역 전체 확인 (admin)
	public void getBookingTotalList() {

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

			System.out.println("예매일\t\t고객명\t영화코드\t\t영화명\t\t상영일\t\t가격\t예매수량");

			while (rs.next()) {
				bookingDate = rs.getDate("bookingDate");
				userName = rs.getString("userName");
				movieCd = rs.getInt("movieCd");
				movieNm = rs.getString("movieNm");
				screenDt = rs.getString("screenDt");
				price = rs.getInt("price");
				quantity = rs.getInt("quantity");
				System.out.println(bookingDate + "\t" + userName + "\t" + movieCd + "\t" + movieNm + "\t\t" + screenDt
						+ "\t" + price + "\t" + quantity);
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

	// 예매 상세내역 보기
	public void getBookingInfo(UserVO user, int movieCd, String screenDt) {

		int bookingId = 0;
		Date bookingDate = null;
		String userName = null;
		String movieNm = null;
		int price = 0;
		int totalPrice = 0;
		String seat = null;

		String sql = "SELECT B.bookingId, B.bookingdate, U.username, M.movieCd, M.movienm, M.screendt, B.price, B.seat\r\n"
				+ "FROM BOOKING B INNER JOIN USERTBL U ON B.userId = U.userId\r\n"
				+ "               INNER JOIN MOVIE M ON B.movieCd = M.movieCd\r\n"
				+ "    WHERE B.userId = ? AND B.movieCd = ? AND M.screenDt = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setInt(2, movieCd);
			pstmt.setString(3, screenDt);
			rs = pstmt.executeQuery();

			System.out.println("예약코드\t예매일\t\t고객명\t영화코드\t\t영화명\t\t상영일\t\t가격\t좌석");

			while (rs.next()) {
				bookingId = rs.getInt("bookingId");
				bookingDate = rs.getDate("bookingDate");
				userName = rs.getString("userName");
				movieCd = rs.getInt("movieCd");
				movieNm = rs.getString("movieNm");
				screenDt = rs.getString("screenDt");
				price = rs.getInt("price");
				seat = rs.getString("seat");
				System.out.println(bookingId + "\t" + bookingDate + "\t" + userName + "\t" + movieCd + "\t" + movieNm
						+ "\t\t" + screenDt + "\t" + price + "\t" + seat);
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

	// 좌석 확인
	public ArrayList<String> setBookingSeat(int movieCd) {

		ArrayList<String> list = new ArrayList<>();
		String sql = "SELECT seat FROM Booking WHERE movieCd = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movieCd);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("seat"));
			}
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

		return list;
	}

	// 중복 좌석 확인
	public boolean getSeatOverlap(int movieCd, String choiceSeat) {

		String sql = "SELECT * FROM booking WHERE movieCd =? AND seat = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean seatOverlapResult = false;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movieCd);
			pstmt.setString(2, choiceSeat);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				seatOverlapResult = true;
			}
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

		return seatOverlapResult;
	}

}
