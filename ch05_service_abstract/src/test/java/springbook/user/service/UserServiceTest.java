package springbook.user.service;

import static org.junit.Assert.*;
import static springbook.user.service.UserService.MIN_LOGCOUNT_FOR_SIVER;
import static springbook.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

/**
 * UserServiceTest 테스트 클래스
 * 5-16 UserServiceTest 클래스
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/resources/applicationContext.xml")
public class UserServiceTest {
	@Autowired
	UserService userService;
	
	@Autowired
	UserDao userDao;
	
	List<User> users; // 테스트 픽스처
	
	@Before
	public void setUp() throws Exception {
		users = Arrays.asList( // 배열을 리스트로 만들어주는 편리한 메소드, 배열을 가변인자로 넣어주면 더욱 편리하다.
			new User("bumjin",   "박범진", "p1", Level.BASIC,  MIN_LOGCOUNT_FOR_SIVER-1, 0),
			new User("joytouch", "강명성", "p2", Level.BASIC,  MIN_LOGCOUNT_FOR_SIVER, 0),
			new User("erwins",   "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1),
			new User("madnitel", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
			new User("green",    "오민규", "p5", Level.GOLD,  100, Integer.MAX_VALUE)
		);
	}

	// 5-17 userService 빈의 주입을 확인하는 테스트
	@Test
	public void bean() {
		assertNotNull(this.userService);
	}

	// 5-30 개선한 레벨 업그레이드 테스트
	@Test
	public void upgradeLevels() {
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		userService.upgradeLevels();
		
		// 각 사용자별로 업그레이드 후의 예상 레벨을 검증한다.
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
		
	}
	
	// DB에서 사용자 정보를 가져와 레벨을 확인하는 코드가 중복되므로 헬퍼 메소드로 분리했다.
	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if(upgraded) {
			assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel()); // 업그레이드가 일어났는지 확인
		}
		else { 
			assertEquals(userUpdate.getLevel(), user.getLevel()); // 업그레이드가 일어나지 않았는지 확인
		}
	}
	
	// 5-21 add() 메소드의 테스트
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(4); // GOLD 레벨
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null); // 레벨이 비어 있는 사용자. 로직에 따라 등록 중에 BASIC 레벨도 설정돼야 한다.
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		// DB에 저장된 결과를 가져와 확인한다.
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
		assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
	}
}
