package cpsc3720.contactserver;
/**
 * Represents a cell on the game board, holds a ship, base or is empty
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class Cell {
	
	private Ship ship;
	private Base base;
	private String affil;
	
	public String getAffil() {
		return affil;
	}

	public void setAffil(String affil) {
		this.affil = affil;
	}

	private TypeEnum type;
	
	public Cell(){
		/*ShipType shipType1 = new ShipType("STC" ,"Starship", "Constitution", "BAJ", 3000 , 9, 500, "PHAS", "PTOR", 10);
		Location loc1 = new Location(2,4,6,5);
		ship = new Ship(0,shipType1, loc1, 2800, 8, AlertType.RED, 300);

		type = TypeEnum.SHIP;*/
		type = TypeEnum.EMPTY;

	}

	public Ship getShip() {
		return this.ship;
	}
	
	public int leave(){
		type = TypeEnum.EMPTY;
		ship = null;
		base = null;
		return 1;
	}


	@Override
	public String toString() {
		return "Cell [affil=" + affil + ", type=" + type + "]";
	}

	public void setShip(Ship ship) {
		type = TypeEnum.SHIP;
		this.ship = ship;
	}

	public void setShip(String newAffil) {
		type = TypeEnum.SHIP;
		this.affil = newAffil;
	}
	
	public TypeEnum getType() {
		return type;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		type = TypeEnum.BASE;
		this.base = base;
	}

	
}