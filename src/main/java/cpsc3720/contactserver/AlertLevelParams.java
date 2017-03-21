package cpsc3720.contactserver;

import retrofit.http.Body;


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
public class AlertLevelParams {
	private String alertLvl;
	private int id;
	private int shipID;
	
	public AlertLevelParams() {}
	
	public AlertLevelParams(String alertLvl, int id, int shipID) {
		super();
		this.alertLvl = alertLvl;
		this.id = id;
		this.shipID = shipID;
	}
	public String getAlertLvl() {
		return alertLvl;
	}
	public void setAlertLvl(String alertLvl) {
		this.alertLvl = alertLvl;
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
