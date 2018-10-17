package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.Level;
import springbook.user.domain.User;

/**
 * 스프링 텍스트 컨텍스트를 적용한 UserDaoTest
 */
@RunWith(SpringJUnit4ClassRunner.class) // 스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장기능 지정
@ContextConfiguration(locations="/applicationContext.xml") // 테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트의 위치 지정
@DirtiesContext // 테스트 메소드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
public class UserDaoTest {
	
	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before // JUnit이 제공하는 애노테이션 @Test 메소드가 실행되기 전에 먼저 실행되야 하는 메소드를 정의한다.
	public void setUp() {
		this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
		this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
		this.user3 = new User("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);
		
	}
	
	// getAll()에 대한 테스트
	@Test
	public void getAll() throws Exception {
		dao.deleteAll();
		
		List<User> users0 = dao.getAll();
		assertThat(users0.size(), is(0));
		
		dao.add(user1); // Id: qyumee
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2); // Id: leegw700
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));	
		checkSameUser(user1, users1.get(0));
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3); // id: bumjin;
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));	
		checkSameUser(user1, users1.get(0));
		checkSameUser(user2, users2.get(1));
		checkSameUser(user2, users3.get(2));
		
		users3.forEach(System.out::println);
	}
	
	// 5-9 checkSameUser() 메소드를 사용하도록 만든 addAndGet() 메소드
	@Test
	public void addAndGet() throws SQLException { 		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		// 첫 번째 User의 id로 get()을 실행하면 첫 번째 User의 값을 가진 오브젝트를 돌려주는지 확인한다.
		User userget1 = dao.get(user1.getId());
		checkSameUser(userget1, user1);
		
		// 두 번째 User에 대해서도 같은 방법으로 검증한다.
		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
	}
	
	// 리스트 5-10 사용자 정보 수정 메소드 테스트
	@Test
	public void update() {
		dao.deleteAll();
		
		dao.add(user1); // 수정할 사용자
		// 5-13 보완된 update() 테스트
		dao.add(user2); // 수정하지 않을 사용자
		
		user1.setName("오민규");
		user1.setPassword("springno6");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		dao.update(user1);
		
		User user1update = dao.get(user1.getId());
		checkSameUser(user1, user1update);
		// 5-13 보완된 update() 테스트
		User user2same = dao.get(user2.getId());
		checkSameUser(user2, user2same);
	}
	
	// getCount() 테스트
	@Test
	public void count() throws SQLException {	
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		assertEquals(dao.getCount(), 1);
		
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		dao.add(user3);
		assertEquals(dao.getCount(), 3);
		
	}
	
	// get() 메소드의 예외상황에 대한 테스트
	@Test(expected=EmptyResultDataAccessException.class) // 테스트 중에 발생할 것으로 기대하는 예외 클래스를 지정해 준다.
	public void getUserFailure() throws Exception {
	
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.get("unkown_id"); // 이 메소드 실행 중에 예외가 발생해야 한다. 예외가 발생하지 않으면 테스트가 실패한다.
	}

	
	// 5-7 새로운 필드를 포함하는 User 필드 값 검증 메소드
	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(),        user2.getId());
		assertEquals(user1.getName(),      user2.getName());
		assertEquals(user1.getPassword(),  user2.getPassword());
		assertEquals(user1.getLevel(),     user2.getLevel());
		assertEquals(user1.getLogin(),     user2.getLogin());
		assertEquals(user1.getRecommend(), user2.getRecommend());
	}
	
	/*
	// DataAccessException에 대한 테스트
	@Test(expected=DataAccessException.class)
	public void duplicateKey() {
		dao.deleteAll();
		
		dao.add(user1);
		dao.add(user1); // 강제로 같은 사용자를 두 번 등록한다. 여기서 예외가 발생해야 한다.
	}
	
	// SQLException 전환 기능의 학습 테스트
	@Test
	public void sqlExceptionTranslate() {
		dao.deleteAll();
		
		try {
			dao.add(user1);
			dao.add(user1);
		}
		catch(DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException) ex.getRootCause();
			SQLExceptionTranslator set =  // 코드를 이용한 SQLException 전환
					new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
			assertEquals(set.translate(null, null, sqlEx), DuplicateKeyException.class);
		}
	}
	*/
}
