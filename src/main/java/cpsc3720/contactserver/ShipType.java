package cpsc3720.contactserver;

/**
 * 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class ShipType {
	
	// follows the list of fields give by the .dat files
	private String shipTypeId;// Short Id or the ship type
	private String name; //the name of the ship
	private String shipClass; 
	private String empireId;
	
	private int maxEnergy; // maximum ENergy of the ship
	private int maxSpeed; // maximum speed of the ship
	private int maxShields; // maximum Shield Level
	
	private String energyWeaponType; // type of energy weapon the ship receives
	private String missileWeaponType; // type of missile weapon the ship receives
	private int maxMissile; // max amount of missiles the ship will have
	
	
	public ShipType(String name){
		this.shipTypeId = null;
		this.name = name; //the name of the ship
		this.shipClass = null;
		this.empireId = null;
		
		this.maxEnergy = 0;
		this.maxSpeed = 0;
		this.maxShields = 0;
		
		this.energyWeaponType = null;
		this.missileWeaponType = null;
		this.maxMissile = 0;
		
	}
	
	public ShipType(String id, String name, String shipClass, String empId, int maxE, int maxSp, int maxSH, String enWepType, String misWepType, int maxMis){
		
		this.shipTypeId = id;
		this.name = name; //the name of the ship
		this.shipClass = shipClass;
		this.empireId = empId;
		
		this.maxEnergy = maxE;
		this.maxSpeed = maxSp;
		this.maxShields = maxSH;
		
		this.energyWeaponType = enWepType;
		this.missileWeaponType = misWepType;
		this.maxMissile = maxMis;
		
	}
	
	

	public String getShipTypeId() {
		return shipTypeId;
	}


	public String getName() {
		return name;
	}


	public String getShipClass() {
		return shipClass;
	}


	public String getEmpireId() {
		return empireId;
	}


	public int getMaxEnergy() {
		return maxEnergy;
	}


	public int getMaxSpeed() {
		return maxSpeed;
	}


	public int getMaxShields() {
		return maxShields;
	}


	public String getEnergyWeaponType() {
		return energyWeaponType;
	}


	public String getMissileWeaponType() {
		return missileWeaponType;
	}


	public int getMaxMissile() {
		return maxMissile;
	}
	
	
}
