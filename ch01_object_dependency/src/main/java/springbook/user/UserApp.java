package springbook.user;

import java.sql.SQLException;
import springbook.user.dao.NUserDao;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

/**
 * UserDao 메인 테스트 
 * @author CodingBear
 */
public class UserApp {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		UserDao dao = new NUserDao(); //
		
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
	}
}
