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
 * 3.4 컨텍스트와 DI
 */
public class UserDao {
	private DataSource dataSource;
	
	// 3-22 JdbcContext를 DI 받아서 사용하도록 만든 UserDao
	private JdbcContext jdbcContext;
	
//	public void setJdbcContext(JdbcContext jdbcContext) { // JdbcContext를 DI 받도록 만든다.
//		this.jdbcContext = jdbcContext;
//	}
	
	public void setDataSource(DataSource dataSource) { 
		this.jdbcContext = new JdbcContext();       // JdbcContext 생성(IoC)
		this.jdbcContext.setDataSource(dataSource); // 의존 오브젝트 주입
		this.dataSource = dataSource; // 아직 JdbcContext를 적용하지 않는 메소드를 위해 저장해둔다.
	}
	
	
	public void add(final User user) throws SQLException {
		this.jdbcContext.workWithStatementStrategy( // DI 받은 JdbcContext의 컨텍스트 메소드를 사용하도록 변경한다.
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					PreparedStatement ps = c.prepareStatement(
							"INSERT INTO users (id, name, password) VALUES (?,?,?)");
					ps.setString(1, user.getId());
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
		PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
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
	
	// 3-22 jdbcContext를 DI 받아서 사용하도록 만든 UserDao
	public void deleteAll() throws SQLException {
		this.jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatement(Connection c) 
						throws SQLException {
					return c.prepareStatement("DELETE FROM users");
				}
			}
		);
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
