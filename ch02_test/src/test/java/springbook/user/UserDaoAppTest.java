package springbook.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

/**
 * 2-4 JUnit 프레임워크에서 동작할 수 있는 테스트 메소드로 전환
 * 2-5 JUnit을 적용한 UserDaoTest
 */
public class UserDaoAppTest {

	// 2-6 JUnit를 이용해 테스트를 실행해주는 Main() 메소드
	public static void main(String[] args) {
		JUnitCore.main("springbook.user.UserDaoAppTest");
	}
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException { // JUnit 테스트 메소드는 반드시 public으로 선언해야 한다.
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user = new User();
		user.setId("gyumee");  // JUnit 테스트로 전환하는 김에 새로운 기분으로 테스트 데이터도 바꿔 봄.
		user.setName("박성철");
		user.setPassword("springno1");
		
		dao.add(user);
		
		User user2 = dao.get(user.getId());
		
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
}
