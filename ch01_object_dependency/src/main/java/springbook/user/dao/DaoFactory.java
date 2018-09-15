package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링의 IoC - 오브젝트 팩토리를 이용한 스프링 IoC
 * DaoFactory를 사용하는 애플리케이션 컨텍스트
 * 의존관계 주입 응용 - 기능 구현의 교환
 * 1-31 CountingConnectionMaker 의존관계가 추가된 DI 설정용 클래스
 */
@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
	
	@Bean // 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
	public UserDao userDao() {
		return new UserDao(connectionMaker()); // 모든 DAO는 여전히 connectionMaker()에서 만들어지는 오브젝트 DI를 받는다.
	}
	
	
	// 1-28 개발용 ConnectionMaker 생성코드
	@Bean
	public ConnectionMaker connectionMaker() {
		return new LocalDBConnectionMaker();
	}
	/*
	// 1-29 운영용 ConnectionMaker 생성 코드
	@Bean
	public ConnectionMaker connectionMaker() {
		return new ProductionDBConnectionMaker();
	}
	*/
}
