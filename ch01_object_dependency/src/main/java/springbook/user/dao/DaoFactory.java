package springbook.user.dao;

/**
 * 제어의 역전(IoC) - 오브젝트 팩토리
 * 1-16 DAO 생성 메소드의 추가로 인해 발생하는 중복
 */
public class DaoFactory {
	public UserDao userDao() {
		return new UserDao(new DConnectionMaker());
	}
	
	/* ConnectionMaker 구현 클래스를 선정하고 생성하는 코드의 중복
	public AccountDao accountDao() { 
		return new AccountDao(new DConnectionMaker());
	}
	
	public MessageDao messageDao() {
		return new MessageDao(new DConnectionMaker());
	}
	*/
}
