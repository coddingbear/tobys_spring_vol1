package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * 
 * 1-5 상속을 통한 확장 방법이 제공되는 UserDAO
 */
public abstract class UserDao {
	
	// 구현 코드는 제거되고 추상 메소드로 바뀌었다. 메소드의 구현은 서브클래스가 담당한다.
	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
		
	// 사용자 데이터 추가
	public void add(User user) throws ClassNotFoundException, SQLException {
		/* 중복된 코드를 추출하여 독립적인 메소드롤 만들어 중복을 제거했다.
		Class.forName("com.mysql.jdbc.Driver"); // DB 드라이버를 불러온다.

		//1. DB 연결을 위한 Connection을 가져온다.
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/springbook", "springuser", "springpass");
		*/
		Connection c = getConnection(); // DB 연결 기능이 필요하면 getConnection() 메소드를 이용하게 한다.
		
		//2. SQL을 담은 Statement 또는 PreparedStatement 를 만든다.
		PreparedStatement ps = c.prepareStatement(
				"INSERT INTO users(id, name, password) VALUES(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2,  user.getName());
		ps.setString(3, user.getPassword());
		
		// 3. 만들어진 Statement를 실행한다.
		ps.executeUpdate();
		
		// 4. 작업중에 생성된 Connection, Statement, ResultSet 리소스를 닫아 준다.
		ps.close();
		c.close();
	}
	
	// 사용자 데이터 가져오기
	public User get(String id) throws ClassNotFoundException, SQLException {

		Connection c = getConnection(); // DB 연결 기능이 필요하면 getConnection() 메소드를 이용하게 한다.
		
		// 2. SQL을 담은 Statement 또는 PreparedStatement 를 만든다.
		PreparedStatement ps = c.prepareStatement(
				"SELECT * FROM users WHERE id = ?");
		ps.setString(1, id);
		
		// 3. 만들어진 Statement를 실행한다.
		// 실행 결과를 ResultSet으로 받아서 정보를 저장할  오브젝트에 옮긴다.
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		// 4. 작업중에 생성된 Connection, Statement, ResultSet 리소스를 닫아 준다.
		rs.close();
		ps.close();
		c.close();
		return user;
	}
}
