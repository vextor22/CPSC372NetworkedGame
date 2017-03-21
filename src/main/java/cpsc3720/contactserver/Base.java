package cpsc3720.contactserver;

/**
 * Base represents an in game base. Currently only functionality is to exist.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Base {
	 private int baseId;
	 private String empireId;
	 private Location baseLocation;
	 
	 public Base(int baseId, String empId,int sx, int sy, int px, int py){
		 this.baseId = baseId;
		 this.empireId = empId;
		 Location baseLoc = new Location(sx,sy,px,py);
		 this.baseLocation = baseLoc;
	 }
	 public Base(int baseId, String empId,Location newLoc){
		 this.baseId = baseId;
		 this.empireId = empId;
		 Location baseLoc = newLoc;
		 this.baseLocation = baseLoc;
	 }
	 
	 public int getBaseId(){
		 return baseId;
	 }
	 
	 public Location getLocation(){
		 return baseLocation;
	 }
	 public void setLocation(Location loc){
			this.baseLocation = loc;
	 }
	
	 public String getEmpireId(){
		 return empireId;
	 }

}
