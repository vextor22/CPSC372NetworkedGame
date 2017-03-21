package cpsc3720.contactserver;

/**
 * Collects the ship id, game Id and move target to pass to the server to moce a ship
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */


public class MoveShipParams {
	
	private int pShip;
	private int gameID;
	private Location target;
	
	public MoveShipParams(){}
	
	@Override
	public String toString() {
		return "MoveShipParams [pShip=" + pShip + ", gameID=" + gameID + ", target=" + target + "]";
	}

	public MoveShipParams(int pShip, int gameID, Location target) {
		super();
		this.pShip = pShip;
		this.gameID = gameID;
		this.target = target;
	}
	
	public int getpShip() {
		return pShip;
	}
	public void setpShip(int pShip) {
		this.pShip = pShip;
	}
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public Location getTarget() {
		return target;
	}
	public void setTarget(Location target) {
		this.target = target;
	}
	

}
