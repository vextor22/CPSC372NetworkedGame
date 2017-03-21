package cpsc3720.contactserver;

/**
 * Attack Data holds the values of a ship that as been attacked
 * uses these values for the attack log 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */


public class AttackData {
	
	Ship attacker;
	Ship hitShip;
	int alertDamage;
	int starDate;
	int isValid; //0 = not a valid place to shoot and decerement count(bases and empty squares); 1 = valid place to shoot decrement count; 2 = trying to shoot self

	public AttackData(){
		
	}
	
	public int isValid() {
		return isValid;
	}

	public void setValid(int isValid) {
		this.isValid = isValid;
	}

	public AttackData(Ship attacker, Ship hitShip, int alertDamage, int starDate, int isValid) {
		super();
		this.attacker = attacker;
		this.hitShip = hitShip;
		this.alertDamage = alertDamage;
		this.starDate = starDate;
		this.isValid = isValid;
	}

	public Ship getAttacker() {
		return attacker;
	}

	public void setAttacker(Ship attacker) {
		this.attacker = attacker;
	}

	public Ship getHitShip() {
		return hitShip;
	}

	public void setHitShip(Ship hitShip) {
		this.hitShip = hitShip;
	}

	public int getAlertDamage() {
		return alertDamage;
	}

	public void setAlertDamage(int alertDamage) {
		this.alertDamage = alertDamage;
	}

	public int getStarDate() {
		return starDate;
	}

	public void setStarDate(int starDate) {
		this.starDate = starDate;
	}


}
