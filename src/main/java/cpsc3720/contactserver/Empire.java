package cpsc3720.contactserver;

import java.util.ArrayList;
/**
 * Empire represents and empire instance in the Game.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Empire {
	
	private int idNum;
	private String title;
	private ArrayList<Integer> shipIds = new ArrayList<>(); // a list of ship ID numbers that are associated with a particular empire
	private boolean isAgressive;
	private String strId;
	
	/**
	 * An empire will have a specific id number and name or title.
	 * Each empire will also have a list of associated ship numbers that 
	 * any user in the empire can use.
	 */
	public Empire(){
		this.idNum = -1;
		this.title = "Test Empire";
		ArrayList<Integer> temp = new ArrayList<>();
		this.shipIds = temp;
		this.isAgressive = false;
		this.strId = "TEM";
	}
	
	public Empire(String title){
		this.idNum = -1;
		this.title = title;
		ArrayList<Integer> temp = new ArrayList<>();
		this.shipIds = temp;
		this.isAgressive = false;
		this.strId = "TEM";
	}
	
	public Empire(String title, String empireId, boolean isAgreesive){
		this.idNum = -1;
		this.title = title;
		ArrayList<Integer> temp = new ArrayList<>();
		this.shipIds = temp;
		this.isAgressive = isAgressive;
		this.strId = empireId;
	}
	
	
	public Empire(int idNum, String title, String empireId, ArrayList<Integer> shipIds, boolean isAgressive){
		this.idNum = idNum;
		this.title = title;
		this.shipIds = shipIds;
		this.isAgressive = isAgressive;
		this.strId = empireId;
	}
	
	public Empire(String title, String empireId, ArrayList<Integer> shipIds, boolean isAgressive) {
		this.idNum = -1;
		this.title = title;
		this.shipIds = shipIds;
		this.isAgressive = isAgressive;
		this.strId = empireId;
	}
	
	@Override
	public String toString() {
		String isAgr = "";
		if(this.isAgressive) isAgr = "Agressive";
		else isAgr = "Peaceful";
		
		return "Empire [idNum=" + idNum + ", title=" + title + ", shipIds="
				+ shipIds + " AgressionType=" + isAgr + " strId=" + strId + "]";
	}
	
	// Sets the title to the new title, and updates the ship id list
	public Empire update(Empire empire){
		this.title = empire.getTitle();
		this.isAgressive = empire.getAgressionType();
		this.strId = empire.getEmpireId();
		// Do I need to ensure that there is at least 1 ship in the list???
		this.setShipIds(empire.getShipIds());
		return this;
	}

	public int getIdNum() {
		return idNum;
	}
	
	public void setIdNum(int idNum){
		this.idNum=idNum;	
	}
	
	public String getEmpireId(){
		return this.strId;
	}
	
	public void setEmpireId(String empireId){
		this.strId = empireId;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	public boolean getAgressionType(){
		return this.isAgressive;
	}
	
	public void setAgressionType(boolean isAgressive){
		this.isAgressive = isAgressive;
	}

	public ArrayList<Integer> getShipIds() {
		return shipIds;
	}
	
	// Updates ship list, and ensures that no duplicate ships are added
	private void setShipIds(ArrayList<Integer> newShipIds){
		this.shipIds.clear();
		for(int i = 0; i < newShipIds.size(); i++){
			boolean hasDupe = false;
			for(int j = 0; j < shipIds.size(); j++){
				if(shipIds.get(j) == newShipIds.get(i)) hasDupe = true;
			}
			if(!hasDupe) this.shipIds.add(newShipIds.get(i));
		}
	}
}
