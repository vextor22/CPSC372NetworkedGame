package cpsc3720.contactserver;



/**
 * AISHipMoveParams is used to collect data from the client in order pass to the server so the AIShip can execute and action.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */

public class AIShipMoveParams {

	private int gameID;
	private int shipID;

	public AIShipMoveParams(){}

	public AIShipMoveParams(int shipID, int gameID) {
		super();
		this.shipID = shipID;
		this.gameID = gameID;
	}

	public int getShipId() {
		return shipID; 

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
