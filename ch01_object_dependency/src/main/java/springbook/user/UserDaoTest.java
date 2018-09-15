package springbook.user;

import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

/**
 * UserDao 클라이언트 메인 테스트 
 * @author CodingBear
 * 
 * 1-19 애플리케이션 컨텍스트를 적용한 UserApp(UserDaoTest)
 */
public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		/*ApplicationContext context = 
				new AnnotationConfigApplicationContext(DaoFactory.class);*/
		// XML을 이용한 애플리케이션 컨텍스트
		ApplicationContext context = 
				new GenericXmlApplicationContext("classpath:applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("whiteship12");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + "조회 성공");
		
	}
}
