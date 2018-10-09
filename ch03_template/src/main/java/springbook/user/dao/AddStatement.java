package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import springbook.user.domain.User;

// 3-13 add() 메소드의 PreparedStatement 생성 로직을 분리한 클래스
public class AddStatement implements StatementStrategy {
	User user;
	
	// 3-14 User 정보를 생성자로부터 제공을 받도록 만든 AddStatement
	public AddStatement(User user) {
		this.user = user;
	}
	
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
