package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

// 1-5 상속을 통한 확장 방법이 제공되는 UserDao
public class NUserDao extends UserDao { 

	@Override // 상속을 통해 확장된 getConnection() 메소드
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		// N 사 DB connection 생성코드
		return null;
	}

}
