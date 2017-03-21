package cpsc3720.contactserver;
import java.util.HashMap;
import java.util.Map;


/**
 * Universe Holds game state information in a format for display
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Universe {
	
	private Map<String, Sector> universeData;
	//private int friendlyEntityCount = 0;
	//private int enemyEntityCount = 0;
	
	public Universe(){
		
	}

	public Universe(String title) {
		universeData = new HashMap<String, Sector>();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				universeData.put(stringFromInts(i,j), new Sector());
			}
		}
		
	}
	
	public boolean collides(Location loc){
		if(universeData.get(stringFromInts(loc.sectXPos, loc.sectYPos)).cellValue(loc.ptXPos,loc.ptYPos).equals(" "))
				return true;
		else
			return false;
		
	}
	

	private String stringFromInts(int x, int y){
		return String.valueOf(x) + "," + String.valueOf(y);
	}
	
	private Pair numFromString(String string){
		int x = Integer.decode(String.valueOf(string.charAt(0)));
		int y = Integer.decode(String.valueOf(string.charAt(3)));
		return new Pair(x,y);
	}
	
	public Map<String, Sector> getUniverseData() {
		return universeData;
	}

	public int setSector(int x, int y, Ship ship){
		System.out.println("sector: " + x +"," + y);
		assert(ship != null);
		universeData.get(stringFromInts(x,y)).addToSector(ship);
		return 1;
	}
	public int setSector(int x, int y, Base base){
		System.out.println("sector: " + x +"," + y);
		assert(base != null);
		universeData.get(stringFromInts(x,y)).addToSector(base);
		return 1;
	}
	
	public Sector getSector(int x, int y){
		return universeData.get(stringFromInts(x,y));
	}
	
	
	

	
}