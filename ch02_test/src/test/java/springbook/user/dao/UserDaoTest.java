package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

/**
 * 2-15 중복 코드를 제거한 UserDaoTest
 * 2-16 User 픽스처를 적용한 UserDaoTest
 */
public class UserDaoTest {
	private UserDao dao; // setUp() 메소드에서 만드는 오브젝트를 테스트 메소드에서 사용할 수 있도록 인스턴스 변수로 선언한다.
	private User user1;
	private User user2;
	private User user3;
	
	@Before // JUnit이 제공하는 애노테이션 @Test 메소드가 실행되기 전에 먼저 실행되야 하는 메소드를 정의한다.
	public void setUp() {
		ApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		this.dao = context.getBean("userDao", UserDao.class);
		
		this.user1 = new User("gyumee", "박성철", "springno1");
		this.user2 = new User("leegw700", "이길원", "springno2");
		this.user3 = new User("bumjin", "박범진", "springno3");
		
	} // 각 테스트 메소드에 반복적으로 나타났던 코드를 제거하고 별도의 메소드로 옮긴다.

	@Test
	public void addAndGet() throws SQLException { 		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		// 첫 번째 User의 id로 get()을 실행하면 첫 번째 User의 값을 가진 오브젝트를 돌려주는지 확인한다.
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		// 두 번째 User에 대해서도 같은 방법으로 검증한다.
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
	}
	
	// 2-11 getCount() 테스트
	@Test
	public void count() throws SQLException {	
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		
	}
	
	// 2-13 get() 메소드의 예외상황에 대한 테스트
	@Test(expected=EmptyResultDataAccessException.class) // 테스트 중에 발생할 것으로 기대하는 예외 클래스를 지정해 준다.
	public void getUserFailure() throws Exception {
	
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unkown_id"); // 이 메소드 실행 중에 예외가 발생해야 한다. 예외가 발생하지 않으면 테스트가 실패한다.
	}
	
/* JUnit이 하나의 테스트 클래스를 가져와 테스트를 수행하는 방식은 다음과 같다.
  1. 테스트 클래스에서 @Test가 붙은 public이고 void형이며 파라미터가 없는 테스트 메소드를 모드 찾는다.
  2. 테스트 클래스의 오브젝트를 하나 만든다.
  3. @Before가 붙은 메소드가 있으면 실행한다.
  4. @Test가 붙은 메소드를 하나 호출하고 테스트 결과를 저장해둔다.
  5. @After가 붙은 메소드가 있으면 실행한다.
  6. 나머지 테스트 메소드에 대해 2~5번을 반복한다.
  7. 모든 테스트의 결과를 종합해서 돌려준다.
*/	
}
