package wk.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wk.model.UserVO;

public class UserDAO {

	// 회원가입
	public void setUserRegiste(UserVO uvo) {

		String sql = "{CALL user_inser_proc(?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement cstmt = null;
		int isAdmin = (uvo.isAdmin()) ? (1) : (0);

		try {
			con = DBUtil.makeConnection();
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, uvo.getUserId());
			cstmt.setString(2, uvo.getUserPw());
			cstmt.setString(3, uvo.getUserName());
			cstmt.setString(4, uvo.getUserPhone());
			cstmt.setString(5, uvo.getUserEmail());
			cstmt.setInt(6, isAdmin);

			int i = cstmt.executeUpdate();

			if (i == 1) {
				System.out.println(uvo.getUserName() + "님 가입이 완료되었습니다.");
			} else {
				System.out.println("가입이 실패했습니다.");
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
			}
		}
	}

	// 유저 아이디 중복체크
	public boolean getUserIdOverlap(String idOverlap) {

		String sql = "SELECT * FROM userTbl WHERE userId = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean idOverlapResult = false;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idOverlap);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				idOverlapResult = true;
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
		return idOverlapResult;
	}
	
	//유저 이메일 중복체크
	public boolean getUserEmailOverlap(String emailOverlap) {
		String sql = "SELECT * FROM userTbl WHERE userEmail = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean emailOverlapResult = false;

		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, emailOverlap);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				emailOverlapResult = true;
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
		return emailOverlapResult;
	}
	
	
	//유저 로그인
	public UserVO getUserLogin(String id, String pw) {
		
		UserVO uvo = null;
		String sql = "SELECT * FROM userTbl WHERE userId=? AND userPw=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				uvo = new UserVO(rs.getString("userId"), rs.getString("userPw"), rs.getString("userName"), rs.getString("userPhone"), rs.getString("userEmail"), (rs.getInt("isAdmin")==1)?(true):(false));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return uvo;
	}

	//유저 정보 수정
	public void setUserUpdate(UserVO uvo) {
		
		String sql = "{CALL user_update_proc(?,?,?,?)}";
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			con = DBUtil.makeConnection();
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, uvo.getUserPw());
			cstmt.setString(2, uvo.getUserName());
			cstmt.setString(3, uvo.getUserPhone());
			cstmt.setString(4, uvo.getUserId());
			
			int i = cstmt.executeUpdate();

			if (i == 1) {
				System.out.println(uvo.getUserId() + "님 유저 정보 수정 완료.");
				System.out.println("유저 정보 수정 성공");
			} else {
				System.out.println("유저 정보 수정 실패");
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

	//유저 전체 목록 보기(admin)
	public void getUserTotalList() {
		
		UserVO uvo = null;
		String sql = "SELECT * FROM userTbl";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println(String.format("%-15s %-20s %-15s %-15s %-30s %-10s", "아이디","비밀번호","성함","전화번호","이메일","관리자(t/f)"));
			while(rs.next()) {
				uvo = new UserVO(rs.getString("userId"), rs.getString("userPw"), rs.getString("userName"), rs.getString("userPhone"), rs.getString("userEmail"), (rs.getInt("isAdmin")==1)?(true):(false));
				System.out.println(String.format("%-15s %-20s %-15s %-15s %-30s %-10s", uvo.getUserId(),uvo.getUserPw(),uvo.getUserName(),uvo.getUserPhone(),uvo.getUserEmail(), uvo.isAdmin() ? "true": "false"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}















