package cpsc3720.contactserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cpsc3720.contactclient.SectorTableModel;
import cpsc3720.contactclient.UniverseTableModel;
import cpsc3720.contactservice.ContactService;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Contact Server represents the server side functionality, and keeps track of separate game instances.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
@RestController
@EnableAutoConfiguration
public class ContactServer implements ContactService{
	
	UserTracker registeredUsers;
	
	Map<Integer, Game> gameList;
	Map<Integer, String> attackLog;
	 
	int gameId; 
	
	public ContactServer(){
		registeredUsers = new UserTracker();
		gameList = new HashMap<Integer, Game>();
		attackLog = new HashMap<Integer,String>();
		registeredUsers.reset();
		gameId = 0;
		resetForTest();
		
	}
	
	private Game findGame(int id) throws GameNotFoundException{
		if(gameList.containsKey(new Integer(id)))
			return gameList.get(new Integer(id));
		else{
			System.out.println(gameList);
			throw new GameNotFoundException();
		}
	}
	
	/**
	 * Finds a ship with the specified ID in the specified game.
	 * 
	 * @param id integer id of a game instance
	 * @param shipID integer id of a ship instance to find
	 * 
	 * @return the instance of Ship with the specified shipID
	 */
	@RequestMapping("/ShipID")
	public Ship IDShip(@RequestBody int id, @RequestBody int shipID) {
		try {
			Game game = findGame(id);
			return game.getShipTracker().getShipByID(shipID);
			
			
		} catch (GameNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Updates the alert level of a specified ship in the specified game.
	 * 
	 * @param params AlertLevelParams object which holds the necessary parameters for the function
	 * 
	 * @return useless integer returned
	 */
	@RequestMapping("/setAlertLevel")
	public int setAlertLevel(@RequestBody AlertLevelParams params){
		Ship ship = IDShip(params.getId(), params.getShipID());
		ship.setAlertLvl(AlertType.fromString(params.getAlertLvl()));
		
		System.out.println("Ship # " + ship.getIdNum() + " has set its alert level to " + params.getAlertLvl());
		
		return 1;
	}

	/**
	 * Simple ping function to check server connection.
	 * 
	 * @return boolean value TRUE
	 */
	@RequestMapping("/TestConnection")
	public boolean testConnection(){
		return true;
	}
	
	/**
	 * Resets the server to an initial state. Not to be used lightly.
	 */
	@RequestMapping("/ResetforTest")
	public int resetForTest(){
		resetUser();
		gameList.clear();
		gameId = 0;
		try {
			gameList.put(gameId,new Game("Civil War",gameId++, registeredUsers));
			gameList.put(gameId,new Game("Star Trek Forever",gameId++, registeredUsers));
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Loads a game from a list of strings representing a game .dat file. File is uploaded from client.
	 * 
	 * @return integer id of the game created
	 */
	@RequestMapping("/RestoreGame")
	public int restoreGame(@RequestBody ArrayList<String> strings){
		strings.size();
		gameList.put(gameId,Game.restoreGame(strings,gameId++, registeredUsers));
		return gameId - 1;
	}
	
	/**
	 * Resets user list to the standard start condition.
	 */
	@RequestMapping("/ResetUser")
	public int resetUser(){
		registeredUsers.reset();
		return 1;
	}
	
	/**
	 * Function takes a User and checks the provided 
	 */
	@RequestMapping("/VerifyLogin")
	public User loginSuccessful(@RequestBody User user){
		return registeredUsers.doesUserExist(user);
	}
	
	ArrayList<User> users = new ArrayList<User>();
	Map<Integer, User> usersMap = new HashMap<Integer, User>();
    
	/**
	 * lists users
	 */
    @RequestMapping("/ListUsers")
    public Collection<User> listUsers() {
    	return registeredUsers.getUserList();
    }
    
    /**
     * makes a user list
     */
    @RequestMapping("/MakeUserList")
	public Map<Integer, User> makeUserList(){
    	return registeredUsers.makeUserList();
    }
    
    /**
     * Returns the list of registers users
     */
    @RequestMapping("/MapUsers")
    public UserTracker mapUsers(){
    	return registeredUsers;
    }
    
    /**
     * Adds user
     * 
     *@param user we want to ass
     *
     *@return returns the id number of the user we add
     */
    @RequestMapping("/AddUser")
    public int addUser(@RequestBody User user) {	
	    return registeredUsers.addUser(user);
    }
    
    /**
     * deletes user
     * 
     * @param user we want to delete
     * 
     * @return returns the user that we deleted
     */
    @RequestMapping("/DeleteUser")
    public User deleteUser(@RequestBody User user){
    	return registeredUsers.deleteUser(user);
    }
   
    /**Updates user
     * 
     * @param user - the user we want to update
     * 
     * @return returns the updated user
     */
    @RequestMapping("/UpdateUser")
    public User updateUser(@RequestBody User user){
    	return registeredUsers.updateUser(user);
    } 
    
    //Weapons classes============================================================================
    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/AddWeapon")
    public int addWeapon(@RequestBody Weapon weapon,@RequestBody int id) throws GameNotFoundException {
    	System.out.println("Add weapon to: " + id);
	    return findGame(id).getWeaponTracker().addWeapon(weapon);
    }
    
    /**
     * gets a list of weapons withing a game given a game ID
     * 
     * @param id - game id number
     * 
     * @return the list of weapons
     */
    @RequestMapping("/ListWeapons")
    public Collection<Weapon> listWeapons(@RequestBody int id) throws GameNotFoundException {
    	System.out.println("Returning Weapon list from: " + id);
    	return findGame(id).getWeaponTracker().getWeaponsList();
    }
    
    /**
     * gets a list of weapons withing a game given a game ID
     * 
     * @param id - game id number
     * 
     * @return the list of weapons
     */
    @RequestMapping("/MakeWeaponList")
    public Map<Integer, Weapon> makeWeaponList(@RequestBody int id) throws GameNotFoundException{
    	return findGame(id).getWeaponTracker().makeWeaponsList();
    }
    
    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/DeleteWeapon")
    public Weapon deleteWeapon(@RequestBody Weapon weapon,@RequestBody int id) throws GameNotFoundException{
    	return findGame(id).getWeaponTracker().deleteWeapon(weapon);
    }
    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/UpdateWeapon")
    public Weapon updateWeapon(@RequestBody Weapon weapon,@RequestBody int id) throws GameNotFoundException{    	
    	return findGame(id).getWeaponTracker().updateWeapon(weapon);
    }
    
    @POST("/ResetWeapons")
	public int resetWeapons(@RequestBody int id) throws GameNotFoundException{
    	findGame(id).getWeaponTracker().reset();
		return 1;
	}
    
    // Empire classes ===========================================================================
    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/AddEmpire")
    public int addEmpire(@RequestBody Empire empire,@RequestBody int id) throws GameNotFoundException {
    	System.out.println("Adding empire: " + empire);
	    return findGame(id).getEmpireTracker().addEmpire(empire);
    }
    
    /**
     * Gets a list of empires based on a game ID
     * 
     * @param id - game id
     * 
     * @return returns the list of empires
     */
    @RequestMapping("/ListEmpires")
    public Collection<Empire> listEmpires(@RequestBody int id) throws GameNotFoundException {
    	System.out.println("Returning Empire list");
    	return findGame(id).getEmpireTracker().getEmpiresList();
    }

    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/DeleteEmpire")
    public Collection<Empire> deleteEmpire(@RequestBody Empire empire,@RequestBody int id) throws GameNotFoundException{
    	System.out.println("Deleting empire in: " + id);
    	return findGame(id).getEmpireTracker().deleteEmpire(empire);
    }
    
    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/UpdateEmpire")
    public Empire updateEmpire(@RequestBody Empire empire,@RequestBody int id) throws GameNotFoundException{  
    	System.out.println("Updating empire in: " + id);
    	return findGame(id).getEmpireTracker().updateEmpire(empire);
    }
    
    @POST("/ResetEmpires")
	public int resetEmpires(@RequestBody int id) throws GameNotFoundException{
    	System.out.println("Reseting empires in: " + id);
    	findGame(id).getEmpireTracker().reset();
		return 1;
	}

    //Ship classes=====================================================================================
    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/AddShip")
    public int addShip(@RequestBody Ship ship,@RequestBody int id) throws GameNotFoundException {	
    	System.out.println("Adding ship in: " + id);
	    return findGame(id).getShipTracker().addShip(ship);
    }
    
    /**
     * Gets a list of ships based on game id.
     * 
     * @param id - the game id
     * 
     * @return returns a collection of the ship list
     */
    @RequestMapping("/GetShipList")
    public Collection<Ship> getShipList(@RequestBody int id) throws GameNotFoundException {
    	System.out.println("Returning Ship list for: " + id);
    	return findGame(id).getShipTracker().getShipList();
    }

    /**
     * Deprecated. Do not use! 
     */
    @RequestMapping("/DeleteShip")
    public int deleteShip(@RequestBody Ship ship,@RequestBody int id) throws GameNotFoundException{
    	System.out.println("Deleting ship in: " + id);
    	return findGame(id).getShipTracker().deleteShip(ship);
    }
    
    /**
     * In sector travel, determines if it is even possible and then carries out the action.
     * 
     * @param params - object which holds the necessary parameters for the function
     *  
     * @return  useless integer returned
     */
    @RequestMapping("/MoveShip")
	public int moveShip(@RequestBody MoveShipParams move){
    	int gameID = move.getGameID();
    	int playerShip = move.getpShip();
    	Location target = move.getTarget();
    	System.out.println("stuff " + move);
    	try {
			Game game = findGame(gameID);
			Universe uni = game.getUniverse();
			Ship ship = game.getShipTracker().getShipByID(playerShip);
			Location currLoc = ship.getLocation();
			uni.getSector(ship.getLocation().sectXPos,ship.getLocation().sectYPos).leaveSector(ship);
			//if(currLoc.sectXPos == target.sectXPos && currLoc.sectYPos == target.sectYPos){
				ship.setLocation(target);
				uni.setSector(target.sectXPos, target.sectYPos, ship);
				System.out.println("Location: " + game.getShipTracker().getShipByID(playerShip).getLocation());
			//}
			//else{
				
			//}
			
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return 1;
    }
    
    /**
     * Handles necessary calculations to see if warping is possible, and if so carries out warp naviagtion.
     * Warp is used for inter-sector travel
     * 
     * @param params - object which holds the necessary parameters for the function
     *  
     *  @return  useless integer returned
     */
    @RequestMapping("/WarpShip")
	public int warpShip(@RequestBody MoveShipParams params){
    	Game game;
		try {
			if(warpEnergyAdequate(params) == 1){
				game = findGame(params.getGameID());
				Universe uni = game.getUniverse();
		    	Ship playerShip = game.getShipTracker().getShipByID(params.getpShip());
		    	Location target = params.getTarget();
		    	System.out.println("Warp To: " + target);
		    	Random rand = new Random();
		    	target.ptYPos = rand.nextInt(8);
		    	target.ptXPos = rand.nextInt(8);
		    	while(!uni.collides(target)){
		    		target.ptYPos = rand.nextInt(8);
		        	target.ptXPos = rand.nextInt(8);
		    	}
		    	//moveShip(new MoveShipParams(currPlayer.getShip(), gameName, loc)
		    	System.out.println("Warp To: " + target);
		    	moveShip(new MoveShipParams(params.getpShip(), params.getGameID(), target));
			}
			else
				return 0;
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	return 1;
    }
    
    private int warpEnergyAdequate(MoveShipParams params){
    	Location target = params.getTarget();
    	try {
			Game game = findGame(params.getGameID());
			Ship ship = game.getShipTracker().getShipByID(params.getpShip());
			Location currLoc = ship.getLoc();
			int distance = (int) Math.sqrt(Math.pow(target.sectXPos-currLoc.sectXPos,2)+Math.pow(target.sectYPos-currLoc.sectYPos,2));
			
			if(distance < ship.getShipType().getMaxSpeed()){
				int energyNeeded = distance * 100;
				if(ship.getEnergyLvl() > energyNeeded)
					ship.setEnergyLvl(ship.getEnergyLvl() - energyNeeded);
				else
					return 0;
			}
			else
				return 0;
			
		if(AlertType.YELLOW == ship.getAlertLvl())
			ship.setEnergyLvl(ship.getEnergyLvl() - (int)(ship.getShipType().getMaxEnergy()*.02));
		else if(AlertType.RED == ship.getAlertLvl())
			ship.setEnergyLvl(ship.getEnergyLvl() - (int)(ship.getShipType().getMaxEnergy()*.05));
		
		if(ship.getEnergyLvl() < 0)
			ship.setEnergyLvl(0);
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return 1;
    }
    
    /**
     * Figures out if the ship has enough energy to do impulse navigation
     * 
     *  @param params - object which holds the necessary parameters for the function
     *  
     *  @return  useless integer returned
     */
	@RequestMapping("/ShipEnergyAdequate")
	public int shipEnergyAdequate(@RequestBody MoveShipParams params){
		Location target = params.getTarget();
		try {
			Game game = findGame(params.getGameID());
			Ship ship = game.getShipTracker().getShipByID(params.getpShip());
			Location currLoc = ship.getLoc();
			int distance = (int) Math.sqrt(Math.pow(target.ptXPos-currLoc.ptXPos,2)+Math.pow(target.ptYPos-currLoc.ptYPos,2));
			int energyNeeded = distance * 10;
			if(ship.getEnergyLvl() > energyNeeded)
				ship.setEnergyLvl(ship.getEnergyLvl() - energyNeeded);
			else return 0;
			if(AlertType.YELLOW == ship.getAlertLvl())
				ship.setEnergyLvl(ship.getEnergyLvl() - (int)(ship.getShipType().getMaxEnergy()*.02));
			else if(AlertType.RED == ship.getAlertLvl())
				ship.setEnergyLvl(ship.getEnergyLvl() - (int)(ship.getShipType().getMaxEnergy()*.05));
			
			if(ship.getEnergyLvl() < 0)
				ship.setEnergyLvl(0);
			
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return 1;
	}
    
	/**
	 * Looks up a ship by its id nnumber within a game
	 * 
	 * @param params - object which holds the necessary parameters for the function
	 * 
	 * @return returns the ship that was looked up by ID number
	 */
    @RequestMapping("/GetShipByID")
    public Ship getShipByID(@RequestBody FindShipParams params) throws ShipNotFoundException{
    	Ship ship = IDShip(params.getGameID(), params.getShipID());
    	
    	if(ship != null)
    		return ship;
    	else return null;
    }
	
    /** 
     * This is called by the the main game screen when fire torpedo is selected.
     * this checks to see if there is a ship to shoot and shoots it or records a miss
     *	If a ship is destroyed it calls the methods to destroy it.
     *
     *@param params - object which holds the necessary parameters for the function
     *
     *@return returns and attack log object that will be used to update the attack log
     *
     */
	@RequestMapping ("/FireTorpedo")
	public AttackLogObject fireTorpedo(@RequestBody FireParams params){
		 int gameID = params.getId();
		 int shootingShip = params.getShipID();
		 
		 //TODO:This stuff here 
		//make sure that that location has a ship that can be attacked
			//if it is a ship continue on
			//if there isnt a ship tell the user that it isnt a valid place to shoot
		
		//verify that the attacker has at least one torpedo
			//tell attacker they cant fire bc they aint got no ammo
			//if they gots the ammo - fire in the hole

		 try{
			 Game game = findGame(gameID);
			 Universe uni = game.getUniverse();
			 int shieldDamage = 0;
			 Ship attacker = game.getShipTracker().getShipByID(shootingShip);
			 Sector theSector = uni.getSector(attacker.getLocation().sectXPos, attacker.getLocation().sectYPos);
			 AttackData theData; 
			 Ship opponentShip;
			 ShipDamage hitShipDamage = new ShipDamage();
			 String log = "";
			
			 try {
				opponentShip = theSector.getCellShip(params.getSelectedPair().getX(), params.getSelectedPair().getY() );
				 if(!(attacker.getShipType().getEmpireId().equals(opponentShip.getShipType().getEmpireId())) && attacker.getMissileQty()>=1 ){
						attacker.decreaseMissleQty(); // decrement Missile count
						System.out.println(attacker.getMissileQty());
						hitShipDamage = opponentShip.hitShip(attacker, game.getWeaponTracker(), theSector);//damage the oppent's ship.
						shieldDamage = hitShipDamage.getShipDamage();
						theData = new AttackData(attacker, opponentShip, shieldDamage, game.getDate(), 1);
				 }
				 //flag of 2 means you dont have any missiles
				 else if (!(attacker.getShipType().getEmpireId().equals(opponentShip.getShipType().getEmpireId())) && attacker.getMissileQty() == 0){
					 opponentShip = new Ship();
					 theData = new AttackData(attacker, opponentShip, 0, game.getDate(), 2);
				 }
				 else if((attacker.getShipType().getEmpireId().equals(opponentShip.getShipType().getEmpireId())) && attacker.getMissileQty() >= 1){
					 theData = new AttackData(attacker, opponentShip, 0, game.getDate(), 3);
				 }
				 //this is not a valid place to shoot, you lost a missile 
				 else{
					 attacker.decreaseMissleQty();
					 opponentShip = new Ship();
					 theData = new AttackData(attacker, opponentShip, 0, game.getDate(), 0);
				 }
			} catch (NoObjectInCell e) {
				opponentShip = new Ship();
				attacker.decreaseMissleQty();
				theData = new AttackData(attacker, opponentShip, 0, game.getDate(), 0);
			}
			
			 if(hitShipDamage.isDestroyed()){
				 theSector.leaveSector(opponentShip);
				 game.getShipTracker().deleteShip(opponentShip);
			 }
			 
			 log = updateAttackLog(theData);
			 
			 return new AttackLogObject(log);
		 }catch(GameNotFoundException e) {
				e.printStackTrace();
			}
		 
		return new AttackLogObject("Error.");
	}
	
	/**
	 * Updates with any new Ship data.
	 * 
	 * @param ship - ship class with parameters that define the ship
	 * 
	 * @param id - the game id where this ship is located
	 * 
	 * @return - returns the updated ship
	 */
    @RequestMapping("/UpdateShip")
    public Ship updateShip(@RequestBody Ship ship,@RequestBody int id) throws GameNotFoundException{   
    	System.out.println("Updating ship in: " + id);
    	return findGame(id).getShipTracker().updateShip(ship);
    }
    
    /**
     * Calculates the move/turn of the AI ship. Scans the sector for any enemy ships and fires a missile at them
     * 
     * @param AIShipMoveParams - object which holds the necessary parameters for the function 
     * 
     * @return returns an AttackLogObject - a string that will be put into the attack log
     */
    @RequestMapping("/AIShipMove")
    public AttackLogObject aiShipMove(@RequestBody AIShipMoveParams params){

    	String string = "";
    	ArrayList<String> list = new ArrayList<String>();
    	AttackLogObject log = new AttackLogObject();
    	Game game;
    	try {
			game = getGame(params.getGameID());
	    	//get ship tracker
			
	    	ShipTracker tracker = game.getShipTracker();
	    	
	    	//see if there are any ships that are not being used 
	    	
	    	//for each ship - scan the blocks around them
	    	Universe uni = game.getUniverse();
	    	Ship aiShip = tracker.getShipByID(params.getShipId());
	    	Sector scan = uni.getSector(aiShip.getLocation().sectXPos, aiShip.getLocation().sectYPos);
	    	Pair aiShipCell = new Pair(aiShip.getLocation().ptXPos, aiShip.getLocation().ptYPos);
	    	Pair closestCell = new Pair();
	    	double closest = Integer.MAX_VALUE;
	    	double dist;
	    	// find foe ships and record closest foe location
	    	// then call get ship with that location
	    	for(int i =0 ; i < 8; i++){
	    		for(int j =0 ; j < 8; j++){
	    			// call function to see if it is a foe
	    			if(scan.isFoe(i, j, aiShip)){
	    				// check the distance of this cell
							dist =  Math.sqrt(((i- aiShipCell.getX()) * (i- aiShipCell.getX())) + ((j- aiShipCell.getY()) * (j- aiShipCell.getY())));
							// if it is closest save the cell as the closest
							if(dist < closest){
								closest = dist;	    // save shortest distance
								closestCell.setX(i);
								closestCell.setY(j);
							}
	    			}
	    		}
	    	}
	    	// try and get the chip in the cell
	    	Ship checkShip; 
	    	try {
				checkShip = scan.getCellShip(closestCell.getX(), closestCell.getY());
				FireParams fireparams = new FireParams(closestCell, params.getGameID(), params.getShipId());
				log = fireTorpedo(fireparams); // get the log from firing at the closest ship
			} catch (NoObjectInCell e) {

				checkShip = new Ship();
				aiShip.decreaseMissleQty();
				AttackData nohitData = new AttackData(aiShip, checkShip, 0, game.getDate(), 0);
				String flog = updateAttackLog(nohitData);
				log = new AttackLogObject(flog);

			}
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	return log;

    }
    
    @POST("/ResetShips")
	public int resetShip(@RequestBody int id) throws GameNotFoundException{
    	System.out.println("Reseting ships for: " + id);
    	findGame(id).getShipTracker().reset();
		return 1;
	}
    
    public static void main(String[] args) throws Exception {
       
    	SpringApplication.run(ContactServer.class, args);    
    }
    
	//Player Stuff======================================================================================
    /**
     * Gets the map of players based on Game ID number
     * 
     * @param id - game ID number
     * 
     * @return returns the player map of a specific game
     */
	@RequestMapping("/GetPlayerMap")
	public Map<Integer, Player> getPlayerMap(@RequestBody int id) throws GameNotFoundException{
		return findGame(id).getPlayerMap();
		
	}
	
	/**
	 * Checks to see that there is a player in the game instance for the user attempting to join.
	 * 
	 * @param gameInstDat - 
	 */
	@RequestMapping("/VerifyUserHasPlayer")
	public Player userCanPlay(@RequestBody GameInstanceData gameInstDat) throws GameNotFoundException{;
		System.out.println(gameInstDat);
		return findGame(gameInstDat.getMyGameID()).getPlayerTracker().isUserInGame(gameInstDat.getMyUser());
	}



	//Game function/Universe============================================================================
	/**
	 * Get a universe by a specific ID number
	 * 
	 * @param id - number of the universe we are looking for
	 * 
	 * @return returns the universe by the specified ID
	 */
	@RequestMapping("/GetUniverse")
	public Universe getUniverse(@RequestBody int id) throws GameNotFoundException{
		return findGame(id).getUniverse();
	}

	/**
	 * Gets game based on an inputed ID number
	 * 
	 * @param id - game id that we want to try to find the game instance of
	 * 
	 * @return returns the found Game
	 */
	@RequestMapping("/GetGame")
	public Game getGame(@RequestBody int id) throws GameNotFoundException {
		return findGame(id);
	}
	
	/**
	 * Gets the entire list of games from the server.
	 * 
	 * @return returns the entire list of games
	 */
	@RequestMapping("/GetGameList")
	public List<TableLineItem> getGameList(){
		List<TableLineItem> lineItems = new ArrayList<TableLineItem>();
		System.out.println("Game List size: " + gameList.size());
		Collection<Game> gameCollection = gameList.values();
		for(Game game : gameCollection){
			lineItems.add(new TableLineItem(game.getTitle(),game.getGameId()));
		}
		System.out.println(lineItems);
		return lineItems;
	}
	
	/**
	 * Gets the game date based on the game id, throws an exception if the game does not exist on the server.
	 * 
	 * @param id - the game ID that we wish to look up the date by
	 * 
	 * @return returns the date for that specific game
	 */
	@RequestMapping("/GetGameDate")
	public int getGameDate(@RequestBody int id) throws GameNotFoundException {
		return findGame(id).getDate();
	}

	/**
	 * Puts together the string that will be displayed in the attack log.
	 * 
	 * @param data AttackData object that holds the necessary parameters for the function to get information
	 * 
	 * @return Returns the string that will be put into the attackModel
	 */
	// stardate: Attacker (x,y) shot a torpedo and Victim (x,y) takes amount of damage 
	@RequestMapping("/Attacklog")
	public String updateAttackLog(@RequestBody AttackData data){
		String string = "";

		//valid spot to shoot
		if (data.isValid == 1){
		
			String attackerloc = "(" + data.attacker.getLocation().ptXPos + "," + data.attacker.getLocation().ptYPos + ")";
			String victimLoc = "(" + data.hitShip.getLocation().ptXPos + "," + data.hitShip.getLocation().ptYPos + ")";

			string = data.starDate + ": " + data.attacker.getShipType().getName() + " at " + attackerloc + " fired a torpedo at " + data.hitShip.getShipType().getName() 
					+ " at " + victimLoc + " for " + data.alertDamage + " damage.";
		}
		//not a valid place to shoot - empty square or base or trying to shoot across sectors, decr count
		else if (data.isValid == 0){
			string = data.attacker.getTitle() + ", this is not a valid place to shoot.";
		}
		else if (data.isValid == 3){
			string = "You can't shoot yourself.";
		}
		//trying to shoot yourself - no torpedo decrement flag 2 
		else{
			string = data.attacker.getTitle() + " has no torpedos left.";
		}
		
		System.out.println(string);
		
		return string;
	}
	
	/**
	 *  Used by the main game screen to update the sector view and display whether a ship or base is you, friendly, or an enemy
	 *  
	 * @param assParam  - object that holds the necessary parameters for the function to get information
	 * 
	 * @return number returned represents the status of a given cell
	 */
	@RequestMapping("/GetAssociationStatus")
	public int getAssociationStatus(@RequestBody AssociationsParameter assParam){
		int currGameID = assParam.getMyGameID();
		
		Player player = assParam.getMyCurrPlayer();
		String currAffil = player.getAffiliation();
		
		Location loc = assParam.getMyLoc();
		
		Collection<Base> baseColl = gameList.get(currGameID).getBaseTracker().getBasesList();
		Base[] baseL = baseColl.toArray(new Base[baseColl.size()]);

		ShipTracker shipTr = gameList.get(currGameID).getShipTracker();
		Collection<Ship> shipColl = shipTr.getShipList();
		Ship[] shipL = shipColl.toArray(new Ship[shipColl.size()]);
		
		ArrayList<AffiliationAndLocation> objAffilLocs = new ArrayList<AffiliationAndLocation>();
		
		for(int i = 0; i < baseL.length; i++){
			objAffilLocs.add(new AffiliationAndLocation(baseL[i].getEmpireId(), baseL[i].getLocation()));
		}
		
		for(int i = 0; i < shipL.length; i++){
			objAffilLocs.add(new AffiliationAndLocation(shipL[i].getShipType().getEmpireId(), shipL[i].getLocation()));
		}
		
		Location myShipLoc = shipTr.getShipByID(player.getShip()).getLocation();
		
		int locsFlag = -1;
		for(int i = 0; i < objAffilLocs.size(); i++){
			if(objAffilLocs.get(i).getMyLoc().isSameLocation(loc)){
				locsFlag = i;
			}
		}
		
		if(loc.isSameLocation(myShipLoc)) return 1;
		else if(locsFlag != -1){
			if(currAffil.equals(objAffilLocs.get(locsFlag).getMyAffil())) return 2;
			else return 3;
		}else return 0;
	}
}
