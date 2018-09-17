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
 * 2-1 main() 메소드로 작성된 테스트
 */
public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		ApplicationContext context = 
				new GenericXmlApplicationContext("classpath:applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		// 2-2 수정전 테스트
//		System.out.println(user2.getName());
//		System.out.println(user2.getPassword());		
//		System.out.println(user2.getId() + "조회 성공");
		
		// 2-3 수정 후 테스트 코드
		if(!user.getName().equals(user2.getName())) {
			System.out.println("테스트 실패 (name)");
		}
		else if (!user.getPassword().equals(user2.getPassword()) ) {
			System.out.println("테스트 실패 (password)");
		}
		else {
			System.out.println("조회 테스트 선공");
		}
	}
}
