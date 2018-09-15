package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * 메소드를 이용한 의존관계 주입
 * 1-33 수정자 메소드 DI 방식을 사용한 UserDao
 */
public class UserDao {
	private Connection c;
	private ConnectionMaker connectionMaker;
		
	// setter 메소드를 이용한 의존관계 주입(DI)
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	} // 수정자 메소드 DI의 전형적인 코드다. 잘 기억해두자. setter 메소드는 보통 IDE의 자동생성 기능을 사용해서 만드는 것이 편리하다.
	
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
