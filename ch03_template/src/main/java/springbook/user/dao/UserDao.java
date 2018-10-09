package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * 3.1.1 예외처리 기능을 갖춘 DAO
 */
public class UserDao {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// 사용자 데이터 추가
	public void add(User user) throws SQLException {
		
		Connection c = dataSource.getConnection();
		
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
	public User get(String id) throws SQLException {

		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"SELECT * FROM users WHERE id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		User user = null;  // User는 null 상태로 초기화해놓는다.
		if(rs.next()) {    // id를 조건으로 한 쿼리의 결과가 있으면 User 오브젝트를 만들고 값을 넣어준다.
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		ps.close();
		c.close();
		// 결과가 없으면 User는 null 상태 그대로일 것이다. 이를 확인해서 예외를 던져준다.
		if(user == null) throw new EmptyResultDataAccessException(1);
		return user;
	}
	
	// 3-12 클라이언트 책임을 담당항 deleteAll() 메소드
	public void deleteAll() throws SQLException {
		StatementStrategy st = new DeleteAllStatement(); // 선정한 전략 클래스의 오브젝트 생성
		jdbcContextWithStatementStrategy(st);  // 컨텍스트 호출. 전략 오브젝트 전달
		/* 3-10 전략 패턴을 따라 DeleteAllStatement가 적용된 deleteAll()
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = dataSource.getConnection();
			StatementStrategy strategy = new DeleteAllStatement();
			ps = strategy.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(ps != null) try { ps.close(); } catch(SQLException e) {}
			if(c != null)  try { c.close();  } catch(SQLException e) {}
		}
		*/
	}
	
	// 3-11 메소드로 분리한 try/catch/finally 컨텍스트 코드
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(ps != null) try { ps.close(); } catch(SQLException e) {}
			if(c != null)  try { c.close();  } catch(SQLException e) {}
		}
	}
		
	// 3-3 JDBC 예외 처리를 적용한 getCount() 메소드
	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("SELECT COUNT(*) FROM users");
			
			rs = ps.executeQuery(); // ResultSet도 다양한 SQLException이 발생할 수 있는 코드이므로 try 블록안에 둬야 한다.
			rs.next();
			return rs.getInt(1);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) try { rs.close(); } catch(SQLException e) {}
			if(ps != null) try { ps.close(); } catch(SQLException e) {}
			if(c != null)  try { c.close();  } catch(SQLException e) {}
		}
	}
	
}
