package cpsc3720.contactserver;
/**
 * This class is used so that objects know where they are both in the sector and universe.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Location {
	public int sectXPos;
	public int sectYPos;
	public int ptXPos;
	public int ptYPos;
	
	public Location(){

	}
	
	@Override
	public String toString() {
		return "Location [sectXPos=" + sectXPos + ", sectYPos=" + sectYPos + ", ptXPos=" + ptXPos + ", ptYPos=" + ptYPos
				+ "]";
	}

	public Location(int sx, int sy, int px, int py){
		this.sectXPos = sx;
		this.sectYPos = sy;
		this.ptXPos = px;
		this.ptYPos = py;
	}
	
	public int getSXPos(){
		return this.sectXPos;
	}
	
	public int getSYPos(){
		return this.sectYPos;
	}
	
	public int getPXPos(){
		return this.ptXPos;
	}
	
	public int getPYPos(){
		return this.ptYPos;
	}
	
	public void setSXPos(int sx){
		this.sectXPos = sx;
	}
	
	public void setSYPos(int sy){
		this.sectYPos = sy;
	}
	
	public void setPXPos(int px){
		this.ptXPos = px;
	}
	
	public void setPYPos(int py){
		this.ptYPos = py;
	}
	
	
	//takes in a location and checks if it is the same as the current location
	public boolean isSameLocation(Location check){
		if(this.ptXPos == check.ptXPos && this.ptYPos == check.ptYPos && this.sectXPos == check.sectXPos && this.sectYPos == check.sectYPos){
			return true;
		}
		return false;
	}
	
}
