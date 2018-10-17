package springbook.user.dao;

import java.util.List;
import springbook.user.domain.User;

/**
 * UserDao 인터페이스
 */
public interface UserDao {
	public void add(User user);
	public User get(String id);
	public List<User> getAll();
	public void deleteAll();
	public int getCount();
	// 5-11 update() 메소드 추가
	public void update(User user);
}
