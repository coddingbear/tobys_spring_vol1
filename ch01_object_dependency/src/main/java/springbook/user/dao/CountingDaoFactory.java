package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 의존관계 주입 응용
 * 1-31 CountingConnectionMaker 의존관계가 추가된 DI 설정용 클래스
 */
@Configuration
public class CountingDaoFactory {
	
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker()); // 모든 DAO는 여전히 connectionMaker()에서 만들어지는 오브젝트 DI를 받는다.
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}
	
	@Bean
	public ConnectionMaker realConnectionMaker() {
		return new DConnectionMaker();
	}
}
