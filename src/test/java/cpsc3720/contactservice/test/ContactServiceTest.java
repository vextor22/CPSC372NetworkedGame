package cpsc3720.contactservice.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import cpsc3720.contactserver.Base;
import cpsc3720.contactserver.ContactServer;
import cpsc3720.contactserver.Empire;
import cpsc3720.contactserver.Game;
import cpsc3720.contactserver.GameNotFoundException;
import cpsc3720.contactserver.Player;
import cpsc3720.contactserver.Ship;
import cpsc3720.contactserver.ShipType;
import cpsc3720.contactserver.Universe;
import cpsc3720.contactserver.User;
import cpsc3720.contactserver.UserTracker;
import cpsc3720.contactserver.Weapon;
import cpsc3720.contactservice.ContactService;
import junit.runner.Version;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;


public class ContactServiceTest {

	final static String PORT = "8976";

	static ContactService service;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("JUNIT VERSION:" + Version.id());
		String serverTest = System.getProperty("servertest", "0");
		if (false) {
			// Start a server
			System.setProperty("server.port", PORT);
			ContactServer.main(new String[] {});
	
			service = new RestAdapter.Builder()
					.setLogLevel(LogLevel.BASIC)
					.setEndpoint("http://localhost:" + PORT).build()
					.create(ContactService.class);
		} else {
			// Test server API directly
			service = new ContactServer();
		}
	}

