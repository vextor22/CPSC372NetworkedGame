package cpsc3720.contactserver;
/**
 * Used for Join game table
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class TableLineItem {
	private String title;
	private int id;
	
	public TableLineItem(String inTitle, int inId){
		title = inTitle;
		id = inId;
	}

	@Override
	public String toString() {
		return title;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(int id) {
		this.id = id;
	}

}
