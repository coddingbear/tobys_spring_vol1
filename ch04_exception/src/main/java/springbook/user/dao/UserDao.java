package springbook.user.dao;

import java.util.List;

import springbook.user.domain.User;

// 4-20 UserDao 인터페이스
public interface UserDao {
	void add(User user);
	User get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
}
