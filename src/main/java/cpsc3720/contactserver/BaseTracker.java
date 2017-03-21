package cpsc3720.contactserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Tracks instances of base.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class BaseTracker {

	private int masterId;
	private Map<Integer, Base> basesMap;
	private ShipTracker shipTracker;
	
	public BaseTracker(){
		masterId= 0;
		basesMap = new HashMap<Integer, Base>();
		
	}
	
    public void addBase(Base base) {	
    	if(validate(base)){
    		basesMap.put(masterId, base);
    		masterId+=1;
    	}
    }
    
	public Collection<Base> getBasesList() {
		return basesMap.values();
	}

	
	//eventually this will return a list of all of the weapons to be presented and edited
	public Map<Integer, Base> makeBasesList(){
		return basesMap;	
	}
		
	
	// Validate the date
	private boolean validate(Base base){

		for(int i = 0; i < basesMap.size(); i++){
			if(base.getLocation().isSameLocation(basesMap.get(i).getLocation())){
				return false;
			}	
		}
		return true;
	}
	
	public boolean reset(){	
		basesMap.clear();
		masterId = 0;
		
		addBase(new Base(0,"FED", 2, 4,3, 2));
		addBase(new Base(0,"FED", 2, 5,6, 7));
		addBase(new Base(0,"KLI", 1, 1,2, 1));
	
		return true;
		
	}
}
