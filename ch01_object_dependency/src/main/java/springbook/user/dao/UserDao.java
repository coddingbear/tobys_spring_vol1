package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * 싱글톤 패턴(singleton pattern)의 한계
 * 1-22 싱글톤 패턴을 적용한 UserDao
 */
public class UserDao {
	private static UserDao INSTANCE;
	
	private ConnectionMaker connectionMaker; // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.
	
	// 싱글톤을 적용하기 위해 생성자의 접근권한을 private 선언
	private UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
	public static synchronized UserDao getInstance(ConnectionMaker connectionMaker) {
		if(INSTANCE == null) INSTANCE = new UserDao(connectionMaker);
		return INSTANCE;
	}
	/* 싱글톤 단점:
	 * - private 생성자를 갖고 있기 때문에 상속할 수 없다.
	 * - 싱글톤은 테스트하기가 힘들다.
	 * - 서버환경에서 싱글톤이 하나만 만들어지는 것을 보장하지 못한다.
	 * - 싱글콩의 사용은 전역 상태를 만들 수 있기 때문에 바람직 하지 못하다.
	 */
	
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
