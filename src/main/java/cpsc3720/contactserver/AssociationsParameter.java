package cpsc3720.contactserver;



/**
 * AlertLevelParams is used to collect data from the client in order pass to the server so the alert level cam be changed
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */



public class AssociationsParameter {
	private int myGameID;
	private Player myCurrPlayer;
	private Location myLoc;
	
	public AssociationsParameter(){}
	
	public AssociationsParameter(int gameID, Player currPlayer, Location loc){
		this.myGameID = gameID;
		this.myCurrPlayer = currPlayer;
		this.myLoc = loc;
	}

	public int getMyGameID() {
		return myGameID;
	}

	public void setMyGameID(int myGameID) {
		this.myGameID = myGameID;
	}

	public Player getMyCurrPlayer() {
		return myCurrPlayer;
	}

	public void setMyCurrPlayer(Player myCurrPlayer) {
		this.myCurrPlayer = myCurrPlayer;
	}

	public Location getMyLoc() {
		return myLoc;
	}

	public void setMyLoc(Location myLoc) {
		this.myLoc = myLoc;
	}
}
