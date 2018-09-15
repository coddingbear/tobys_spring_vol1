package springbook.user;

import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

/**
 * UserDao 클라이언트 메인 테스트 
 * @author CodingBear
 * 
 * 1-19 애플리케이션 컨텍스트를 적용한 UserApp(UserDaoTest)
 */
public class UserApp {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		ApplicationContext context = 
				new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		// 1-20 직접 생성한 DaoFactory 오브젝트 출력 코드
		DaoFactory factory = new DaoFactory();
		UserDao dao1 = factory.userDao();
		UserDao dao2 = factory.userDao();
		// userDao를 매번 호출하면 새로운 오브젝트가 생성도니다.
		System.out.println(dao1);
		System.out.println(dao2);
		
		// 1-21 스프링 컨텍스트로부터 가져온 오브젝트 출력 코드
		UserDao dao3 = context.getBean("userDao", UserDao.class);
		UserDao dao4 = context.getBean("userDao", UserDao.class);
		// 스프링은 여러번에 걸쳐 빈을 요청하더라도 매번 동일한 오프젝트를 반환한다.
		System.out.println(dao3);
		System.out.println(dao4);
		
		/*
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + "조회 성공");
		*/
		
	}
}
