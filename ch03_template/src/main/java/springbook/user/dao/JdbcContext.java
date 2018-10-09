package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

// 3-21 JDBC 작업 흐름을 분리해서 만든 JdbcContext 클래스
public class JdbcContext {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) { // DataSource 타입 빈을 DI 받을 수 있게 준비해둔다.
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = this.dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(ps != null) try { ps.close(); } catch(SQLException e) {}
			if(c != null)  try { c.close();  } catch(SQLException e) {}
		}
	}
	
	public void executeSql(final String query) throws SQLException {
		workWithStatementStrategy(
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatement(Connection c)
						throws SQLException {
					return c.prepareStatement(query);
				}
			}
		);
	}
}