/*
 * ===========================================
 * 
 * USER TEST
 * 
 * 
 * ===========================================
 */
	@Test
	public void testListUsers() {
		service.resetUser();
		Collection<User> result = service.listUsers();
		assertEquals(5, result.size());
	}
	
	@Test
	public void testUpdateUsers(){
		service.resetUser();		
		UserTracker tracker = service.mapUsers();
		User updateUser = new User (1, "newName", "newMail", "newPassword", false);
		
		
		tracker.updateUser(updateUser);
		Map<Integer, User> userMap = tracker.makeUserList();
		User testUser = userMap.get(1);
		
		assertEquals(0,testUser.getUserName().compareTo(updateUser.getUserName()));
		assertEquals(0,testUser.getEmail().compareTo(updateUser.getEmail()));
		assertEquals(0,testUser.getPassword().compareTo(updateUser.getPassword()));
	}

	@Test
	public void testAddUser() {
		service.resetUser();
		User contact = new User(0, "name","name","name",true);
		int result = service.addUser(contact);
		assertNotEquals(0, result);

		Collection<User> contacts = service.listUsers();
		assertEquals(6, contacts.size());

	}
	@Test
	public void testDeleteUser(){
		service.resetUser();
		User contact = new User(0, "name","name","name",true);
		int result = service.addUser(contact);
		assertNotEquals(0, result);

		Collection<User> users = service.listUsers();
		Iterator<User> userIterator = users.iterator();
		User toRemove = userIterator.next();
		System.out.println("User: " + toRemove);
		
		service.deleteUser(toRemove);
		Collection<User> usersAgain = service.listUsers();
		assertEquals(5, usersAgain.size());

	}
	/*
	 * ===========================================
	 * 
	 * END USER TEST
	 * 
	 * 
	 * ===========================================
	 */
	
	/*
	 * ===========================================
	 * 
	 * WEAPON TEST
	 * 
	 * 
	 * ===========================================
	 */
	@Test
	public void testListWeapons() {
		service.resetForTest();
		try {
			service.resetWeapons(0);
			Collection<Weapon> result = service.listWeapons(0);
			assertEquals(3, result.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testAddWeapon() {
		

		Collection<Weapon> weapons;
		try {
			service.resetForTest();
			service.resetWeapons(0);
			Weapon testWeapon = new Weapon("weapon", true, 400, "Anything1");
			int result = service.addWeapon(testWeapon, 0);
			assertNotEquals(0, result);
			weapons = service.listWeapons(0);
			assertEquals(4, weapons.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		

	}
	
	@Test
	public void testDeleteWeapon(){
		
		int result;
		try {
			service.resetForTest();
			service.resetShip(0);
			service.resetWeapons(0);
			
			Weapon testWeapon = new Weapon("weapon", true, 400, "Anything2");
			result = service.addWeapon(testWeapon, 0);
			assertNotEquals(0, result);
			

			Map<Integer, Weapon> checkWeapon = service.makeWeaponList(0);
			Weapon toRemove = checkWeapon.get(3);
			System.out.println("This Weapon Should be Deleted: " + toRemove);
			
			service.deleteWeapon(toRemove, 0);
			Collection<Weapon> weaponsAgain = service.listWeapons(0);
			assertEquals(3, weaponsAgain.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		
		
	

	} 
	
	@Test
	public void testUpdateWeapon(){
		service.resetForTest();
		try {
			service.resetWeapons(0);
			ArrayList<Integer> newWeaponIds1 = new ArrayList<>();
			newWeaponIds1.add(0);
			newWeaponIds1.add(1);
			
			ArrayList<Integer> newWeaponIds2 = new ArrayList<>();
			newWeaponIds2.add(2);
			
			
			//int idNum, String title, boolean isEnergyType, int yield
			
			Weapon testWeapon1 = new Weapon(10,"Test Weapon 1", false, 100);
			
			Weapon testWeapon2 = new Weapon(testWeapon1.getIdNum(), "Test Weapon 2", true, 200);
			Weapon myWeapon = service.updateWeapon(testWeapon2, 0);
			
			assertEquals(10, myWeapon.getIdNum());
			assertTrue(true == myWeapon.isEnergyType());
			assertEquals(200, myWeapon.getYield());
			assertEquals(0, myWeapon.getTitle().compareTo("Test Weapon 2"));
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		
		
		
	}
	
	/*
	 * ===========================================
	 * 
	 * END WEAPON TEST
	 * 
	 * 
	 * ===========================================
	 */
	
	/*
	 * ===========================================
	 * 
	 * EMPIRE TEST
	 * 
	 * 
	 * ===========================================
	 */
	@Test
	public void testListEmpires() {
		service.resetForTest();
		Collection<Empire> result;
		try {
			service.resetShip(0);
			service.resetEmpires(0);
			result = service.listEmpires(0);
			assertEquals(3, result.size());
		} catch (GameNotFoundException e) {
			// TODO: handle exception
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddEmpire() {
		service.resetForTest();
		
		try {
			service.resetShip(0);
			service.resetEmpires(0);
			Empire testEmpire = new Empire();
			int result = service.addEmpire(testEmpire, 0);
			assertNotEquals(-1, result);

			Collection<Empire> empires = service.listEmpires(0);
			assertEquals(4, empires.size());
			
			int result2 = service.addEmpire(testEmpire, 0);
			assertEquals(-1, result2);
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteEmpire(){
		service.resetForTest();
		try {
			service.resetShip(0);
			service.resetEmpires(0);
			Empire testEmpire = new Empire();
			int result = service.addEmpire(testEmpire, 0);
			assertNotEquals(0, result);

			Collection<Empire> empires = service.listEmpires(0);
			Iterator<Empire> empireIterator = empires.iterator();
			Empire toRemove = empireIterator.next();
			System.out.println("Empire: " + toRemove);
			
			service.deleteEmpire(toRemove, 0);
			Collection<Empire> empiresAgain = service.listEmpires(0);
			assertEquals(3, empiresAgain.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}

	}
	
	@Test
	public void testUpdateEmpire(){
		service.resetForTest();
		try {
			service.resetShip(0);
			service.resetEmpires(0);
			
			ArrayList<Integer> newShipIds1 = new ArrayList<>();
			newShipIds1.add(0);
			newShipIds1.add(1);
			
			ArrayList<Integer> newShipIds2 = new ArrayList<>();
			newShipIds2.add(2);
			
			Empire testEmpire1 = new Empire("Kevin's Empire", "KEM", newShipIds1, false);
			int firstEmpireId = service.addEmpire(testEmpire1, 0);
			
			Empire testEmpire2 = new Empire(firstEmpireId, "Other Empire", "OEM", newShipIds2, true);
			Empire myEmpire = service.updateEmpire(testEmpire2, 0);
			
			
			assertEquals(3, myEmpire.getIdNum());
			assertEquals(1, myEmpire.getShipIds().size());
			assertEquals(2, myEmpire.getShipIds().get(0).intValue());
			assertEquals(0, myEmpire.getTitle().compareTo("Other Empire"));
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}
	/*
	 * ===========================================
	 * 
	 * END EMPIRE TEST
	 * 
	 * 
	 * ===========================================
	 */
	
	/*
	 * ===========================================
	 * 
	 * SHIP TEST
	 * 
	 * 
	 * ===========================================
	 */
	
	@Test
	public void testListShips(){
		service.resetForTest();
		Collection<Ship> result;
		try {
			service.resetShip(0);
			result = service.getShipList(0);
			assertEquals(3, result.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testaddShip(){
		service.resetForTest();
		try {
			service.resetShip(0);
			Ship testShip = new Ship();	
			int result = service.addShip(testShip, 0);
			assertNotEquals(0, result);
			
			Collection<Ship> ships = service.getShipList(0);
			assertEquals(4, ships.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteShip(){
		service.resetForTest();
		try {
			service.resetShip(0);
			Ship testShip = new Ship();
			int result = service.addShip(testShip, 0);
			assertNotEquals(0, result);
			
			Collection<Ship> ships = service.getShipList(0);
			Iterator<Ship> shipIterator = ships.iterator();
			Ship toRemove = shipIterator.next();
			System.out.println("Ship: " + toRemove);
			
			service.deleteShip(toRemove, 0);
			Collection<Ship> shipsAgain = service.getShipList(0);
			assertEquals(3, shipsAgain.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	/*
	 * ===========================================
	 * 
	 * END SHIP TEST
	 * 
	 * 
	 * ===========================================
	 */
	
	
	/*
	 * ===========================================
	 * 
	 * GAME INSTANCE TESTS
	 * 
	 * 
	 * ===========================================
	 */
	@Test
	public void testResetForTest(){
		service.resetForTest();
		Game testGame;
		try {
			testGame = service.getGame(0);

			assertEquals(0,testGame.getTitle().compareTo("Civil War"));
			assertEquals(1862, testGame.getDate());
			
			//testEmpires
			Collection<Empire> empireList = new ArrayList<>();
			
			empireList= testGame.getEmpireTracker().getEmpiresList();
			assertEquals(2,empireList.size());
			
			for(Empire empire : empireList){
				assertTrue(empire.getTitle().equals("Union")|| empire.getTitle().equals("Confederacy"));
				assertTrue(empire.getEmpireId().equals("NOR")|| empire.getEmpireId().equals("SOU"));
			}
			
			
			//test Weapons
			Collection<Weapon> weaponList = testGame.getWeaponTracker().getWeaponsList();
			
			assertEquals(2,weaponList.size()); // check size
			
			for(Weapon weapon : weaponList){ // check the weapons for the different fields
				assertTrue(weapon.getTitle().equals("Water Cannon") || weapon.getTitle().equals("Torpedo"));
				assertTrue(weapon.getWeaponId().equals("CAN") || weapon.getWeaponId().equals("TOR"));
			}
	 		
			//test SHIPSTYPES
			Collection<ShipType> shipTypeList = testGame.getShipTypeTracker().getShipTypeList();
			
			assertEquals(2, shipTypeList.size()); // check for amount of ships
			
			for(ShipType shipType: shipTypeList){ // check ship fields
				assertTrue(shipType.getShipTypeId().equals("MON") || shipType.getShipTypeId().equals("SUB"));
				assertTrue(shipType.getName().equals("Monitor") || shipType.getName().equals("Submarine"));
			
				assertTrue(shipType.getShipClass().equals("Ironclad") || shipType.getShipClass().equals("Submarine"));
				assertTrue(shipType.getEmpireId().equals("NOR") || shipType.getEmpireId().equals("SOU"));
				
				assertTrue(shipType.getMissileWeaponType().equals("TOR"));
				assertTrue(shipType.getEnergyWeaponType().equals("CAN"));

			}
			
			//Test Bases
			Collection<Base> baseList = testGame.getBaseTracker().getBasesList();
			
			assertEquals(2,baseList.size());
			
			for(Base base: baseList){
				assertTrue(base.getEmpireId().equals("NOR") || base.getEmpireId().equals("SOU"));
				// need to verify the locations
			}
			
			//Test SHIPS
			Collection<Ship> shipList = testGame.getShipTracker().getShipList();

			assertEquals(2, shipList.size());
			
			for(Ship ship : shipList){
				assertTrue(ship.getShipType().getShipTypeId().equals("MON") || ship.getShipType().getShipTypeId().equals("SUB"));
			}
			
	 		
	 		Map<Integer,Player> playerList = testGame.getUserTracker().getPlayerMap();
	 		
	 		assertEquals(2, playerList.size()); // lee and grant should be playing
	 		int i;
	 		for(i = 0; i < playerList.size(); i++){
	 			assertTrue(playerList.get(i).getAffiliation().equals("SOU") || playerList.get(i).getAffiliation().equals("NOR"));
	 			assertTrue(playerList.get(i).getPlayerId().equals("lee") || playerList.get(i).getPlayerId().equals("grant"));
	 			assertTrue( playerList.get(i).getShip() == 1 || playerList.get(i).getShip() == 0);
	 		}
			
			
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		Game testGame2;
		try {
			testGame2 = service.getGame(1);
			
			assertTrue(testGame2.getTitle().equals("Star Trek Forever"));
			assertEquals(2236, testGame2.getDate());
			
			
			//testEmpires
			Collection<Empire> empireList = new ArrayList<>();
			
			empireList= testGame2.getEmpireTracker().getEmpiresList();
			assertEquals(4,empireList.size());
			
			for(Empire empire : empireList){
				assertTrue(empire.getTitle().equals("Federation")|| empire.getTitle().equals("Klingon") || empire.getTitle().equals("Bajoran")|| empire.getTitle().equals("Cardassian"));
				assertTrue(empire.getEmpireId().equals("FED")|| empire.getEmpireId().equals("KLI") || empire.getEmpireId().equals("BAJ")|| empire.getEmpireId().equals("CAR"));
				//FIGURE OUT HOW TO CHECK THE AGRESSION TYPE
			}
			
			
			//test Weapons
			Collection<Weapon> weaponList = testGame2.getWeaponTracker().getWeaponsList();
			
			assertEquals(5,weaponList.size()); // check size
			
			for(Weapon weapon : weaponList){ // check the weapons for the different fields
				assertTrue(weapon.getTitle().equals("Phaser") || weapon.getTitle().equals("Photon Torpedo") || weapon.getTitle().equals("Antimatter Torpedo") || weapon.getTitle().equals("Gravimetric Torpedo") ||weapon.getTitle().equals("Pulse Cannon"));
				assertTrue(weapon.getWeaponId().equals("PHAS") || weapon.getWeaponId().equals("PTOR") ||weapon.getWeaponId().equals("ATOR") || weapon.getWeaponId().equals("GTOR") || weapon.getWeaponId().equals("PCAN"));
			}

	 		
			//test SHIPTYPES
			
			Collection<ShipType> shipTypeList = testGame2.getShipTypeTracker().getShipTypeList();
			
			assertEquals(5, shipTypeList.size()); // check for amount of ships
			
			for(ShipType shipType: shipTypeList){ // check ship fields
				assertTrue(shipType.getShipTypeId().equals("STC") || shipType.getShipTypeId().equals("STM") || shipType.getShipTypeId().equals("BOP") || shipType.getShipTypeId().equals("CWS") ||  shipType.getShipTypeId().equals("BWS"));
				assertTrue(shipType.getName().equals("Starship") || shipType.getName().equals("Bird of Prey") || shipType.getName().equals("Cruiser"));
			
				assertTrue(shipType.getShipClass().equals("Constitution") || shipType.getShipClass().equals("Miranda") ||shipType.getShipClass().equals("D-12") || shipType.getShipClass().equals("Galor") || shipType.getShipClass().equals("Antares"));
				assertTrue(shipType.getEmpireId().equals("FED") || shipType.getEmpireId().equals("KLI") || shipType.getEmpireId().equals("CAR") || shipType.getEmpireId().equals("BAJ"));
				
				assertTrue(shipType.getMissileWeaponType().equals("PTOR") ||  shipType.getMissileWeaponType().equals("GTOR") || shipType.getMissileWeaponType().equals("ATOR"));
				assertTrue(shipType.getEnergyWeaponType().equals("PHAS") || shipType.getEnergyWeaponType().equals("PCAN"));
			}
			
			//Test Bases
			Collection<Base> baseList = testGame2.getBaseTracker().getBasesList();
			
			assertEquals(3,baseList.size());

			for(Base base: baseList){
				assertTrue(base.getEmpireId().equals("FED") || base.getEmpireId().equals("KLI") || base.getEmpireId().equals("BAJ") || base.getEmpireId().equals("CAR"));
			}
			
			//Test SHIPS
			Collection<Ship> shipList = testGame2.getShipTracker().getShipList();

			
			assertEquals(4, shipList.size());
		
			
			for(Ship ship : shipList){
				assertTrue(ship.getShipType().getShipTypeId().equals("STC") || ship.getShipType().getShipTypeId().equals("STM") || ship.getShipType().getShipTypeId().equals("BOP") || ship.getShipType().getShipTypeId().equals("CWS") || ship.getShipType().getShipTypeId().equals("BWS"));
			}
			
	 		
	 		Map<Integer,Player> playerList = testGame2.getUserTracker().getPlayerMap();
	 		
	 		assertEquals(2, playerList.size()); 
	 		int i;
	 		for(i = 0; i < playerList.size(); i++){
	 			assertTrue(playerList.get(i).getAffiliation().equals("FED") || playerList.get(i).getAffiliation().equals("KLI"));
	 			assertTrue(playerList.get(i).getPlayerId().equals("dax") || playerList.get(i).getPlayerId().equals("kirk"));
	 			assertTrue( playerList.get(i).getShip() == 1 || playerList.get(i).getShip() == 0);
	 		}
			

			
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}		
	}
	
	
	
	@Test
	public void getPlayerMapTest(){
		//Map<Integer, Player> getPlayerMap(@Body String title) throws GameNotFoundException;
		service.resetForTest();
		
		try {
			Map<Integer, Player> map = service.getPlayerMap(1);
			assertTrue(map != null);
			assertEquals(2, map.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		try {
			Map<Integer, Player> map2 = service.getPlayerMap(0);
			assertTrue(map2 != null);
			assertEquals(2, map2.size());
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void getUniverseTest(){
		//public Universe getUniverse(@RequestBody String title) throws GameNotFoundException
		service.resetForTest();
		
		try {
			Universe uni1 = service.getUniverse(1);
			assertTrue(uni1 != null);
			assert(uni1.getSector(4, 4) != null);
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		try {
			Universe uni2 = service.getUniverse(0);
			assertTrue(uni2 != null);
			assert(uni2.getSector(4, 4) != null);
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void getGameTest(){
		//public Game getGame(@RequestBody String title) throws GameNotFoundException
		service.resetForTest();
		
		try {
			Game game1 = service.getGame(1);
			assertTrue(game1 != null);
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		
		
	}
			
			
}
