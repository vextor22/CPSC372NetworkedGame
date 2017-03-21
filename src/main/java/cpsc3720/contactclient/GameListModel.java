package cpsc3720.contactclient;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

import cpsc3720.contactserver.TableLineItem;
/**
 * Represents the set of games to be displayed in the JoinGameFrame window.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class GameListModel extends AbstractTableModel {

	List<TableLineItem> gameList = new ArrayList<TableLineItem>();
	
	public int addGames(List<TableLineItem> games){
		
		gameList = games;
		
		return 1;
	}
	/*
	@Override
	public TableLineItem getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return gameList.get(arg0);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return gameList.size();
	}*/

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return gameList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return gameList.get(arg0);
	}

}
