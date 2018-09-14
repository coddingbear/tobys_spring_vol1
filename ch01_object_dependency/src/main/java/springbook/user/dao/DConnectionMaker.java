package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 인터페이스 도입 후 -> 구현
 * 1-9 ConnectionMaker 구현 클래스
 */
public class DConnectionMaker implements ConnectionMaker {

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		// D 사의 독자적인 방법으로 Connection을 생성하는 코드
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/springbook", "springuser", "springpass");
		return c;
	}
}
