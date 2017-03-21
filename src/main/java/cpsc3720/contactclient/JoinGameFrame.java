package cpsc3720.contactclient;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cpsc3720.contactserver.GameInstanceData;
import cpsc3720.contactserver.Player;
import cpsc3720.contactserver.TableLineItem;
import cpsc3720.contactserver.User;
import cpsc3720.contactservice.ContactService;
import retrofit.RestAdapter;

/**
 * JoinGameFrame is used to select a game instance from the server. The user can also restore a game from a game state file in this window.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class JoinGameFrame extends JFrame {

	private JPanel contentPane;
	private TableLineItem selectedGame = null;

	/**
	 * Create the frame.
	 */
	public JoinGameFrame(final String globalFont, final ContactService service, final User currUser) {
		setResizable(false);
		setTitle("Select a Game to Play");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCurrentGameInstances = new JLabel("Current Game Instances");
		lblCurrentGameInstances.setFont(new Font(globalFont, Font.BOLD, 24));
		lblCurrentGameInstances.setBounds(12, 12, 324, 29);
		contentPane.add(lblCurrentGameInstances);
		
		//DefaultListModel listModel = new DefaultListModel();
		
		//listModel.addElement("Star Trek Forever");
		//listModel.addElement("Civil War");
		
	
		//String[] data = { "Star Trek Forever", "Civil War" };
		final GameListModel listModel = new GameListModel();
		listModel.addGames(service.getGameList());
		System.out.println(service.getGameList());
		// JList declaration
		final JTable list = new JTable(listModel);
		list.setFont(Font.getFont(globalFont));
		
		list.addMouseListener(new java.awt.event.MouseAdapter() {
			//selectedGame = list.getSelectedValue();
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if(list.getSelectedRow() >= 0)
					selectedGame = (TableLineItem)list.getValueAt(list.getSelectedRow(), 0);
			}
        });
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/*
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        */
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(12, 53, 634, 319);
		contentPane.add(list);
		
		JButton btnJoinGame = new JButton("Join Game!");
		btnJoinGame.setFont(Font.getFont(globalFont));
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try{
					if(selectedGame != null){
						//=====================================================
						System.out.println(currUser.getUserName());
						Player currPlayer = service.userCanPlay(new GameInstanceData(currUser, selectedGame.getId()));
						if(currPlayer.getId() != -1){
							System.out.println(selectedGame.getId());
							
							MainGameScreen mgFrame = new MainGameScreen(selectedGame.getId(), globalFont, service, currUser, currPlayer);
							mgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							mgFrame.setSize(1024,768);
							mgFrame.setVisible(true);
							dispose();
						}
					}
				}
				catch(Exception e){
					System.out.println("Could not join the game");
					System.out.println(e);
				}
			}
		});
		
		btnJoinGame.setBounds(505, 398, 117, 25);
		contentPane.add(btnJoinGame);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setFont(Font.getFont(globalFont));
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame lgFrame = new LoginFrame(globalFont);
				lgFrame.setVisible(true);
				dispose();
			}
		});
		btnDisconnect.setBounds(36, 398, 117, 25);
		contentPane.add(btnDisconnect);
		
		JButton btnRestoreGame = new JButton("Restore Game");
		
		btnRestoreGame.setBounds(256, 398, 134, 25);
		btnRestoreGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Restore Game Clicked");
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(JoinGameFrame.this);
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					try {
						List<String> lines =  Files.readAllLines(file.toPath(),StandardCharsets.US_ASCII);
						StringBuilder stringer = new StringBuilder();
						for(String s : lines){
							stringer.append(s);
						}
						System.out.println(stringer.toString());
						service.restoreGame((ArrayList<String>) lines);
						listModel.addGames(service.getGameList());
						listModel.fireTableDataChanged();
						System.out.println("listmodel set");
						} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		contentPane.add(btnRestoreGame);
		
		JLabel lblLoggedInAs = new JLabel("Logged In As:");
		lblLoggedInAs.setBounds(437, 23, 96, 15);
		contentPane.add(lblLoggedInAs);
		
		JLabel currUserLoggedIn = new JLabel(currUser.getUserName());
		float hue = (float)1.4;
		float sat = (float)1;
		float bri = (float)0.65;
		currUserLoggedIn.setForeground(Color.getHSBColor(hue, sat, bri));
		currUserLoggedIn.setBounds(545, 23, 101, 15);
		contentPane.add(currUserLoggedIn);
	}
}
