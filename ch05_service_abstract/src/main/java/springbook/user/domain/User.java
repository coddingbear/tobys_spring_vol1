package springbook.user.domain;

/**
 * 사용자 클래스
 */
public class User {
	private String id;       // 아이디
	private String name;     // 이름
	private String password; // 패스워드
	
	//5-4 User 에 추가된 필드
	private Level level;   // 사용자 레벨
	private int login;     // 로그인 횟수
	private int recommend; // 추천수
	
	public User(String id, String name, String password) {	
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	// 5-6 추가된 필드를 파라미터로 포함하는 생성자
	public User(String id, String name, String password, Level level, int login, int recommend) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
	}

	public User() {
		// 자바빈의 규약을 따르는 클래스에 생성자를 명시적으로 추가했을 때는
		// 파라미터가 없는 디폴트 생성자도 함께 정의해 주는 것을 잊지 말자. 
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//5-4 User 에 추가된 필드에 따른 getter/setter 메소드
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	// 5-27 User의 레벨 업그레이드 작업용 메소드
	public void upgradeLevel() {
		Level nextLevel = this.level.nextLevel();
		if (nextLevel == null) {
			throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다.");
		}
		else {
			this.level = nextLevel;
		}
	}
}
