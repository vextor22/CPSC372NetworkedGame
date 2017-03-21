package cpsc3720.contactserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * Tracks a Game's instances of Empire
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class EmpireTracker {
	
	private int masterId;
	private Map<Integer, Empire> empiresMap;
	private ShipTracker shipTracker;
	
	public EmpireTracker(ShipTracker shipTracker){
		masterId= 0;
		empiresMap = new HashMap<Integer, Empire>();
		this.shipTracker = shipTracker; 
	}
	
	// Ensures that an empire in question does not already exist (i.e. same title), and
	// ensures that the ship ids for the included ships actually exist
	private boolean validateEmpire(Empire empire){
		// Determines if the new name for the empire already exists
		for(int i = 0; i < empiresMap.size(); i++){
			if(empire.getTitle().equals(empiresMap.get(i).getTitle())){
				return false;
			}
		}

		// Determines if the ship ids for the ships are actually valid ships
		if(!shipTracker.validateNewShipList(empire.getShipIds())) return false;
		
		return true;
	}
	
	// Adds a new empire to the tracker
    public int addEmpire(Empire empire) {	
    	if(validateEmpire(empire)){
	    	empire.setIdNum(masterId);
	    	masterId += 1;
	    	empiresMap.put(empire.getIdNum(), empire);
	    	return empire.getIdNum();
    	}else{
    		return -1;
    	}
    }
    
    // Removes an empire if it exists in the list of empires, then returns the new collection of empires
    // NOTE: if the empire to be removed does not exist, returns the collection without making further changes
    public Collection<Empire> deleteEmpire(Empire empire){
    	if(empiresMap.containsKey(empire.getIdNum()))
    		empiresMap.remove(empire.getIdNum());
    	return this.empiresMap.values();
    }
    
    // Updates the desired empire in the list with a new name and a list of available ships
    public Empire updateEmpire(Empire empire){
    	Empire tempEmp = null;
    	if(empire.getIdNum() != -1){
	    	tempEmp = empiresMap.get(empire.getIdNum());
	    	if(tempEmp == null) return null;
	    	tempEmp = empiresMap.get(empire.getIdNum()).update(empire);
    	}
    	return tempEmp;
    }
    
	
	// Returns a collection of current valid empires for use elsewhere
	public Collection<Empire> getEmpiresList(){
		return this.empiresMap.values();
	}
	
	// Clears out all valid empires, and creates 3 default ones
	public boolean reset(){
		empiresMap.clear();
		masterId = 0;
		ArrayList<Integer> newShipIds = new ArrayList<>();
		newShipIds.add(0);
		newShipIds.add(1);
		newShipIds.add(2);
		addEmpire(new Empire("Empire 1", "EM1", newShipIds, false));
		addEmpire(new Empire("Empire 2", "EM2", newShipIds, false));
		addEmpire(new Empire("Empire 3", "EM3", newShipIds, false));
		return true;
	}
}
