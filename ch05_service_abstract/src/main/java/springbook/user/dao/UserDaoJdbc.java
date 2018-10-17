package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 */
public class UserDaoJdbc implements UserDao{

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// 5-9 추가된 필드를 위한 UserDaoJdbc의 수정 코드
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		}
	};
	
	// 5-9 추가된 필드를 위한 UserDaoJdbc의 수정 코드
	@Override
	public void add(final User user) {
		this.jdbcTemplate.update(
				"INSERT INTO users(id, name, password, level, login, recommend) " +
				"VALUES(?,?,?,?,?,?)", 
				user.getId(), user.getName(), user.getPassword(),
				user.getLevel().intValue(), user.getLogin(), user.getRecommend());
	}
	
	// get() 메소드
	@Override
	public User get(String id) {
		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
			new Object[] {id}, // SQL에 바인딩할 파라미터 값, 가변인자 대신 배열을 사용한다.
			this.userMapper);
	}
	
	// deleteAll() 메소드
	@Override
	public void deleteAll() {
		// 3-47 내장 콜백을 사용하는 update()로 변경한 deleteAll() 메소드
		this.jdbcTemplate.update("DELETE FROM users");
	}
		
	// getCount()
	@Override
	public int getCount() {
		return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
	}
	
	// getAll() 메소드
	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id",this.userMapper);
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(
				"UPDATE users SET name = ?, password = ?, level = ?, login = ?, recommend = ? " +
				"WHERE id = ?", 
				user.getName(), user.getPassword(),	user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
				user.getId());
	}
}
