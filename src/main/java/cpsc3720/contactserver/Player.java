package cpsc3720.contactserver;

/**
 * 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Player {
	private String playerId;
	private String affiliation;
	private int ship;
	private int id;
	
	public Player(){}
	
	public Player(String ID, String affil, int ship){
		playerId = ID;
		affiliation = affil;
		this.ship = ship;
	}

	void updateNumericId(int newId){
		id = newId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public int getShip() {
		return ship;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Player [playerId=" + playerId + ", affiliation=" + affiliation + ", ship=" + ship + ", id=" + id + "]";
	}
}
