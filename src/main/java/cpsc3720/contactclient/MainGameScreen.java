package cpsc3720.contactclient;



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import cpsc3720.contactserver.AIShipMoveParams;
import cpsc3720.contactserver.AlertLevelParams;

import cpsc3720.contactserver.AssociationsParameter;

import cpsc3720.contactserver.AttackLogObject;
import cpsc3720.contactserver.FindShipParams;
import cpsc3720.contactserver.FireParams;
import cpsc3720.contactserver.Game;
import cpsc3720.contactserver.GameNotFoundException;
import cpsc3720.contactserver.Location;
import cpsc3720.contactserver.MoveShipParams;
import cpsc3720.contactserver.NoObjectInCell;
import cpsc3720.contactserver.Pair;
import cpsc3720.contactserver.Player;
import cpsc3720.contactserver.RefreshParams;
import cpsc3720.contactserver.Sector;
import cpsc3720.contactserver.Ship;
import cpsc3720.contactserver.ShipNotFoundException;
import cpsc3720.contactserver.TableLineItem;
import cpsc3720.contactserver.Universe;
import cpsc3720.contactserver.User;
import cpsc3720.contactservice.ContactService;
import retrofit.RestAdapter;
import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JScrollPane;

/**
 * MainGameScreen generates the JFrame window which is used to interact with the game state. This is the main application window.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class MainGameScreen extends JFrame {

	private JPanel contentPane;
	private Universe universe;
	private BufferStrategy strategy;
	private final Pair currentSector = new Pair(0,0);
	private boolean navFlag = false;
	private JTable sectorTable;
	private int cellSelectedCol;
	private int cellSelectedRow;
	private int sectorSelectedCol;
	private int sectorSelectedRow;

	/**
	 * Generates main game frame. This window holds the action controls, and data displays for the user.
	 * 
	 * @param gameName - name of the game as an integer ID, unique
	 * @param globalFont - determines font for game
	 * @param service - service instance to be used
	 * @throws ShipNotFoundException 
	 */
	public MainGameScreen(final int gameName, final String globalFont, final ContactService service, final User currUser, final Player currPlayer) throws ShipNotFoundException {
		//setTitle("Test");	
		List<TableLineItem> gameList = service.getGameList();
		setTitle("Game: " + gameList.get(gameName).getTitle() + "  |  Player: " + currUser.getUserName()); // Needs to have try/catch for title
		
		final JButton scanButton = new JButton("Scan");
/*
		final Ship aiShip1;
		final Ship aiShip2;
		final AttackLogObject aiShip1Log;
		final AttackLogObject aiShip2Log;
*/
		
		final UniverseTableModel univModel;
		
		
		Ship tempShip;
	
			tempShip = service.getShipByID(new FindShipParams(gameName, currPlayer.getShip()));
			int currSecX = tempShip.getLocation().getSXPos();
			int currSecY = tempShip.getLocation().getSYPos();

	
		
		final JLabel lblSpecificSector = new JLabel("(" + currSecY + ", " + currSecX + ")");
		
		System.out.println("reset for test");

		/*
		try {
			setTitle(service.getGame(gameName).getTitle());
		} catch (GameNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
		final SectorTableModel sectorModel = new SectorTableModel();
		final JTable universeTable = new JTable();
		univModel = new UniverseTableModel(currPlayer);
		//============================================================================================================================================
		
		//universeTable.setDefaultRenderer(String.class, centerRenderer);
		
		
		JLabel lblNewLabel = new JLabel("Ship View: ");
		
		// fill ship name
		final JLabel selectedShipName = new JLabel("");
		
		JLabel lblEnergyLevel = new JLabel("Energy Level: ");
		JLabel lblTorpedoCount = new JLabel("Torpedo Count: ");
		JLabel lblShieldCount = new JLabel("Shield Count: ");
		JLabel lblAlertLevel = new JLabel("Alert Level: ");
		JLabel lblClass = new JLabel("Class: ");
		JLabel affiliationLabel = new JLabel("Affiliation: ");
		
		
		// fill these fields
		final JLabel lblShipAffiliation = new JLabel(" ");
		final JLabel lblShipClass = new JLabel(" ");
		final JLabel lblShipAlertness = new JLabel(" ");
		final JLabel lblShipShieldCount = new JLabel(" ");
		final JLabel lblShipTorpedoCount = new JLabel(" ");
		final JLabel lblShipEnergyLvl = new JLabel(" ");
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel attackLogPanel = new JPanel();
		attackLogPanel.setBackground(Color.WHITE);
		attackLogPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		attackLogPanel.setBounds(12, 509, 535, 213);
		contentPane.add(attackLogPanel);
		attackLogPanel.setLayout(null);
		
		JLabel lblAttackLog = new JLabel("Attack Log");
		lblAttackLog.setFont(new Font(globalFont, Font.BOLD, 13));
		lblAttackLog.setBounds(12, 13, 82, 16);
		attackLogPanel.add(lblAttackLog);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		separator.setBounds(0, 38, 535, 2);
		attackLogPanel.add(separator);
		
		JPanel greyPanel = new JPanel();
		greyPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		greyPanel.setBackground(Color.LIGHT_GRAY);
		greyPanel.setBounds(0, 0, 535, 39);
		attackLogPanel.add(greyPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 513, 147);
		attackLogPanel.add(scrollPane);
		final AttackLogModel attackModel = new AttackLogModel();
		JTable attackLogList = new JTable(attackModel);
		attackLogList.setTableHeader(null);
		attackLogList.setShowVerticalLines(false);
		attackLogList.setShowGrid(false);
		attackLogList.setRowSelectionAllowed(false);
		attackLogList.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(attackLogList);
		attackLogList.setFont(Font.getFont(globalFont));
		attackLogList.setRowHeight(30);
		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setBorder(null);
		actionsPanel.setBounds(548, 546, 458, 176);
		contentPane.add(actionsPanel);
		actionsPanel.setLayout(null);
		

		
		/***********************************************
		 * Universe View
		 ***********************************************/
		
		try {
			Game theGame = service.getGame(gameName);
			System.out.println("checking for game: "+ gameName);
			Universe thisUniv = service.getUniverse(gameName);
					
			JPanel universeViewPanel = new JPanel();
			universeViewPanel.setBounds(756, 46, 248, 248);
			contentPane.add(universeViewPanel);
			universeViewPanel.setLayout(null);
			
			//Player player = service.getPlayerMap(gameName).get(0);
			System.out.println(currPlayer.getPlayerId());
			System.out.println(currPlayer.getAffiliation());
			
			
			univModel.setUniverseTable(thisUniv, service);
				
			
			// Universe Table Listener =============================================================	
			universeTable.setCellSelectionEnabled(true);
			universeTable.setFont(new Font(globalFont, Font.PLAIN, 10));
			universeTable.setBorder(new LineBorder(new Color(0, 0, 0)));
			universeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			universeTable.setCellSelectionEnabled(true);
		
			universeTable.setRowHeight(31);
			universeTable.setBounds(0, 0, 248, 248);
			universeViewPanel.add(universeTable);
			universeViewPanel.setFont(Font.getFont(globalFont));
	
			universeTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					int row = universeTable.getSelectedRow();
					int col = universeTable.getSelectedColumn();

					Location loc = new Location( row, col, cellSelectedRow, cellSelectedCol);
					sectorSelectedCol = col;
					sectorSelectedRow = row;

						try {
							
							Universe universe = service.getUniverse(gameName);
							Location target = new Location( row, col, cellSelectedRow, cellSelectedCol);
						if(navFlag){
							navFlag = false;
							//WARP IN HERE
							if(service.warpShip(new MoveShipParams(currPlayer.getShip(), gameName, target)) == 1){
							try {
								univModel.setUniverseTable(service.getUniverse(gameName), service);
								sectorModel.setSector(currentSector.getX(),currentSector.getY(), service.getUniverse(gameName));
							} catch (GameNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							sectorModel.fireTableDataChanged();
							univModel.fireTableDataChanged();
							try {
								universe = service.getUniverse(gameName);
								int currSelRow = row;
								int currSelCol = col;
								
								currentSector.setX(currSelRow);
								currentSector.setY(currSelCol);
								System.out.println("universe selected cell: " + currSelCol + "," + currSelRow);
								sectorModel.setSector(currSelRow, currSelCol, universe);
								sectorModel.fireTableDataChanged();

								lblSpecificSector.setText("(" + currSelCol + ", " + currSelRow + ")");
								/*
								currentSector.setX(universeTable.getSelectedRow());
								currentSector.setY(universeTable.getSelectedColumn());
								System.out.println("universe selected cell: " + universeTable.getSelectedColumn() + "," + universeTable.getSelectedRow());
								sectorModel.setSector(universeTable.getSelectedRow(), universeTable.getSelectedColumn(), universe);
								sectorModel.fireTableDataChanged();

								lblSpecificSector.setText("(" + currentSector.getX() + ", " + currentSector.getY() + ")");
								*/
							} catch (GameNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							}
								
						}
						} catch (GameNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
						
					
					System.out.println("Selected Cell X: " + col);
					System.out.println("Selected Cell Y: " + row);
					//sectorTableSelected.setX(sectorTable.getSelectedColumn());
	            	//sectorTableSelected.setY(sectorTable.getSelectedRow());
				}
			});
			
			JButton refreshButton = new JButton("Refresh");
			refreshButton.setFont(Font.getFont(globalFont));
			refreshButton.setBounds(279, 75, 130, 25);
			actionsPanel.add(refreshButton);
			refreshButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//RefreshParams refreshParams = new RefreshParams(sectorModel, univModel, gameName);
					//service.refreshMGS(refreshParams);
					try {
						univModel.setUniverseTable(service.getUniverse(gameName), service);
						sectorModel.setSector(currentSector.getX(), currentSector.getY(), service.getUniverse(gameName));
					} catch (GameNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					sectorModel.fireTableDataChanged();
					univModel.fireTableDataChanged();
					System.out.println("User refreshed screen.");
				}
			});
			
			univModel.fireTableDataChanged();
			universeTable.setModel(univModel);
			
			/***********************************************
			 * End Universe View
			 ***********************************************/
			
			/***********************************************
			 * Sector View
			 ***********************************************/
			tempShip = service.getShipByID(new FindShipParams(gameName, currPlayer.getShip()));
			System.out.println(tempShip.getLocation().toString());
			currSecX = tempShip.getLocation().getSXPos();
			currSecY = tempShip.getLocation().getSYPos();
			sectorSelectedCol = currSecY;
			sectorSelectedRow = currSecX;
			System.out.println("currSecX = " + currSecX + "  |  currSecY = " + currSecY);
			sectorModel.setSector(currSecX, currSecY, thisUniv); // start selected

				currentSector.setX(currSecX);
				currentSector.setY(currSecY);
	
				// Sector Table
				sectorTable = new JTable(sectorModel){
				    @Override
				    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				    	//System.out.println("Made it to  @Override prepareRenderer -----------------------------------------------------------------------------------------------");
				        Component comp = super.prepareRenderer(renderer, row, col);
				        //Object value = getModel().getValueAt(row, col);

				        //System.out.println("secSelCol = " + sectorSelectedRow + " | secSelRow = " + sectorSelectedCol + " | col = " + row + " | row = " + col);
				        int value = service.getAssociationStatus(new AssociationsParameter(gameName, currPlayer, new Location(sectorSelectedRow, sectorSelectedCol, row, col)));

				        if (true /*getSelectedRow() == row*/) {
				            if(value == 1) comp.setBackground(new java.awt.Color(88, 191, 229));
				            else if(value == 2) comp.setBackground(new java.awt.Color(76, 206, 115));
				            else if(value == 3) comp.setBackground(new java.awt.Color(229, 88, 88));
				            else comp.setBackground(sectorTable.getBackground());
				        }
				        return comp;
				    }
				};
				sectorTable.setRowSelectionAllowed(false);
				sectorTable.setFont(new Font(globalFont, Font.PLAIN, 16));
				sectorTable.setCellSelectionEnabled(true);
				
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
				for(int i = 0; i < universeTable.getColumnCount(); i++){
					sectorTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
				}//------------------------
				
				sectorTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
				sectorTable.addMouseListener(new java.awt.event.MouseAdapter() {
					@Override
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						int row = sectorTable.getSelectedRow();
						int col = sectorTable.getSelectedColumn();
						cellSelectedCol = col;
						cellSelectedRow = row;
						//int sx, int sy, int px, int py
						Location target = new Location( currentSector.getX(), currentSector.getY(), cellSelectedRow, cellSelectedCol);
						Ship ship;
					
							System.out.println("navFlag: " + navFlag);
						
						
					
							try {
								ship = service.getShipByID(new FindShipParams(gameName, currPlayer.getShip()));
								
								if(ship == null){
									JOptionPane.showMessageDialog(MainGameScreen.this, "Ship destroyed", "Error",
		                                    JOptionPane.ERROR_MESSAGE);
									
									JoinGameFrame jgFrame = new JoinGameFrame(globalFont, service, currUser);
									jgFrame.setVisible(true);
									dispose();
									
								}
								else{
								Location currLoc = ship.getLocation();
								System.out.println(target.sectXPos + " = " + currLoc.sectXPos + ", " +target.sectYPos + " = " + currLoc.sectYPos);
							if(navFlag){
								if(service.getUniverse(gameName).collides(target) && target.sectXPos == currLoc.sectXPos && target.sectYPos == currLoc.sectYPos){
									if(service.shipEnergyAdequate(new MoveShipParams(currPlayer.getShip(), gameName, target)) == 1){
										//System.out.println("move: " + currPlayer);
										//System.out.println("attemtping move");
										//System.out.println(target);
										service.moveShip(new MoveShipParams(currPlayer.getShip(), gameName, target));
										navFlag = false;
										
										try {
											univModel.setUniverseTable(service.getUniverse(gameName), service);
											sectorModel.setSector(currentSector.getX(),currentSector.getY(), service.getUniverse(gameName));
										} catch (GameNotFoundException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										sectorModel.fireTableDataChanged();
										univModel.fireTableDataChanged();
										
										//ADD IN AI SHIP MOVEMENT HERE
										Ship aiShip1 = service.getShipByID(new FindShipParams(gameName, 2));
										AttackLogObject aiShip1Log = service.aiShipMove(new AIShipMoveParams(aiShip1.getIdNum(), gameName));
										Ship aiShip2 = service.getShipByID(new FindShipParams(gameName, 3));
										AttackLogObject aiShip2Log = service.aiShipMove(new AIShipMoveParams(aiShip2.getIdNum(), gameName));

										attackModel.addLog(aiShip1Log.toString());
										attackModel.fireTableDataChanged();
										attackModel.addLog(aiShip2Log.toString());
										attackModel.fireTableDataChanged();
										
										sectorModel.fireTableDataChanged();
										univModel.fireTableDataChanged();
																				
									}
								}
								else{
									navFlag = false;
									//WARP IN HERE
									service.warpShip(new MoveShipParams(currPlayer.getShip(), gameName, target));
									try {
										univModel.setUniverseTable(service.getUniverse(gameName), service);
										sectorModel.setSector(currentSector.getX(),currentSector.getY(), service.getUniverse(gameName));
									} catch (GameNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									sectorModel.fireTableDataChanged();
									univModel.fireTableDataChanged();
									
									//ADD IN AI SHIP MOVEMENT HERE
									Ship aiShip1 = service.getShipByID(new FindShipParams(gameName, 2));
									AttackLogObject aiShip1Log = service.aiShipMove(new AIShipMoveParams(aiShip1.getIdNum(), gameName));
									Ship aiShip2 = service.getShipByID(new FindShipParams(gameName, 3));
									AttackLogObject aiShip2Log = service.aiShipMove(new AIShipMoveParams(aiShip2.getIdNum(), gameName));

									attackModel.addLog(aiShip1Log.toString());
									attackModel.fireTableDataChanged();
									attackModel.addLog(aiShip2Log.toString());
									attackModel.fireTableDataChanged();
									
									sectorModel.fireTableDataChanged();
									univModel.fireTableDataChanged();
								}
								sectorModel.fireTableDataChanged();
								univModel.fireTableDataChanged();
							}/*
							else{
								navFlag = false;
								//WARP IN HERE
								service.warpShip(new MoveShipParams(currPlayer.getShip(), gameName, target));
								try {
									univModel.setUniverseTable(service.getUniverse(gameName), service);
									sectorModel.setSector(currentSector.getX(),currentSector.getY(), service.getUniverse(gameName));
								} catch (GameNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								sectorModel.fireTableDataChanged();
								univModel.fireTableDataChanged();
							}*/
						}
						
							
							} catch (GameNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ShipNotFoundException e1) {
								
								
							}
				
							navFlag = false;
						
					
					try {
						Sector mySector = service.getUniverse(gameName).getSector(currentSector.getX(), currentSector.getY());
						
						Ship shipToShow = mySector.getCellShip(row, col);
						
						selectedShipName.setText(shipToShow.getTitle());
						lblShipAffiliation.setText(shipToShow.getShipType().getEmpireId());
						lblShipClass.setText(shipToShow.getShipType().getShipClass());
						lblShipAlertness.setText(shipToShow.getAlertLvl().name());
						lblShipShieldCount.setText(Integer.toString(shipToShow.getShieldLvl()));
						lblShipTorpedoCount.setText(Integer.toString(shipToShow.getMissileQty()));
						lblShipEnergyLvl.setText(Integer.toString(shipToShow.getEnergyLvl()));
					} catch (GameNotFoundException | NoObjectInCell e) {
					
						selectedShipName.setText("No Ship Selected ");
						lblShipAffiliation.setText(" ");
						lblShipClass.setText(" ");
						lblShipAlertness.setText(" ");
						lblShipShieldCount.setText(" ");
						lblShipTorpedoCount.setText(" ");
						lblShipEnergyLvl.setText(" ");
					}
					
					System.out.println("Selected Cell X: " + cellSelectedCol);
					System.out.println("Selected Cell Y: " + cellSelectedRow);
					//sectorTableSelected.setX(sectorTable.getSelectedColumn());
                	//sectorTableSelected.setY(sectorTable.getSelectedRow());
					navFlag = false;
				}
			});
			
			//Ship myShip = new Ship();
			//myShip.getCellShip(cellSelectedCol, cellSelectedRow);


			sectorTable.setBorder(new LineBorder(new Color(0, 0, 0)));
			
			JPanel sectorViewPanel = new JPanel();
			sectorViewPanel.setFont(Font.getFont(globalFont));
			sectorViewPanel.setBounds(54, 46, 448, 448);
			contentPane.add(sectorViewPanel);
			sectorViewPanel.setLayout(null);
			
			
			sectorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			sectorTable.setCellSelectionEnabled(true);
			
			sectorTable.setBounds(0, 0, 448, 448);
			sectorViewPanel.add(sectorTable);
			sectorTable.setRowHeight(56);
			sectorTable.setTableHeader(null);
			
			sectorModel.fireTableDataChanged();
			sectorTable.setModel(sectorModel);
			
			JPanel panel = new JPanel();
			panel.setFont(Font.getFont(globalFont));
			panel.setBounds(422, 0, 174, 46);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblStarDate = new JLabel("Star Date:");
			lblStarDate.setFont(Font.getFont(globalFont));
			lblStarDate.setBounds(8, 13, 93, 20);
			panel.add(lblStarDate);
			lblStarDate.setFont(new Font(globalFont, Font.BOLD, 16));

			/*Integer.toString(service.getGame(gameName).getDate())*/
			System.out.println("The Stardate in MGS is " + service.getGameDate(gameName));
			//JLabel updateStarDate = new JLabel(Integer.toString(theGame.getDate()));

			JLabel updateStarDate = new JLabel(Integer.toString(service.getGameDate(gameName)));
			updateStarDate.setFont(Font.getFont(globalFont));
			updateStarDate.setBounds(109, 15, 56, 16);
			panel.add(updateStarDate);
			updateStarDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
		} catch (GameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShipNotFoundException e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Ship destroyed", "Error",
                    JOptionPane.ERROR_MESSAGE);
			
			JoinGameFrame jgFrame = new JoinGameFrame(globalFont, service, currUser);
			jgFrame.setVisible(true);
			dispose();
		}
		
		//final SectorTableModel sectorModel = SectorTableModel.getInstance();
	
		
		JPanel shipViewHeaderPnl = new JPanel();
		shipViewHeaderPnl.setFont(Font.getFont(globalFont));
		shipViewHeaderPnl.setBounds(597, 320, 409, 39);
		contentPane.add(shipViewHeaderPnl);
		shipViewHeaderPnl.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		shipViewHeaderPnl.setBackground(Color.LIGHT_GRAY);
		shipViewHeaderPnl.setLayout(null);
		
		
		lblNewLabel.setFont(new Font(globalFont, Font.BOLD, 13));
		lblNewLabel.setBounds(12, 13, 108, 16);
		shipViewHeaderPnl.add(lblNewLabel);
		
		selectedShipName.setFont(Font.getFont(globalFont));
		selectedShipName.setBounds(138, 13, 212, 16);
		shipViewHeaderPnl.add(selectedShipName);
		
		JPanel lblAffiliation = new JPanel();
		lblAffiliation.setFont(Font.getFont(globalFont));
		lblAffiliation.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblAffiliation.setBounds(597, 354, 409, 189);
		contentPane.add(lblAffiliation);
		lblAffiliation.setLayout(null);
		
		lblEnergyLevel.setFont(Font.getFont(globalFont));
		lblEnergyLevel.setBounds(12, 158, 100, 16);
		lblAffiliation.add(lblEnergyLevel);
		
		lblTorpedoCount.setFont(Font.getFont(globalFont));
		lblTorpedoCount.setBounds(12, 129, 114, 16);
		lblAffiliation.add(lblTorpedoCount);
		
		
		lblShieldCount.setFont(Font.getFont(globalFont));
		lblShieldCount.setBounds(12, 100, 100, 16);
		lblAffiliation.add(lblShieldCount);
		
		
		lblAlertLevel.setFont(Font.getFont(globalFont));
		lblAlertLevel.setBounds(12, 71, 89, 16);
		lblAffiliation.add(lblAlertLevel);
		
		
		lblClass.setFont(Font.getFont(globalFont));
		lblClass.setBounds(12, 42, 54, 16);
		lblAffiliation.add(lblClass);
		
		
		affiliationLabel.setFont(Font.getFont(globalFont));
		affiliationLabel.setBounds(12, 13, 78, 16);
		lblAffiliation.add(affiliationLabel);
		
		
		lblShipAffiliation.setFont(new Font(globalFont, Font.PLAIN, 12));
		lblShipAffiliation.setBounds(138, 13, 172, 16);
		lblAffiliation.add(lblShipAffiliation);
		
		
		lblShipClass.setFont(new Font(globalFont, Font.PLAIN, 12));
		lblShipClass.setBounds(138, 42, 172, 16);
		lblAffiliation.add(lblShipClass);
		
		
		lblShipAlertness.setFont(new Font(globalFont, Font.PLAIN, 12));
		lblShipAlertness.setBounds(138, 71, 172, 16);
		lblAffiliation.add(lblShipAlertness);
		
		
		lblShipShieldCount.setFont(new Font(globalFont, Font.PLAIN, 12));
		lblShipShieldCount.setBounds(138, 100, 172, 16);
		lblAffiliation.add(lblShipShieldCount);
		
	
		lblShipTorpedoCount.setFont(new Font(globalFont, Font.PLAIN, 12));
		lblShipTorpedoCount.setBounds(138, 129, 172, 16);
		lblAffiliation.add(lblShipTorpedoCount);
		
		
		lblShipEnergyLvl.setFont(new Font(globalFont, Font.PLAIN, 12));
		lblShipEnergyLvl.setBounds(138, 158, 172, 16);
		lblAffiliation.add(lblShipEnergyLvl);
		
		JPanel sectorPanel = new JPanel();
		sectorPanel.setFont(Font.getFont(globalFont));
		sectorPanel.setBounds(55, 0, 224, 46);
		contentPane.add(sectorPanel);
		sectorPanel.setLayout(null);
		
		JLabel lblSectorView = new JLabel("Sector View:");
		lblSectorView.setFont(new Font(globalFont, Font.BOLD, 13));
		lblSectorView.setBounds(0, 13, 96, 16);
		sectorPanel.add(lblSectorView);
		

		lblSpecificSector.setFont(Font.getFont(globalFont));
		lblSpecificSector.setBounds(104, 13, 68, 16);
		sectorPanel.add(lblSpecificSector);
		
		JLabel lblUniverse = new JLabel("Universe View");
		lblUniverse.setFont(new Font(globalFont, Font.BOLD, 13));
		lblUniverse.setBounds(756, 10, 154, 16);
		contentPane.add(lblUniverse);
		/***********************************************
		 * End Sector View
		 ***********************************************/
		
		JButton navigateButton = new JButton("Navigate");
		navigateButton.setFont(Font.getFont(globalFont));
		navigateButton.setBounds(50, 25, 130, 25);
		navigateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				navFlag = true;
			}
		});
		
		actionsPanel.add(navigateButton);
		
		//Set Alert
		final JButton setAlertButton = new JButton("Set Alert");
		setAlertButton.setFont(Font.getFont(globalFont));
		setAlertButton.setBounds(50, 75, 130, 25);
		actionsPanel.add(setAlertButton);
		try {
			universe = service.getUniverse(gameName);
		} catch (GameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setAlertButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					universe = service.getUniverse(gameName);
				} catch (GameNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JPopupMenu alertPopup = new JPopupMenu();
				
				ActionListener popupActionListener = (new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						System.out.println("Alert Level Set to " + e.getActionCommand());
						AlertLevelParams params = new AlertLevelParams(e.getActionCommand().toString(), gameName, currPlayer.getShip());
						service.setAlertLevel(params);
						
						
						try {
							Ship aiShip111 = service.getShipByID(new FindShipParams(gameName, 2));
							AttackLogObject aiShip1Log = service.aiShipMove(new AIShipMoveParams(aiShip111.getIdNum(), gameName));
							Ship aiShip222 = service.getShipByID(new FindShipParams(gameName, 3));
							AttackLogObject aiShip2Log = service.aiShipMove(new AIShipMoveParams(aiShip222.getIdNum(), gameName));
							attackModel.addLog(aiShip1Log.toString());
							attackModel.fireTableDataChanged();
							attackModel.addLog(aiShip2Log.toString());
							attackModel.fireTableDataChanged();
						} catch (ShipNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						
						sectorModel.fireTableDataChanged();
						univModel.fireTableDataChanged();
						}
				});
				
				JMenuItem alertLvlGreen = new JMenuItem("Green");
				alertLvlGreen.setBackground(Color.GREEN);
				alertLvlGreen.addActionListener(popupActionListener);
				
				JMenuItem alertLvlYellow = new JMenuItem("Yellow");
				alertLvlYellow.setBackground(Color.yellow);
				alertLvlYellow.addActionListener(popupActionListener);
				
				JMenuItem alertLvlRed = new JMenuItem("Red");
				alertLvlRed.setBackground(Color.red);
				alertLvlRed.addActionListener(popupActionListener);
				
				alertPopup.add(alertLvlGreen);
				alertPopup.add(alertLvlYellow);
				alertPopup.add(alertLvlRed);
				
				alertPopup.addPopupMenuListener(new PopupListener());
				alertPopup.show(setAlertButton, 0, 25);
			}
		});
		
		// Scan Button ===================================================================
		
		scanButton.setFont(Font.getFont(globalFont));
		scanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					universe = service.getUniverse(gameName);
					int currSelRow = universeTable.getSelectedRow();
					int currSelCol = universeTable.getSelectedColumn();
					
					currentSector.setX(currSelRow);
					currentSector.setY(currSelCol);
					System.out.println("universe selected cell: " + currSelCol + "," + currSelRow);
					sectorModel.setSector(currSelRow, currSelCol, universe);
					sectorModel.fireTableDataChanged();

					lblSpecificSector.setText("(" + currSelCol + ", " + currSelRow + ")");
				} catch (GameNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		scanButton.setBounds(50, 125, 130, 25);
		actionsPanel.add(scanButton);
		
		// Quit Button
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(Font.getFont(globalFont));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				JoinGameFrame jgFrame = new JoinGameFrame(globalFont, service, currUser);
				jgFrame.setVisible(true);
				dispose();
			}
		});
		
		quitButton.setBounds(279, 125, 130, 25);
		actionsPanel.add(quitButton);
	
		JButton fireTorpedoButton = new JButton("Fire Torpedo");
		fireTorpedoButton.setFont(Font.getFont(globalFont));
		fireTorpedoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				
				// call fireTorpedo server function that takes in the selected cell.
				Pair selectedPair = new Pair(cellSelectedRow, cellSelectedCol);
				AttackLogObject attackLogString = service.fireTorpedo(new FireParams(selectedPair, gameName, currPlayer.getShip() ));
				System.out.println(attackLogString.toString());
				//attackModel.addRow(attackLogString.toString());
				attackModel.addLog(attackLogString.toString());
				attackModel.fireTableDataChanged();
				
				try {

					univModel.setUniverseTable(service.getUniverse(gameName), service);
					sectorModel.setSector(currentSector.getX(),currentSector.getY(), service.getUniverse(gameName));
				} catch (GameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sectorModel.fireTableDataChanged();
				univModel.fireTableDataChanged();
				
				//AI SHIP TURN
				try {
					Ship aiShip11 = service.getShipByID(new FindShipParams(gameName, 2));
					AttackLogObject aiShip1Log = service.aiShipMove(new AIShipMoveParams(aiShip11.getIdNum(), gameName));
					Ship aiShip22 = service.getShipByID(new FindShipParams(gameName, 3));
					AttackLogObject aiShip2Log = service.aiShipMove(new AIShipMoveParams(aiShip22.getIdNum(), gameName));
					attackModel.addLog(aiShip1Log.toString());
					attackModel.fireTableDataChanged();
					attackModel.addLog(aiShip2Log.toString());
					attackModel.fireTableDataChanged();
					
				} catch (ShipNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sectorModel.fireTableDataChanged();
				univModel.fireTableDataChanged();
			}
		});
		
		fireTorpedoButton.setBounds(279, 25, 130, 25);
		actionsPanel.add(fireTorpedoButton);
		
		JPanel keyPanel = new JPanel();
		keyPanel.setBounds(548, 46, 174, 248);
		keyPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		contentPane.add(keyPanel);
		keyPanel.setLayout(null);
		
		JPanel youKey = new JPanel();
		youKey.setBounds(12, 30, 55, 55);
		youKey.setBackground(new Color(88,191,229));
		keyPanel.add(youKey);
		
		JPanel enemyKey = new JPanel();
		enemyKey.setBounds(12, 105, 55, 55);
		enemyKey.setBackground(new Color(229,88,88));
		keyPanel.add(enemyKey);
		
		JPanel friendKey = new JPanel();
		friendKey.setBounds(12, 180, 55, 55);
		friendKey.setBackground(new Color(16,206,115));
		keyPanel.add(friendKey);
		
		JLabel lblMapKey = new JLabel("Map Key");
		lblMapKey.setBounds(65, 5, 78, 16);
		keyPanel.add(lblMapKey);
		
		JLabel lblYou = new JLabel("You");
		lblYou.setBounds(79, 53, 94, 16);
		keyPanel.add(lblYou);
		
		JLabel lblEnemy = new JLabel("Enemy");
		lblEnemy.setBounds(79, 125, 94, 16);
		keyPanel.add(lblEnemy);
		
		JLabel lblFriendly = new JLabel("Friendly");
		lblFriendly.setBounds(79, 203, 94, 16);
		keyPanel.add(lblFriendly);

	
		/***********************************************
		 * End Buttons
		 ***********************************************/
	}
	
	public void updateSectorTable(){
		
	}
}
