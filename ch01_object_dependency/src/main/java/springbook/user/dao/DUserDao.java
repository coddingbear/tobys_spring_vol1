package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//1-5 상속을 통한 확장 방법이 제공되는 UserDao
public class DUserDao extends UserDao {

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		// D 사 DB connection 생성코드
		
		Class.forName("com.mysql.jdbc.Driver"); // MySQL JDBC 드라이버를 불러온다.
		//1. DB 연결을 위한 Connection을 가져온다.
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/springbook", "springuser", "springpass");
		return c;
	}

}
