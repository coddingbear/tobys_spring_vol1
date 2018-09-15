package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * 1-23 인스턴스 변수를 사용하도록 수정한 UserDao
 */
public class UserDao {
	private ConnectionMaker connectionMaker; // 초기에 설정하면 사용 중에는 바뀌지 않는 읽기전용 인스턴스 변수
	private Connection c;  // 메번 새로운 값으로 바뀌는 정보를 담은 인스턴스 변수
	private User user;
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
	// 사용자 데이터 추가
	public void add(User user) throws ClassNotFoundException, SQLException {
		
		this.c = connectionMaker.makeConnection();
		
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

		this.c = connectionMaker.makeConnection(); 
		
		// 2. SQL을 담은 Statement 또는 PreparedStatement 를 만든다.
		PreparedStatement ps = c.prepareStatement(
				"SELECT * FROM users WHERE id = ?");
		ps.setString(1, id);
		
		// 3. 만들어진 Statement를 실행한다.
		// 실행 결과를 ResultSet으로 받아서 정보를 저장할  오브젝트에 옮긴다.
		ResultSet rs = ps.executeQuery();
		rs.next();
		this.user = new User();
		this.user.setId(rs.getString("id"));
		this.user.setName(rs.getString("name"));
		this.user.setPassword(rs.getString("password"));
		
		// 4. 작업중에 생성된 Connection, Statement, ResultSet 리소스를 닫아 준다.
		rs.close();
		ps.close();
		c.close();
		return this.user;
	}
}
