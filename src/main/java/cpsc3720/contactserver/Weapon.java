package cpsc3720.contactserver;
/**
 * A text field will be parsed in order to create objects of weapons
 * each created weapon will pass it's information to 
 * the constructor and fill the fields
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 */
public class Weapon {

	private int idNum;
	private String title;
	private boolean energyType;// true if it is energy, false if it is missile
	private int maxYield;  //damage inflicted
	private String weaponId;
	
	public Weapon(){
		
	}
	@Override
	public String toString() {
		return "Weapon [idNum=" + idNum + ", title=" + title
				+ ", isEnergyType=" + energyType + ", yield=" + maxYield + "]";
	}
	public Weapon(String title, boolean energyType, int yield, String weaponID) {
		super();
		this.title = title;
		this.energyType = energyType;
		this.maxYield = yield;
		this.weaponId = weaponID;
	}
	public Weapon(int idNum, String title, boolean isEnergyType, int yield) {
		super();
		this.idNum = idNum;
		this.title = title;
		this.energyType = isEnergyType;
		this.maxYield = yield;
	}
	
	public int getIdNum() {
		return idNum;
	}
	
	public String getTitle() {
		return title;
	}
	public boolean isEnergyType() {
		return energyType;
	}
	public int getYield() {
		return maxYield;
	}
	
	public void setIdNum(int idNum){
		this.idNum=idNum;	
	}
	
	public String getWeaponId() {
		return weaponId;
	}
	
	public void setWeaponId(String weaponId){
		this.weaponId=weaponId;	
	}
	
	public Weapon update(Weapon weapon){
		this.idNum = weapon.idNum;
		this.title = weapon.title;
		this.energyType = weapon.energyType;
		this.maxYield = weapon.maxYield;
		
		return weapon;
	}
	
	
	
}
