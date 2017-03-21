package cpsc3720.contactserver;

/**
 * records the damage level of a ship as well as if the ship is destroyed to set it up it be removed 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 *
 */

public class ShipDamage {
	
	private int shipDamage;
	private boolean isDestroyed;
	
	public ShipDamage(){
		
	}
	
	public ShipDamage(int shipDamage, boolean isDestroyed) {
		super();
		this.shipDamage = shipDamage;
		this.isDestroyed = isDestroyed;
	}

	public int getShipDamage() {
		return shipDamage;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

}
