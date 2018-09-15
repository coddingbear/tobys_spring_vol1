package springbook.user.dao;

/**
 * 제어의 역전(IoC) - 오브젝트 팩토리
 * 1-17 DAO 생성 오브젝트 코드 수정
 */
public class DaoFactory {
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	
	/* 
	public AccountDao accountDao() { 
		return new AccountDao(connectionMaker());
	}
	
	public MessageDao messageDao() {
		return new MessageDao(connectionMaker());
	}
	*/
	
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();  // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
	}
}
