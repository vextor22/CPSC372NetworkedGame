package cpsc3720.contactserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class ShipTypeTracker {

	Map<Integer,ShipType> shipTypeMap;
	Map<String,ShipType> usefulMap;
	private int idNum;
	public ShipTypeTracker(){
		shipTypeMap = new HashMap<Integer, ShipType>();
		usefulMap = new HashMap<String, ShipType>();
		idNum = 0;
	}
	
    public void addShipType(ShipType type) {	
    	if(validate(type)){
    		shipTypeMap.put(idNum, type);
    		usefulMap.put(type.getShipTypeId(), type);
    		idNum+=1;
    	}
    }
    
    public ShipType getType(String typeID){
    	return usefulMap.get(typeID);
    }
    
	public Collection<ShipType> getShipTypeList() {
		return shipTypeMap.values();
	}

	
	//eventually this will return a list of all of the weapons to be presented and edited
	public Map<Integer, ShipType> makeShipTypeList(){
		return shipTypeMap;	
	}
		
	// Validate the date
	private boolean validate(ShipType type){

		for(int i = 0; i < shipTypeMap.size(); i++){
			if(type.getShipTypeId().equals(shipTypeMap.get(i).getShipTypeId())){
				return false;
			}	
		}
		return true;
	}
	
	public boolean reset(){	
		shipTypeMap.clear();
		idNum = 0;
		addShipType(new ShipType("Type1"));
		addShipType(new ShipType("Type2"));
		addShipType(new ShipType("Type3"));
	
		return true;
		
	}
}

