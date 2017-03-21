package cpsc3720.contactserver;



/**
 * Holds the user and gameID of a game
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */

public class GameInstanceData {
	@Override
	public String toString() {
		return "GameInstanceData [myUser=" + myUser + ", myGameID=" + myGameID + "]";
	}

	
	private User myUser;
	private int myGameID;
	
	public GameInstanceData(){}
	
	public GameInstanceData(User user, int gameID){
		this.myUser = user;
		this.myGameID = gameID;
	}

	public User getMyUser() {
		return myUser;
	}

	public void setMyUser(User myUser) {
		this.myUser = myUser;
	}

	public int getMyGameID() {
		return myGameID;
	}

	public void setMyGameID(int myGameID) {
		this.myGameID = myGameID;
	}
	
}
