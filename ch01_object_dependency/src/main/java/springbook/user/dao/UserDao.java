package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * 인터페이스를 도입
 * 1-10 ConnectionMaker 인터페이스를 사용하여 개선한 UserDao
 */
public class UserDao {
	
	private ConnectionMaker connectionMaker; // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.
	
	// 생성자 메소드
	public UserDao() {
		connectionMaker = new DConnectionMaker(); // 앗! 근데 여기에는 클래스 이름이 나오네요!!
	}
	
	// 사용자 데이터 추가
	public void add(User user) throws ClassNotFoundException, SQLException {
		
		Connection c = connectionMaker.makeConnection(); // 인터페이스에 정의된 메소드를 사용하므로 클래스가 바뀐다고 해도 메소드 이름이 변경될 걱정은 없다.
		
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

		Connection c = connectionMaker.makeConnection(); // 인터페이스에 정의된 메소드를 사용하므로 클래스가 바뀐다고 해도 메소드 이름이 변경될 걱정은 없다.
		
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
