package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

/**
 * 5-14 UserService 클래스와 빈 등록
 */
public class UserService {
	// 5-31 상수 도입
	public static final int MIN_LOGCOUNT_FOR_SIVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	// 5-23 기본 작업 흐름만 남겨둔 upgradeLevels()
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for(User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			} 
		}
	}
	
	// 5-24 업그레이드 기능 확인 메소드
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		// 레벨별로 구분해서 조건을 판단한다.
		switch(currentLevel) {
			case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SIVER);
			case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD: return false;
			// 현재 로직에서 다룰 수 없는 레벨이 주어지면 예외를 발생시킨다. 새로운 레벨이 추가되고 로직을 수정하지 않으면 에러가 나서 확인할 수 있다.
			default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}
	
	// 5-25 레벨 업그레이드 작업 메소드
	private void upgradeLevel(User user) {
		/* Level 이늄과  User 클래스에 수정한다.
		if(user.getLevel() == Level.BASIC) 
			user.setLevel(Level.SILVER);
		else if (user.getLevel() == Level.SILVER)
			user.setLevel(Level.GOLD);
		*/
		user.upgradeLevel();
		userDao.update(user);
	}
	
	// 5-22 사용자 신규 등록 로직을 담은 add() 메소드
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
