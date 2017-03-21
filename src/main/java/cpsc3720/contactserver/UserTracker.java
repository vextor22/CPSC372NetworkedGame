package cpsc3720.contactserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//userTracker needs List to be able to return as List...
//Go figure!!

/**
 * Keeps track of all the users that are registered
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */
public class UserTracker {


	private int masterId;

	private Map<Integer, User> userMap;
	
	public UserTracker(){
		masterId= 0;
		userMap = new HashMap<Integer, User>();
	}
	
	private boolean valid(User user){
		if(user.getUserName().compareTo("") == 0)
			return false;
		if(user.getEmail().compareTo("") == 0)
			return false;
		if(user.getPassword().compareTo("[C@2f82ba25") == 0)
			return false;
		
		
		return true;
	}
	
    public Collection<User> getUserList() {
		return userMap.values();
	}
    
    public User updateUser(User user){
		if (valid(user)) {
			userMap.get(user.getIdNum()).update(user);
		}else{
			user = new User();
			user.setIdNum(-1);
		}
		
    	return user;
    }



	public int addUser(User user) {	
    	if (valid(user)) {
			user.setIdNum(masterId);
			masterId += 1;
			userMap.put(user.getIdNum(), user);
		}else{
			user = new User();
			user.setIdNum(-1);
		}
		return user.getIdNum();
    }
    	
	//eventually this will return a list of all of the weapons to be presented and edited
	public Map<Integer, User> makeUserList(){
		return userMap;	
	}



	public User deleteUser(User user) {
		userMap.remove(user.getIdNum());
		
		return user;
	}
	
	public User doesUserExist(User user){
		String testUserName = user.getUserName();
		String testPassword = user.getPassword();
		Collection<User> tempColl = this.getUserList();
		User[] tempList = tempColl.toArray(new User[tempColl.size()]);
		for(int i = 0; i < tempList.length; i++){
			if(tempList[i].getUserName().compareTo(testUserName) == 0){
				if(tempList[i].getPassword().toLowerCase().equals(testPassword.toLowerCase())) return tempList[i];
			}
		}
		return user;
	}



	public void reset() {
		userMap.clear();
		masterId = 0;
		addUser(new User(0, "admin","mail","p",true));
		addUser(new User(1, "kirk","mail","p",false));
		addUser(new User(2, "dax","mail","p",false));
		addUser(new User(3, "lee", "mail", "p", false));
		addUser(new User(4, "grant", "mail", "p", false));
	}
}
