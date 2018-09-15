package springbook.user.dao;

/**
 * 제어의 역전(IoC) - 오브젝트 팩토리
 * 1-14 UserDao의 생성 책임을 맡은 팩토리 클래스
 */
public class DaoFactory {
	public UserDao userDao() {
		// 팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고 어떻게 준비시킬지를 결정한다.
		ConnectionMaker connectionMaker = new DConnectionMaker();
		UserDao userDao = new UserDao(connectionMaker);
		return userDao;
	}
}
