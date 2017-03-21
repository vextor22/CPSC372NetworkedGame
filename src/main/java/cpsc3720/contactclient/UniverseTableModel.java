package cpsc3720.contactclient;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import cpsc3720.contactserver.GameNotFoundException;
import cpsc3720.contactserver.Player;
import cpsc3720.contactserver.Universe;
import cpsc3720.contactservice.ContactService;
import retrofit.RestAdapter;


/**
 * Responsible for the Universe JTable data model on MainGameScreen.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class UniverseTableModel extends AbstractTableModel{

	
	private static final long serialVersionUID = -3647780584869878205L;

	private static UniverseTableModel myUnivTableModel;
	private String[][] universe = new String[8][8];//each sector will have a int,int
	Collection<Integer> univData;
	private Player thePlayer;
	
	UniverseTableModel() {}
	
	UniverseTableModel(Player thePlayer){
		super();
		univData = new ArrayList<Integer>();
		this.thePlayer = thePlayer;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	//this wil return what will be seen in the cell
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return universe[arg0][arg1];
	}
	
	//needs to be similar to setSector but instead of displaying
	public void setUniverseTable(Universe univ, ContactService service){
		
		//go through each sector and then set what the sector needs to be.
		//ArrayList<String> Data = new ArrayList<String>();
		
		int foe = 0, friend = 0;
		
		
		
		for (int x = 0; x < 8; x++){
			for (int y = 0; y < 8; y++){
				//System.out.println(univ.);
				//univ.getSector(x,y);
				
				try {
					Universe testUni = service.getUniverse(0);
					assert(univ.getSector(x, y) != null);
					assert(thePlayer != null);
					assert(universe[x][y] != null);
					universe[x][y] = univ.getSector(x,y).sectorSummary(thePlayer);
					
					
				} catch (GameNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
}