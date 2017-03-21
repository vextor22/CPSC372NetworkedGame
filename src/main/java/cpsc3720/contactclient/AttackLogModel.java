package cpsc3720.contactclient;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import cpsc3720.contactserver.TableLineItem;

public class AttackLogModel extends AbstractTableModel {

	List<String> attackLogList = new ArrayList<String>();
		
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return attackLogList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return attackLogList.get(arg0);
	}

	public void addLog(String attackLogString) {
		// TODO Auto-generated method stub
		attackLogList.add(0,attackLogString);
	}
/*
	public void addRow(String string) {
		// TODO Auto-generated method stub
		
	}
*/
}
