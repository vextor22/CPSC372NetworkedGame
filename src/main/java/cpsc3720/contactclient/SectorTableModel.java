package cpsc3720.contactclient;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import cpsc3720.contactserver.Sector;
import cpsc3720.contactserver.Universe;

/**
 * Sector table model is used to display the game's sector data in a JTable on MainGameScreen
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class SectorTableModel extends AbstractTableModel{

	private static SectorTableModel mySectorTableModel;
	Collection<Integer> sectorData;
	private String[][] sector = new String[8][8];
	
	SectorTableModel(){
		super();
		sectorData = new ArrayList<Integer>();	
	}
	
	public static SectorTableModel getInstance(){
		if(mySectorTableModel == null){
			mySectorTableModel = new SectorTableModel();
		}
		
		return mySectorTableModel;
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
	public String getValueAt(int x, int y) {
		//needs to return an object that you want to be displayed
				
		// TODO Auto-generated method stub
		return sector[x][y];
	}
	
	//might be useful to have a set sector method that pulls from the big 2D array
	public void setSector(int col, int row, Universe univ){
		if(row >= 0 || col >= 0){
		//int[][] sector = new int[8][8];
		int counter = 0;
		//Universe univ = new Universe(); //this will have to eventually be moved or else a new universe will get created each time this function is called
		
		Sector sec = univ.getSector(col, row);
			for(int x = 0; x < 8;  x++){
				for (int y = 0; y < 8; y++){
					sector[x][y] =  sec.cellValue(x, y);
					

				}
			}
		
		//need to fill the rest of this in for when we want to select other
		//sectors from Universe View
		}
	}
}
