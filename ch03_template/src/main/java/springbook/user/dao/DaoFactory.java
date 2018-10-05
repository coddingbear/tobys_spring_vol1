package springbook.user.dao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * 스프링의 IoC - 오브젝트 팩토리를 이용한 스프링 IoC
 * 
 * 1-43 DataSource 타입의 dataSource 빈 정의 메소드
 */
@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/springbook");
		dataSource.setUsername("springuser");
		dataSource.setPassword("springpass"); 
		// DB 연결 정보를 수정자 메소드를 통해 넣어준다. 이렇게 하면 오브젝트 레벨에서 DB 연결 방식을 변경할 수 있다.
		
		return dataSource;
	}
	
	// 1-44 DataSource 타입의 빈을 DI받는 userDao() 빈 정의 메소드
	@Bean 
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
}
