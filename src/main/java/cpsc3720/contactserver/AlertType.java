package cpsc3720.contactserver;
/**
 * Convenience ENUM for determining the alert level of a specific ship.
 * 
 * @author Matthew Higgins
 */
public enum AlertType {
	GREEN, YELLOW, RED;
	
	/**
	 * 
	 * @param in
	 * @return AlertType corresponding to the string brought in. If failed to match, assumed RED alert.
	 */
	public static AlertType fromString(String in){
		if(in.equals("Green"))
			return AlertType.GREEN;
		if(in.equals("Yellow"))
			return AlertType.YELLOW;
		if(in.equals("Red"))
			return AlertType.RED;
		
		return AlertType.RED;
		
	}
	
	
}
