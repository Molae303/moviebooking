package wk.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

	public static Connection makeConnection() {

		String filePath = "C:/Users/wookyung/Desktop/Wookyung/doitjava/MovieBookingDB/src/wk/db.properties";
		Connection con = null;
		try {
			Properties properties = new Properties();
			properties.load(new FileReader(filePath));
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//System.out.println("드라이버 로드 성공");
			con = DriverManager.getConnection(url, user, password);
			//System.out.println("데이타베이스 접속 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("커낵션 실패");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
}
