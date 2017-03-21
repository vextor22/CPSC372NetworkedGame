package cpsc3720.contactserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Weapon tracker keeps 2 lists the hold the weapon objects created when parsing a .dat file
 * each map is used to access the weapon data in different ways
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */

public class WeaponTracker {


	private int masterId;
	private Map<Integer, Weapon> weaponsMap;
	private Map<String, Weapon> getWeaponMap;
	private ShipTracker shipTracker;
	
	public WeaponTracker(ShipTracker shipTracker){
		masterId= 0;
		weaponsMap = new HashMap<Integer, Weapon>();
		getWeaponMap = new HashMap<String, Weapon>();
		this.shipTracker = shipTracker;
	}
	
	/**
	 * adds a Weapon after validating it.
	 * @param weapon
	 * @return the id number of the added weapon
	 */
    public int addWeapon(Weapon weapon) {	
    	if(validate(weapon)){
    		weapon.setIdNum(masterId);
    		masterId+=1;
    		weaponsMap.put(weapon.getIdNum(), weapon);
    		getWeaponMap.put(weapon.getWeaponId(), weapon);
    		return weapon.getIdNum();
    	}
    	else{
    		return -1;
    	}
    }
    
	public Collection<Weapon> getWeaponsList() {
		return weaponsMap.values();
	}
	
	/**
	 * used for getting a certain weapon
	 * @param weaponID
	 * @return returns a specific weapon you are searching for
	 */
	public Weapon getTheWeapon(String weaponID) {
		return getWeaponMap.get(weaponID);
	}

	public Weapon updateWeapon(Weapon weapon){
		if(validate(weapon)){
			weaponsMap.get(weapon.update(weapon));
			getWeaponMap.get(weapon.update(weapon));
			return weapon;
		}
		else{
			Weapon errorWeapon = new Weapon(null, false, 0, null);
			errorWeapon.setIdNum(-1);
			return errorWeapon;
		}
	}

	/**
	 * returns the map of weapons
	 * @return
	 */
	public Map<Integer, Weapon> makeWeaponsList(){
		return weaponsMap;	
	}
		
	/**
	 * searches list of ships to check if any ship has that weapon
	 * then deletes the weapon if it is not connected to the ship
	 * @param weapon to be deleted
	 * @return the deleted Weapon
	 */
	public Weapon deleteWeapon(Weapon weapon) {
		
		Collection<Ship> ships = shipTracker.makeShipList().values();
	
		Iterator<Ship> shipIterator = ships.iterator();
		Ship toSearch = new Ship();
		for(;shipIterator.hasNext(); toSearch = shipIterator.next()){	
			if(/*toSearch.getEnergyWeaponId() == weapon.getIdNum() || toSearch.getMissileWeaponId() == weapon.getIdNum()*/ false){
				
				Weapon errorWeapon = new Weapon(null, false, 0, null);
				errorWeapon.setIdNum(-1);
				return errorWeapon; 
			}
		}
		weaponsMap.remove(weapon.getIdNum());
		return weapon;
	}
	

	/**
	 *  Validate the date by checking if there are any weapons with the same name, and if the yield is greater than 0
	 * @param weapon the weapon to check
	 * @return true if the ship can be added, false if it can not
	 */
	private boolean validate(Weapon weapon){

		for(int i = 0; i < weaponsMap.size(); i++){

			if(weapon.getTitle().equals(weaponsMap.get(i).getTitle())){
				return false;
			}	
		if(weapon.getYield()<0){
				return false;
			}
		}
		return true;
	}
	
	public boolean reset(){	
		weaponsMap.clear();
		masterId = 0;
		addWeapon(new Weapon("EnergyWeapon1",true, 5,  "weapon Id 1"));
		addWeapon(new Weapon("EnergyWeapon2",true, 6,  "weapon Id 2"));
		addWeapon(new Weapon("EnergyWeapon3",false, 7, "weapon id 3"));
	
		return true;
		
	}
}
