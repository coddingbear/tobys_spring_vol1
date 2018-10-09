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
	
	// 3-18 AddStatement를 익명 내부 클래스로 전환
	// 3-19 메소드 파라미터로 이전한 익명 내부 클래스
	public void add(final User user) throws SQLException {
		jdbcContextWithStatementStrategy(
			new StatementStrategy() { // 익명 내부 클래스는 구현하는 인터페이스를 생성자처럼 이용해서 오브젝트를 만든다.
				@Override
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					PreparedStatement ps = c.prepareStatement(
							"INSERT INTO users (id, name, password) VALUES (?,?,?)");
					ps.setString(1, user.getId()); // 로컬 클래스의 코드에서 외부의 메소드 로컬변수를 직접 접근할 수 있다.
					ps.setString(2, user.getName());
					ps.setString(3, user.getPassword());
					
					return ps;
				}
			}
		);
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
	
	// 3-20 익명 내부 클래스를 적용한 deleteAll() 메소드
	public void deleteAll() throws SQLException {
		jdbcContextWithStatementStrategy(
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatement(Connection c) 
						throws SQLException {
					return c.prepareStatement("DELETE FROM user");
				}
			}
		);
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
