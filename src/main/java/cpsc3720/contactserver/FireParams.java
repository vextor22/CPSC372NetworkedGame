package cpsc3720.contactserver;

/**
 * FireParams is used to collect data from the client in order to excecute fireTorpedo on the server.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */


public class FireParams {
	private Pair selectedPair;
	private int id;
	private int shipID;
	
	
	public FireParams(){}
	
	public FireParams(Pair selected, int id, int shipID){
		selectedPair = selected;
		this.id = id;
		this.shipID = shipID;
	}

	public Pair getSelectedPair() {
		return selectedPair;
	}

	public void setSelectedPair(Pair selectedPair) {
		this.selectedPair = selectedPair;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShipID() {
		return shipID;
	}

	public void setShipID(int shipID) {
		this.shipID = shipID;
	}
	
	
	
	
	
}
