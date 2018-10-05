package springbook.user.domain;

/**
 * 2-10 파라미터가 있는 User 클래스 생성자
 */
public class User {
	private String id;       // 아이디
	private String name;     // 이름
	private String password; // 패스워드
	
	
	public User(String id, String name, String password) {	
		this.id = id;
		this.name = name;
		this.password = password;
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
}
