package cpsc3720.contactserver;

/*
 * There will be multiple types of ships
 */

/**
 * 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Ship {
	//private enum alertType{ GREEN, YELLOW, RED };
	
	private int idNum;
	private ShipType shipType;
	private Location loc;
	private int energyLvl;
	private int missileQty;
	private AlertType alertLvl;
	private int shieldLvl;
	
	/*
	Ship ID	Type	SX	SY	PX	PY	Energy	Missile Qty	Alert	Shield
	0	    STC	    2	4	6	5	2800	   8	     RED	 300
	1	    BOP	    2	5	4	4	1900	   7	     YELLOW	 400
	2	    STM	    2	5	2	2	3000	   2	     YELLOW	 300
	3	    BOP	    3	6	1	5	1500	   1	     RED	 5
	*/

	public Ship(){
		this.idNum = -1;
		this.shipType = new ShipType("test");
		this.loc = new Location();
		this.energyLvl = 0;
		this.missileQty = 0;
		this.alertLvl = AlertType.GREEN;
		this.shieldLvl = 0;
	}
	
	
	public Ship(ShipType shipType, Location location, int energyLevel, int missileQuantity, AlertType alertLevel, int shieldLevel) {
		this.idNum = -1;
		this.shipType = shipType;
		this.loc = location;
		this.energyLvl = energyLevel;
		this.missileQty = missileQuantity;
		this.alertLvl = alertLevel;
		this.shieldLvl = shieldLevel;
	}
	public Ship(int idNum, ShipType shipType, Location location, int energyLevel, int missileQuantity, AlertType alertLevel, int shieldLevel) {
		this.idNum = idNum;
		this.shipType = shipType;
		this.loc = location;
		this.energyLvl = energyLevel;
		this.missileQty = missileQuantity;
		this.alertLvl = alertLevel;
		this.shieldLvl = shieldLevel;
	}
		
	@Override
	public String toString(){

		return "Ship [idNum=" + idNum + ", shipType=" + shipType.getName() + ", energyLvl="
		+ energyLvl + ", missileQty=" + missileQty + ", alertLvl=" + alertLvl + ", shieldLvl=" + shieldLvl + "]";		
		
	}
	
	public String getTitle(){
		return shipType.getName();
	}
	
	
	
	public Location getLoc() {
		return loc;
	}


	public void setLoc(Location loc) {
		this.loc = loc;
	}


	public Ship update(Ship ship){
		this.idNum = idNum;
		this.shipType = ship.getShipType();
		this.loc = ship.getLocation();
		this.energyLvl = ship.getEnergyLvl();
		this.missileQty = ship.getMissileQty();
		this.alertLvl = ship.getAlertLvl();
		this.shieldLvl = ship.getShieldLvl();
		return this;
	}
	
	public int getIdNum() {
		return idNum;
	}

	public ShipType getShipType(){
		return this.shipType;
	}
	
	public Location getLocation(){
		return this.loc;
	}
	
	public int getEnergyLvl(){
		return this.energyLvl;
	}
	
	public int getMissileQty(){
		return this.missileQty;
	}
	
	public AlertType getAlertLvl(){
		return this.alertLvl;
	}
	
	public int getShieldLvl(){
		return this.shieldLvl;
	}
		
	void setIdNum(int idNum){
		this.idNum = idNum;
	}
	
	public void setShipType(ShipType shipType){
		this.shipType = shipType;
	}
	
	public void setLocation(Location location){
		this.loc = location;
	}
	
	public void setEnergyLvl(int energyLevel){
		this.energyLvl = energyLevel;
	}
	
	public void setMissileQty(int missilQuantity){
		this.missileQty = missilQuantity;
	}
	public void decreaseMissleQty(){
		this.missileQty--;
	}
	
	public void setAlertLvl(AlertType alertLevel){
		this.alertLvl = alertLevel;
	}
	
	
	/**
	 * 
	 * returns an object the records how much the ships shield were damaged by an attack 
	 * after checking the shield alert level.
	 * and then records in a boolean value if the ship was destroyed.
	 * 
	 * @param attackingShip
	 * @param weapons
	 * @param theSector
	 * @return
	 */
	public ShipDamage hitShip(Ship attackingShip, WeaponTracker weapons, Sector theSector){
		
		// get the attacking ships Weapon
		Weapon attackingWeapon = weapons.getTheWeapon(attackingShip.getShipType().getMissileWeaponType()); 
		
		int shieldLevelDecrease;
		int oldLevel = shieldLvl;
		ShipDamage thisDamage = new ShipDamage();
		
		// check the alert level
		if(this.alertLvl == AlertType.GREEN){
			shieldLevelDecrease = shieldLvl;
			shieldLvl = 0;
			thisDamage = new ShipDamage(shieldLevelDecrease, true);
			theSector.leaveSector(this);
			
		}
		else if(this.alertLvl == AlertType.YELLOW){
			// 50% of the weapons maximum yield maxYield * .5
			this.shieldLvl = shieldLvl - (attackingWeapon.getYield())/2;
			shieldLevelDecrease = oldLevel - shieldLvl;
			
			if(this.shieldLvl <=0){
				//delete the ship
				thisDamage = new ShipDamage(shieldLevelDecrease, true);
			}
			else{
				thisDamage = new ShipDamage(shieldLevelDecrease, false);
			}
		}else{
			// 20% of the weapons maximum yield: maxYield * .2
			this.shieldLvl = shieldLvl - (attackingWeapon.getYield())/5;
			shieldLevelDecrease =  oldLevel - shieldLvl;
			
			if(this.shieldLvl <=0){
				//delete the ship
				thisDamage = new ShipDamage(shieldLevelDecrease, true);
			}
			else{
				thisDamage = new ShipDamage(shieldLevelDecrease, false);
			}

			
		}
		
		// returns the amount of damage done to the hit ship.
		return thisDamage;

	}
	
	public void setShieldLvl(int shieldLevel){
		this.shieldLvl = shieldLevel;
	}
	
}
