package cpsc3720.contactserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class PlayerTracker {
	
	Map<Integer, Player> playerList;
	private int masterId;
	
	public PlayerTracker(){
		playerList = new HashMap<Integer, Player>();
		masterId = 0;
	}
	
	public void add(Player player){
		playerList.put(masterId, player);
		player.updateNumericId(masterId);
		masterId++;
	}
	
	public Player isUserInGame(User user){
		Player tempPlayer = new Player("TEST", "TEST", 0);
		tempPlayer.updateNumericId(-1);
		
		Collection<Player> tempColl = playerList.values();
		Player[] tempList = tempColl.toArray(new Player[tempColl.size()]);
		for(int i = 0; i < tempList.length; i++){
			if(user.getUserName().toLowerCase().equals(tempList[i].getPlayerId().toLowerCase())) return tempList[i];
		}
		return tempPlayer;
	}
	
	public Map<Integer, Player> getPlayerMap(){
		return playerList;
	}
	


}
