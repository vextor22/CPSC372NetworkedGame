package cpsc3720.contactserver;

/**
 * Attack Log object stores a string to present in the attack log string 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */

public class AttackLogObject {
	
	String attackLogString = "string not set";

	AttackLogObject() {}
	
	public String getAttackLogString() {
		return attackLogString;
	}

	public void setAttackLogString(String attackLogString) {
		this.attackLogString = attackLogString;
	}

	public AttackLogObject(String attackLogString) {
		super();
		this.attackLogString = attackLogString;
	}

	@Override
	public String toString() {
		return attackLogString;
	}
	
	

}
