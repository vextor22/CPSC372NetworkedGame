package cpsc3720.contactserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The Game class represents Game instances. Game instances track all of the state and behavior of a particular game instance.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Game {
	
	private ShipTracker shipTracker;
	private Universe universeData;
	private EmpireTracker empireTracker;
	private WeaponTracker weaponTracker;
	private PlayerTracker playerTracker;
	private ShipTypeTracker shipTypeTracker;
	private BaseTracker baseTracker;
	private int gameId;


	private  int gameDate;
	private String gameTitle;
	private UserTracker registeredUsers;
	
	public static Game restoreGame(List<String> gameFile, int gameId, UserTracker userTracker){
		Game game = new Game();
		
		game.universeData = new Universe(game.gameTitle);
		game.playerTracker = new PlayerTracker();
		game.shipTracker = new ShipTracker();
		game.empireTracker = new EmpireTracker(game.shipTracker);
		game.weaponTracker = new WeaponTracker(game.shipTracker);
		game.shipTypeTracker = new ShipTypeTracker();
		game.baseTracker = new BaseTracker();
		
		game.registeredUsers = userTracker;
		String[] currentLine;
		
		System.out.println(gameFile.size());
		game.gameTitle = gameFile.get(1).split("\\t")[0];
		game.gameDate = Integer.parseInt(gameFile.get(1).split("\\t")[1]);
		
		int i = 0;
		for(; i < gameFile.size(); i++){
			if(gameFile.get(i).split("\\t")[0].equals("Empire Id"))
				break;
		}
		for(i = i+1; i < gameFile.size(); i++){
			if(!gameFile.get(i).split("\\t")[0].equals("Weapon Id")){
				//public Empire(String title, String empireId, boolean isAgreesive)
				currentLine = gameFile.get(i).split("\\t");
				if(currentLine[0] != "")
					game.empireTracker
						.addEmpire(new Empire(
							currentLine[0],
							currentLine[1],
							currentLine[2].equals("Aggression")));
			}
			else
				break;
		}
		for(i = i+1; i < gameFile.size(); i++){
			if(!gameFile.get(i).split("\\t")[0].equals("ShipType Id")){
				currentLine = gameFile.get(i).split("\\t");
				//String title, boolean energyType, int yield, String weaponID
				if(currentLine[0] != "")
					game.weaponTracker
					.addWeapon(new Weapon(
							currentLine[1],
							currentLine[2].equals("ENERGY"),
							Integer.parseInt(currentLine[3]),
							currentLine[0]));
			}
			else
				break;
		}
		for(i = i+1; i < gameFile.size(); i++){
			if(!gameFile.get(i).split("\\t")[0].equals("Base Id")){
				currentLine = gameFile.get(i).split("\\t");
				//String id, String name, String shipClass, String empId, 
				//int maxE, int maxSp, int maxSH, 
				//String enWepType, String misWepType, int maxMis
				if(currentLine[0] != "")
					game.shipTypeTracker.addShipType(new ShipType(
							currentLine[0],currentLine[1],currentLine[2],currentLine[3],
							Integer.parseInt(currentLine[4]),Integer.parseInt(currentLine[5]),Integer.parseInt(currentLine[6]),
							currentLine[7], currentLine[8],Integer.parseInt(currentLine[9])));
			}
			else
				break;
		}
		for(i = i+1; i < gameFile.size(); i++){
			if(!gameFile.get(i).split("\\t")[0].equals("Ship ID")){
				//int baseId, String empId,int sx, int sy, int px, int py
				currentLine = gameFile.get(i).split("\\t");
				if(currentLine[0] != "")
				game.baseTracker.addBase(new Base(
						Integer.parseInt(currentLine[0]), currentLine[1],
						Integer.parseInt(currentLine[2]),Integer.parseInt(currentLine[3]),
						Integer.parseInt(currentLine[4]),Integer.parseInt(currentLine[5])));
			}
			else
				break;
		}
		for(i = i+1; i < gameFile.size(); i++){
			if(!gameFile.get(i).split("\\t")[0].equals("Player ID")){
				//int idNum, ShipType shipType, Location location, int energyLevel, int missileQuantity,
				//AlertType alertLevel, int shieldLevel
				currentLine = gameFile.get(i).split("\\t");
				if(currentLine[0] != ""){
					ShipType type = game.shipTypeTracker.getType(currentLine[1]);
					//int sx, int sy, int px, int py
					Location loc = new Location(Integer.parseInt(currentLine[2]),Integer.parseInt(currentLine[3]),
							Integer.parseInt(currentLine[4]),
							Integer.parseInt(currentLine[5]));
					game.shipTracker.addShip(new Ship(Integer.parseInt(currentLine[0]),
							type,loc,Integer.parseInt(currentLine[6]),Integer.parseInt(currentLine[7]),
							AlertType.fromString(currentLine[8]),Integer.parseInt(currentLine[9])));
				}
			}
			else
				break;
		}
		for(i = i+1; i < gameFile.size(); i++){
			
			currentLine = gameFile.get(i).split("\\t");
			if(currentLine[0] != ""){
				//String ID, String affil, int ship
				game.playerTracker.add(new Player(
						currentLine[0],currentLine[1],Integer.parseInt(currentLine[2])));
			}
			
		}
		
		Collection<Ship> shipCol = game.shipTracker.getShipList();
		for(Ship ship : shipCol){
			game.universeData.setSector(ship.getLocation().getSXPos(), ship.getLocation().getSYPos(), ship);
		}
		
		Collection<Base> baseCol = game.baseTracker.getBasesList();
		for(Base base : baseCol){
			game.universeData.setSector(base.getLocation().getSXPos(), base.getLocation().getSYPos(), base);
		}
		
		
		game.gameId = gameId;
		//TODO: actually parse the string that comes in
		System.out.println("Got to the appropriate constructor");
		
		return game;
	}
	
	public Game(){
		
	}
	public int getGameId() {
		return gameId;
	}
	public Game(String filepath, int gameId, UserTracker userTracker) throws IOException{
		//read file and populate trackers with objects as specified
		universeData = new Universe(gameTitle);
		playerTracker = new PlayerTracker();
		shipTracker = new ShipTracker();
		empireTracker = new EmpireTracker(shipTracker);
		weaponTracker = new WeaponTracker(shipTracker);
		shipTypeTracker = new ShipTypeTracker();
		baseTracker = new BaseTracker();
		this.gameId = gameId;
		registeredUsers = userTracker;
		System.out.println(filepath);
		if(filepath.equals("Civil War")){
			buildCivilWar();
		}else{
			buildTrek();
		}
	}
	
	public Universe getUniverse(){
		return universeData;
	}

	public ShipTracker getShipTracker() {
		return shipTracker;
	}
	
	public PlayerTracker getPlayerTracker(){
		return playerTracker;
	}

	public EmpireTracker getEmpireTracker() {
		return empireTracker;
	}

	public WeaponTracker getWeaponTracker() {
		return weaponTracker;
	}

	public PlayerTracker getUserTracker() {
		return playerTracker;
	}
	public BaseTracker getBaseTracker(){
		return baseTracker;
	}
	
	public ShipTypeTracker getShipTypeTracker() {
		return shipTypeTracker;
	}
	public String getTitle(){
		return gameTitle;
	}
	public int getDate(){
		System.out.println("The star date is set to: " + gameDate);
		return gameDate;
	}
	

	/**Builds the game instance from the Trek.dat
	 * creates all of the objects and fills all of the fields required for the game
	 */
	private void buildTrek(){
		
		gameDate = 2236;
		System.out.println(gameDate);
		gameTitle = "Star Trek Forever";
						
		//public Empire(String title, String empireId, boolean isAgreesive)
		empireTracker.addEmpire(new Empire("Federation", "FED", false));
		empireTracker.addEmpire(new Empire("Klingon", "FED", true));
		empireTracker.addEmpire(new Empire("Bajoran", "FED", false));
		empireTracker.addEmpire(new Empire("Cardassian", "FED", true));
		
		
		//String title, boolean energyType, int yield, String weaponID
		Weapon weapon1 = new Weapon("Phaser", true, 100, "PHAS");
		Weapon weapon2 = new Weapon("Photon Torpedo", false, 300, "PTOR");
		Weapon weapon3 = new Weapon("Antimatter Torpedo", false, 10000, "ATOR");
		Weapon weapon4 = new Weapon("Gravimetric Torpedo", false, 800, "GTOR");
		Weapon weapon5 = new Weapon("Pulse Cannon", true, 150, "PCAN");
		weaponTracker.addWeapon(weapon1);
		weaponTracker.addWeapon(weapon2);
		weaponTracker.addWeapon(weapon3);
		weaponTracker.addWeapon(weapon4);
		weaponTracker.addWeapon(weapon5);
		
		ShipType shipType1 = new ShipType("STC" ,"Starship", "Constitution", "FED", 3000 , 9, 500, "PHAS", "PTOR", 10);
 		ShipType shipType2 = new ShipType("STM" ,"Starship", "Miranda", "FED", 4000 , 4, 400, "PHAS", "ATOR", 2);
 		ShipType shipType3 = new ShipType("BOP" ,"Bird of Prey", "D-12", "KLI", 2500 , 5, 600, "PCAN", "GTOR", 5);
 		ShipType shipType4 = new ShipType("CWS" ,"Cruiser", "Galor", "CAR", 2000 , 6, 300, "PHAS", "PTOR", 12);
 		ShipType shipType5 = new ShipType("BWS" ,"Starship", "Antares", "BAJ", 2500 , 4, 300, "PHAS", "PTOR", 6);
 		
 		shipTypeTracker.addShipType(shipType1);
 		shipTypeTracker.addShipType(shipType2);
 		shipTypeTracker.addShipType(shipType3);
 		shipTypeTracker.addShipType(shipType4);
 		shipTypeTracker.addShipType(shipType5);
 		
 		Base base1 = new Base(0,"FED", 2, 4,3, 2);
 		Base base2 = new Base(0,"FED", 2, 5,6, 7);
 		Base base3 = new Base(0,"KLI", 1, 1,2, 1);
 		
 		baseTracker.addBase(new Base(0,"FED", 2, 4,3, 2));
		baseTracker.addBase(new Base(0,"FED", 2, 5,6, 7));
		baseTracker.addBase(new Base(0,"KLI", 1, 1,2, 1));
 		
 		Location loc1 = new Location(2,4,6,5);
 		Location loc2 = new Location(2,5,4,4);
 		Location loc3 = new Location(2,5,2,2);
 		Location loc4 = new Location(3,6,1,5);
 		
 		Ship ship1 = new Ship(0,shipType1, loc1, 2800, 8, AlertType.RED, 300);
 		Ship ship2 = new Ship(1,shipType3, loc2, 1900, 7, AlertType.YELLOW, 400);
 		Ship ship3 = new Ship(1,shipType2, loc3, 3000, 2, AlertType.YELLOW, 200);
 		Ship ship4 = new Ship(1,shipType3, loc4, 1500, 1, AlertType.YELLOW, 5);
	
 		shipTracker.addShip(new Ship(0,shipType1, loc1, 2800, 8, AlertType.RED, 300));
 		shipTracker.addShip(new Ship(1,shipType3, loc2, 1900, 7, AlertType.YELLOW, 400));
 		shipTracker.addShip(new Ship(1,shipType2, loc3, 3000, 2, AlertType.YELLOW, 200));
 		shipTracker.addShip(new Ship(1,shipType3, loc4, 1500, 1, AlertType.YELLOW, 5));
 		
 	/*	//AI SHIPS
 		Location loc5 = new Location(2,4,5,5);
 		Location loc6 = new Location(2,5,1,6);
 		Location loc7 = new Location(4,4,4,4);
 		
 		Ship aiShip1 = new Ship(0, shipType1, loc5, 2800, 5, AlertType.GREEN, 420);
 		Ship aiShip2 = new Ship(1, shipType2, loc6, 3000, 2, AlertType.YELLOW, 330);
 		Ship aiShip3 = new Ship(0, shipType3, loc7, 2000, 9, AlertType.YELLOW, 150);
 		
 		shipTracker.addShip(aiShip1);
 		shipTracker.addShip(aiShip2);
 		shipTracker.addShip(aiShip3);
 	*/	
 		Collection<Ship> shipList = shipTracker.getShipList();
		
		assert(universeData != null);
		
		
		universeData.setSector(ship1.getLocation().getSXPos(), ship1.getLocation().getSYPos(), ship1);
		universeData.setSector(ship2.getLocation().getSXPos(), ship2.getLocation().getSYPos(), ship2);
		universeData.setSector(ship3.getLocation().getSXPos(), ship3.getLocation().getSYPos(), ship3);
		universeData.setSector(ship4.getLocation().getSXPos(), ship4.getLocation().getSYPos(), ship4);
		
		universeData.setSector(base1.getLocation().getSXPos(), base1.getLocation().getSYPos(), base1);
		universeData.setSector(base2.getLocation().getSXPos(), base2.getLocation().getSYPos(), base2);
		universeData.setSector(base3.getLocation().getSXPos(), base3.getLocation().getSYPos(), base3);
		

		playerTracker.add(new Player(registeredUsers.makeUserList().get(1).getUserName(), "FED", 0));
		playerTracker.add(new Player(registeredUsers.makeUserList().get(2).getUserName(), "KLI", 1));
		
		
				
	}
	
	/**Builds the game instance from the CivilWar.dat
	 * creates all of the objects and fills all of the fields required for the game
	*/
	
	private void buildCivilWar() {
			
		gameDate = 1862;
		gameTitle = "Civil War";
		
		//create Empires
		empireTracker.addEmpire(new Empire("Union", "NOR", true));
		empireTracker.addEmpire(new Empire("Confederacy", "SOU", false));
		
		
		// CREATE WEAPONS
 		weaponTracker.addWeapon(new Weapon("Water Cannon" ,true, 10, "CAN"));
 		weaponTracker.addWeapon(new Weapon("Torpedo", false, 30, "TOR"));
 		
 		
 		//CREATE SHIPTYPE
 		ShipType shipType1 = new ShipType("MON" ,"Monitor", "Ironclad", "NOR", 300 , 9, 50, "CAN", "TOR", 10);
 		ShipType shipType2 = new ShipType("SUB" ,"Submarine", "Submarine", "SOU", 400 , 4, 40, "CAN", "TOR", 2);
 		shipTypeTracker.addShipType(shipType1);
 		shipTypeTracker.addShipType(shipType2);
 		
 		
 		//CREATE BASE
 		Base base1 = new Base(0,"NOR", 3, 5,2, 1);
 		Base base2 = new Base(1,"SOU",2, 4, 3 ,2);
 		baseTracker.addBase(base1);
 		baseTracker.addBase(base2);
 		
 		//add to the sector
 		universeData.setSector(base1.getLocation().getSXPos(), base1.getLocation().getSYPos(), base1);
 		universeData.setSector(base2.getLocation().getSXPos(), base2.getLocation().getSYPos(), base2);

		//CREATE SHIPS
 		Location loc1 = new Location(3,4,4,3);
 		Location loc2 = new Location(2,5,2,1);
 		
 		Ship ship1 = new Ship(0,shipType1, loc1, 280, 8, AlertType.RED, 30);
 		Ship ship2 = new Ship(1,shipType2, loc2, 190, 2, AlertType.YELLOW, 40);
 		shipTracker.addShip(ship1);
 		shipTracker.addShip(ship2);
 		//add ships to the sectors
 		
 		universeData.setSector(ship1.getLocation().getSXPos(), ship1.getLocation().getSYPos(), ship1);
 		universeData.setSector(ship2.getLocation().getSXPos(), ship2.getLocation().getSYPos(), ship2);
 		
 		//create Players
 		playerTracker.add(new Player("lee", "SOU", 1));
 		playerTracker.add(new Player("grant", "NOR", 0));
		
		
		
	}
	@Override
	public String toString() {
		return "Game [shipTracker=" + shipTracker + ", universeInstance="
				+ universeData + ", empireTracker=" + empireTracker
				+ ", weaponTracker=" + weaponTracker + ", playerTracker="
				+ playerTracker + ", gameDate=" + gameDate + ", gameTitle="
				+ gameTitle + ", registeredUsers=" + registeredUsers + "]";
	}

	public Map<Integer, Player> getPlayerMap() {
		// TODO Auto-generated method stub
		return playerTracker.getPlayerMap();
	}

}
