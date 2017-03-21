package cpsc3720.contactserver;

/**
 * Collects the Data find a ship and pass that information between the client and server
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */


public class FindShipParams {
	private int gameID;
	private int shipID;
	
	public FindShipParams(){}
	public FindShipParams(int gameID, int shipID) {
		super();
		this.gameID = gameID;
		this.shipID = shipID;
	}
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public int getShipID() {
		return shipID;
	}
	public void setShipID(int shipID) {
		this.shipID = shipID;
	}
	
	
}
