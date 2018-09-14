package springbook.user;

import java.sql.SQLException;

import springbook.user.dao.ConnectionMaker;
import springbook.user.dao.DConnectionMaker;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

/**
 * UserDao 클라이언트 메인 테스트 
 * @author CodingBear
 */
public class UserApp {
	// 1-12 관계설정 책임이 추가된 UserDao 클라이언트인 main() 메소드
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// UserDao가 사용할 ConnectionMaaker 구현 클래스를 결정하고 오브젝트를 만든다.
		ConnectionMaker connectionMaker = new DConnectionMaker();  
		
		UserDao dao = new UserDao(connectionMaker); 
		// 1. UserDao 생성
		// 2. 사용할 ConnectionMaker 타입의 오브젝트 제공. -> 결국 두 오브첵트 사이의 의존관계 설정 효과
		
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
