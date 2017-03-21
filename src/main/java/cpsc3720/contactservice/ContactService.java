package cpsc3720.contactservice;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cpsc3720.contactclient.SectorTableModel;
import cpsc3720.contactclient.UniverseTableModel;
import cpsc3720.contactserver.AIShipMoveParams;
import cpsc3720.contactserver.AlertLevelParams;
import cpsc3720.contactserver.AssociationsParameter;
import cpsc3720.contactserver.AttackData;
import cpsc3720.contactserver.AttackLogObject;
import cpsc3720.contactserver.Empire;
import cpsc3720.contactserver.FindShipParams;
import cpsc3720.contactserver.FireParams;
import cpsc3720.contactserver.Game;
import cpsc3720.contactserver.GameInstanceData;
import cpsc3720.contactserver.GameNotFoundException;

import cpsc3720.contactserver.MoveShipParams;
import cpsc3720.contactserver.Pair;
import cpsc3720.contactserver.Player;
import cpsc3720.contactserver.RefreshParams;
import cpsc3720.contactserver.Ship;
import cpsc3720.contactserver.ShipNotFoundException;
import cpsc3720.contactserver.TableLineItem;
import cpsc3720.contactserver.Universe;
import cpsc3720.contactserver.User;
import cpsc3720.contactserver.UserTracker;
import cpsc3720.contactserver.Weapon;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ContactService{
	
	//tests for existance of server
	@POST("/TestConnection")
	boolean testConnection() throws UnknownHostException;
	
	//reset server state to known condition
	@POST("/ResetforTest")
	int resetForTest();
	
	//reload game from file
	@POST("/RestoreGame")
	int restoreGame(@Body ArrayList<String> strings);
	
	//USER=================================
	//reset user list to known
	@POST("/Reset")
	int resetUser();
	
	//check credentials
	@POST("/VerifyLogin")
	User loginSuccessful(@Body User user);
	
	//get tracker instance
	@POST("/MapUsers")
	UserTracker mapUsers();
	
	//get collection of users
	@POST("/ListUsers")
	Collection<User> listUsers();
	
	//adds a user to tracker
	@POST("/AddUser")
	int addUser(@Body User user);
	
	//returns map of users
	@POST("/MakeUserList")
	Map<Integer, User> makeUserList();
	
	//updates user
	@POST("/UpdateUser")
	User updateUser(@Body User user);

	//deletes user
	@POST("/DeleteUser")
	User deleteUser(@Body User user);
	
	// WEAPON =================================== 
	
	//reset weapon list
	@POST("/ResetWeapons")
	int resetWeapons(@Body int id) throws GameNotFoundException;
	
	//return weapon collection
	@POST("/ListWeapons")
	Collection<Weapon> listWeapons(@Body int id) throws GameNotFoundException;
	
	//returns map of weapons
	@POST("/MakeWeaponList")
	public Map<Integer, Weapon> makeWeaponList(@Body int id) throws GameNotFoundException;
	
	//adds weapon to tracker
	@POST("/AddWeapon")
	int addWeapon(@Body Weapon weapon,@Body  int id) throws GameNotFoundException;
	
	//updates a weapon instance
	@POST("/UpdateWeapon")
	Weapon updateWeapon(@Body Weapon weapon,@Body  int id) throws GameNotFoundException;
	
	//deletes a weapon from tracker
	@POST("/DeleteWeapon")
	Weapon deleteWeapon(@Body Weapon weapon,@Body  int id) throws GameNotFoundException;
	
	// EMPIRES====================================
	
	//resets empire tracker to known state
	@POST("/ResetEmpires")
	int resetEmpires(@Body int id) throws GameNotFoundException;
	
	//returns collection of empires
	@POST("/ListEmpires")
	Collection<Empire> listEmpires(@Body int id) throws GameNotFoundException;
	
	//adds empire to tracker
	@POST("/AddEmpire")
	int addEmpire(@Body Empire empire,@Body  int id) throws GameNotFoundException;
	
	//updates empire tracker
	@POST("/UpdateEmpire")
	Empire updateEmpire(@Body Empire empire,@Body  int id) throws GameNotFoundException;
	
	//deletes empire from tracker
	@POST("/DeleteEmpire")
	Collection<Empire> deleteEmpire(@Body Empire empire,@Body  int id) throws GameNotFoundException;

	
	// SHIPS===================================

	//resets ship to known state
	@POST("/ResetShips")
	int resetShip(@Body int id) throws GameNotFoundException;
	
	//returns collection of ships
	@POST("/GetShipList")
	Collection<Ship> getShipList(@Body int id) throws GameNotFoundException;
	
	//identifies ship by int id
	@POST("/IDShip")
	Ship IDShip(@Body int id, @Body int shipID);
	
	//gets a ship by ID
	@POST("/GetShipByID")
	Ship getShipByID(@Body FindShipParams params) throws ShipNotFoundException;
	
	//sets alert of ship
	@POST("/setAlertLevel")
	int setAlertLevel(@Body AlertLevelParams params);
	
	//attempts to fire a torpedo
	@POST ("/FireTorpedo")
	AttackLogObject fireTorpedo(@Body FireParams params);
	
	//adds a ship to tracker
	@POST("/AddShip")
	int addShip(@Body Ship ship,@Body  int id) throws GameNotFoundException;
	
	//updates a ship instance
	@POST("/UpdateShip")
	Ship updateShip(@Body Ship ship,@Body  int id) throws GameNotFoundException;
	
	//moves a ship
	@POST("/MoveShip")
	int moveShip(@Body MoveShipParams move);
	
	//moves a ship to a known sector, and unknown cell
	@POST("/WarpShip")
	int warpShip(@Body MoveShipParams params);
	
	//checks that there is enough energy for action
	@POST("/ShipEnergyAdequate")
	int shipEnergyAdequate(@Body MoveShipParams params);
	
	//move AI ship
	@POST("/AIShipMove")
	AttackLogObject aiShipMove(@Body AIShipMoveParams params);
	
	//deletes ship from tracker
	@POST("/DeleteShip")
	int deleteShip(@Body Ship ship,@Body  int id) throws GameNotFoundException;
	
	// Game===================================
	//gets universe instance from game
	@POST("/GetUniverse")
	Universe getUniverse(@Body int id) throws GameNotFoundException;
	
	//gets list of games
	@POST("/GetGameList")
	List<TableLineItem> getGameList();

	//gets a game instance
	@POST("/GetGame")
	Game getGame(@Body int id) throws GameNotFoundException;
	
	//gets date from game instance
	@POST("/GetGameDate")
	int getGameDate(@Body int id) throws GameNotFoundException;
	
	//adds line to attack log
	@POST("/attackLog")
	String updateAttackLog(@Body AttackData data);
	
	//gets association between player and target
	@POST("/GetAssociationStatus")
	int getAssociationStatus(@Body AssociationsParameter assParam);
	
	// PLAYER8==========================D
	//gets map of players
	@POST("/GetPlayerMap")
	Map<Integer, Player> getPlayerMap(@Body int id) throws GameNotFoundException;

	//checks that the user can actually join the game
	@POST("/VerifyUserHasPlayer")
	Player userCanPlay(@Body GameInstanceData gameInstDat) throws GameNotFoundException;

	
}
