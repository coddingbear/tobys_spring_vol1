package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

/**
 * 5-14 UserService 클래스와 빈 등록
 */
public class UserService {
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	// 5-18 사용자  레벨 업그레이트 메소드
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for(User user : users) {
			Boolean change = null; // 레벨의 변화가 있는지를 확인하는 플래그
			// BASIC 레벨 업그레이드 작업
			if(user.getLevel() == Level.BASIC && user.getLogin() >= 50) { 
				user.setLevel(Level.SILVER);
				change = true;
			}
			// SILVER 레벨 업그레이드
			else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30) { 
				user.setLevel(Level.GOLD);
				change = true; // 레벨 변경 플래그 설정
			}
			// GOLD 레벨은 변경이 일어나지 않는다.
			else if (user.getLevel() == Level.GOLD) { 
				change = false;
			}
			// 일치하는 조건이 없으면 변경 없음
			else { change = false; } 
			if (change) { userDao.update(user); } // 레벨의 변경이 있는 경우에만 update() 호출 
		}
	}
	
	// 5-22 사용자 신규 등록 로직을 담은 add() 메소드
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
