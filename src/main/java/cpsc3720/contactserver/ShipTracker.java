package cpsc3720.contactserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Ship Tracker keeps track of a list of all of the ship objects that are created after parsing the .dat file
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 *  
 */
public class ShipTracker {
	
	enum alertType{ GREEN, YELLOW, RED };
	
	private int masterId;
	private Map<Integer, Ship> shipsMap;
	
	//constructor
	public ShipTracker(){
		masterId = 0;
		shipsMap = new HashMap<Integer, Ship>();
	}
	
	// Validates that a new ship can be created
	private boolean validateShip(Ship ship){
		for(int i = 0; i < shipsMap.size(); i++){
			if(ship.getTitle().equals(shipsMap.get(i).getTitle()))
				return true; //BLAH BLAH COME BACK TO THIS?????
		}
			
		return true;
	}
	
	//adding a ship instance and returns the Identifier created
	/**
	 * Adds a ship instance to the list and assigns it a masterId
	 * @param ship
	 * @return
	 */
	public int addShip(Ship ship){
		if( validateShip(ship) == true){
			ship.setIdNum(masterId);
			masterId += 1;
			shipsMap.put(ship.getIdNum(), ship);
			return ship.getIdNum();
		}else return -1;
	}
	
	public Collection<Ship> getShipList(){
		return shipsMap.values();
	}
	
	//will return the entire list of ships
	public Map<Integer, Ship> makeShipList(){
		return shipsMap;
	}
	
	public int deleteShip(Ship ship){
    	shipsMap.remove(ship.getIdNum());
    	return 1;
    }
	
	public Ship getShipByID(int shipID){
		return shipsMap.get(shipID);
	}
	 
	public Ship updateShip(Ship ship){
		return shipsMap.get(ship.getIdNum()).update(ship);
	}
	
	/**
	 * Returns true if the new ship ids are valid, false otherwise
	 * @param newShipIds
	 * @return
	 */
	public boolean validateNewShipList(ArrayList<Integer> newShipIds){
		for(int i = 0; i < newShipIds.size(); i++){
			if(!shipsMap.containsKey(newShipIds.get(i))) return false;
		}
		return true;
	}
	
	//need a way to get the id numbers from the weapons created
	public boolean reset(){
		shipsMap.clear();
		masterId = 0;
		addShip(new Ship(new ShipType("type1"), new Location(), 100, 5, AlertType.GREEN, 50));
		addShip(new Ship(new ShipType("type2"), new Location(), 75, 3, AlertType.YELLOW, 75));
		addShip(new Ship(new ShipType("type3"), new Location(), 90, 6, AlertType.GREEN, 105));
		return true;
	}

}
