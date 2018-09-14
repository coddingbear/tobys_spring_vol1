package springbook.user.domain;

/**
 * 1-1 사용자 정보 저장용 자바빈 User 클래스
 */
public class User {
	private String id;       // 아이디
	private String name;     // 이름
	private String password; // 패스워드
	
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
