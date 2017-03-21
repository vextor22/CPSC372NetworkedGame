package cpsc3720.contactserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Sector represents the set of Cells in a single sector of the universe model.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Sector {
	
	//private ArrayList<Sector> sectorList;
	private Map<String, Cell> theSector;
	
	public Sector(){
		theSector = new HashMap<String, Cell>();
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				theSector.put(stringFromInts(i,j), new Cell());
			}
		}
	}

	private String stringFromInts(int x, int y){
		return String.valueOf(x) + "," + String.valueOf(y);
	}
	
	/**
	 * inserts an obj, either ship, base or whatever to a cell in the sector it is assigned to
	 * @param obj
	 * @return
	 */
	public int addToSector(Object obj){
		assert(obj != null);
		if(obj instanceof Ship){
			//Ship ship = new Ship(0,((Ship) obj).getShipType(), ((Ship) obj).getLocation(), ((Ship) obj).getEnergyLvl(), ((Ship) obj).getMissileQty(), ((Ship) obj).getAlertLvl(), ((Ship) obj).getShieldLvl());
			theSector.get(stringFromInts(((Ship) obj).getLocation().getPXPos(),((Ship) obj).getLocation().getPYPos())).setShip((Ship)obj);
		}else if(obj instanceof Base){
			//Base base = new Base(((Base) obj).getBaseId(), ((Base) obj).getEmpireId(), ((Base) obj).getLocation());
			theSector.get(stringFromInts(((Base) obj).getLocation().getPXPos(),((Base) obj).getLocation().getPYPos())).setBase((Base)obj);
		}
		return 1;
	}

	public int addToSector(String xy, String affil){
		
		theSector.get(xy).setShip(affil);
		System.out.println("Ship affiliation set in addToSector");
		return 1;
	}
	
	
	public Map<String, Cell> getTheSector() {
		return theSector;
	}

	@Override
	public String toString() {
		return "Sector [theSector=" + theSector + "]";
	}

	/**
	 * takes in the object(Ship or base) and replaces that cell with an empty cell
	 * @param obj
	 */
	public void leaveSector(Object obj){
		if(obj instanceof Ship){
			theSector.get(stringFromInts(((Ship) obj).getLocation().getPXPos(),((Ship) obj).getLocation().getPYPos())).leave();
		}
		else if(obj instanceof Base){
			theSector.get(stringFromInts(((Base) obj).getLocation().getPXPos(),((Base) obj).getLocation().getPYPos())).leave();
		}	
	}
	
	/**
	 * Checks the cells contents and returns a string to represent a ship or base
	 * @param x
	 * @param y
	 * @return "A" if the cell contains a ship, "B" if it is a base
	 */
	public String cellValue(int x, int y){ // returns a string the describes the object at the give sector coordinates
		System.out.println(stringFromInts(x,y));
		TypeEnum type =  theSector.get(stringFromInts(x,y)).getType();
		if(type == TypeEnum.SHIP){

			return "S";
		}else if(type == TypeEnum.BASE){

			return "B";
		}else{
			return " ";
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return a ship object if the cell contains a ship
	 * @throws NoObjectInCell
	 */
	
	public Ship getCellShip(int x, int y) throws NoObjectInCell{  // returns the ship object in the cell given by x,y
		TypeEnum type =  theSector.get(stringFromInts(x,y)).getType();
		if(type == TypeEnum.SHIP){
			return theSector.get(stringFromInts(x,y)).getShip();
		}else if(type == TypeEnum.BASE){
			throw new NoObjectInCell();
		}else{
			throw new NoObjectInCell();
		}
	}
	
	/**
	 * checks the cell given by x,y to see if it is a foe
	 * @param x
	 * @param y
	 * @param aiShip
	 * @return true is it is a foe and flase is it is not a foe
	 */
	public boolean isFoe(int x, int y, Ship aiShip){
		boolean foe = false;
		Ship check;
		TypeEnum type =  theSector.get(stringFromInts(x,y)).getType();
		if(type == TypeEnum.SHIP){
			check = theSector.get(stringFromInts(x,y)).getShip();
			if(!(check.getShipType().getEmpireId().equals(aiShip.getShipType().getEmpireId()))){
				return true; // return true if the ship type does not equal the aiShips type
			}
		}else if(type == TypeEnum.BASE){
			return false;
		}else{
			return false;
		}
		
		
		
		return foe;
	}
		
	
	/**
	 * Reviews every cell in each sector to get a count of friendly and foe objects in the entire cell	
	 * 
	 * @param player
	 * @return a string that represents the amount of foes and friendly objects in the sector
	 */
	public String sectorSummary(Player player){
		int friend = 0;
		int foe = 0;
		if(player != null){
			String empId = player.getAffiliation();
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					TypeEnum type = theSector.get(stringFromInts(i,j)).getType();
					if(type == TypeEnum.SHIP){
						Ship check = theSector.get(stringFromInts(i,j)).getShip();
						if(((Ship) check).getShipType().getEmpireId().equals(empId)){
							friend+=1;
						}else{
							foe+=1;
						}
					}else if(type == TypeEnum.BASE){
						Base check = theSector.get(stringFromInts(i,j)).getBase();
						if(((Base) check).getEmpireId().equals(empId)){
							friend+=1;
						}else{
							foe+=1;
						}
					}
				}
			}
		}
		
		return Integer.toString(friend) + " , " + Integer.toString(foe); // returns string value presented in universe view
	}
	
	//Will not get used until we move objects on the game board around
	/**
	 * Has an object leave a sector and jump to another sector 
	 * @param obj
	 * @param moveTo
	 */
	public void moveCell(Object obj, Location moveTo){
		leaveSector(obj);
		
		if(obj instanceof Ship){
			((Ship) obj).setLocation(moveTo);
			addToSector(obj);
		}else if(obj instanceof Base){
			((Base) obj).setLocation(moveTo);
			addToSector(obj);
		}
		
		
	}

}