package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링의 IoC - 오브젝트 팩토리를 이용한 스프링 IoC
 * DaoFactory를 사용하는 애플리케이션 컨텍스트
 * 
 * 1-18 스프링 빈 팩토리가 사용할 설정정보를 담은 DaoFactory 클래스
 */
@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
	
	@Bean // 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
	public UserDao userDao() {
		//return new UserDao(connectionMaker());
		return new UserDao(connectionMaker()); // 싱글톤일때 인스턴스 생성 메소드 사용
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
