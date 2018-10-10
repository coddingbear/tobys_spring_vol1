package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mysql.jdbc.MysqlErrorNumbers;

import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * 4장 예외
 */
public class UserDaoJdbc implements UserDao{
	// 3-45 JdbcTemplate의 초기화를 위한 코드
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// 3-56 재사용 가능하도록 독립시킨 RowMapper
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};
	
	// 4-18 중복 키 예외의 전환
	public void add(final User user) throws DuplicateUserIdException {
		try {
			// jdbcTemplate을 이용해 User 를 add하는 코드
			this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?,?,?)", 
					user.getId(), user.getName(), user.getPassword());
		} catch (DuplicateKeyException e) {
			// 로그를 남기는 등의 필요한 작업
			throw new DuplicateUserIdException(e);
			// 예외를 전환할 때는 원인이 되는 예외를 중첩하는 것이 좋다.
		}
	}
	
	// 3-51 queryForObject()와 RowMapper를 적용한 get() 메소드
	public User get(String id) {
		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
			new Object[] {id}, // SQL에 바인딩할 파라미터 값, 가변인자 대신 배열을 사용한다.
			this.userMapper);
	}
	
	// 3-29 JdbcTemplate을 적용한 deleteAll() 메소드
	public void deleteAll() {
		// 3-47 내장 콜백을 사용하는 update()로 변경한 deleteAll() 메소드
		this.jdbcTemplate.update("DELETE FROM users");
	}
		
	// 3-49 JdbcTemplate을 이용해 만든 getCount()
	public int getCount() {
		return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
	}
	
	// 3-53 getAll() 메소드
	public List<User> getAll() {
		return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id",this.userMapper);
	}
}
