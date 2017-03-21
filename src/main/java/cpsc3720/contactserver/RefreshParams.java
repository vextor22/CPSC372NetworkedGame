package cpsc3720.contactserver;

import org.springframework.web.bind.annotation.RequestBody;

import cpsc3720.contactclient.SectorTableModel;
import cpsc3720.contactclient.UniverseTableModel;

/**
 * RefreshParams is used to collect data from the client in order pass to the server so we can refresh the screen
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 * 
 * 
 */


public class RefreshParams {
	
	SectorTableModel sectModel;
	UniverseTableModel univModel;
	int gameName;
	
	public RefreshParams() {}
	
	public RefreshParams(SectorTableModel sectModel, UniverseTableModel univModel, int gameName) {
		super();
		this.sectModel = sectModel;
		this.univModel = univModel;
		this.gameName = gameName;
	}
	
	public int getGameName() {
		return gameName;
	}

	public void setGameName(int gameName) {
		this.gameName = gameName;
	}

	public SectorTableModel getSectModel() {
		return sectModel;
	}
	
	public void setSectModel(SectorTableModel sectModel) {
		this.sectModel = sectModel;
	}
	
	public UniverseTableModel getUnivModel() {
		return univModel;
	}
	
	public void setUnivModel(UniverseTableModel univModel) {
		this.univModel = univModel;
	}

}
