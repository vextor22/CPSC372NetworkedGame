package cpsc3720.contactserver;

public class AffiliationAndLocation {
	private String myAffil;
	private Location myLoc;
	
	public AffiliationAndLocation(){}
	
	public AffiliationAndLocation(String affiliation, Location location){
		this.myAffil = affiliation;
		this.myLoc = location;
	}

	public String getMyAffil() {
		return myAffil;
	}

	public void setMyAffil(String myAffil) {
		this.myAffil = myAffil;
	}

	public Location getMyLoc() {
		return myLoc;
	}

	public void setMyLoc(Location myLoc) {
		this.myLoc = myLoc;
	}
	
	
}
