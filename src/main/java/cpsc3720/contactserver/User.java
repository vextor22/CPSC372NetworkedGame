package cpsc3720.contactserver;
/**
 * User Holds credentials for signing in
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class User {
	private int idNum;
	private String userName;
	private String email;
	private String password;
	private String affiliation;
	private boolean admin;

	public User() {
		
	}
	
	
	
	public User(int idNum, String userName, String email, String password,boolean isAdmin){
		this.idNum = idNum; // have server change is after the object is created
		this.userName = userName;

		this.email = email;
		this.password = password;
		this.admin = isAdmin;

	}
	public User update(User user){
		
		userName = user.userName;
		email = user.email;
		password = user.password;
		admin = user.admin;
		
		return user;
	}
	
	public int getIdNum() {
		return idNum;
	}
	
	void setIdNum(int idNum){
		this.idNum = idNum;
	}
	public String getUserName() {
		return userName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public boolean getAdmin() {
		return admin;
	}

	@Override
	public String toString() {
		return "User [idNum=" + idNum + ", userName=" + userName + ", email=" + email+", password=" + password + ", isAdmin=" + admin + "]";
	}
}
