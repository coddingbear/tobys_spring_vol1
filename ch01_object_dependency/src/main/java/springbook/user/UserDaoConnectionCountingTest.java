package springbook.user;

import java.sql.SQLException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.dao.CountingConnectionMaker;
import springbook.user.dao.CountingDaoFactory;
import springbook.user.dao.UserDao;

public class UserDaoConnectionCountingTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		//
		// DAO 사용코드
		// DL(의존관계 검색)을 사용하면 이름을 이용해서 어떤 빈이든 가져올 수 있다.
		CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
		
		System.out.println("Connection counter: " + ccm.getCounter());

	}

}
