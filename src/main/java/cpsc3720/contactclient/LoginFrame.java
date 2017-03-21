package cpsc3720.contactclient;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cpsc3720.contactserver.User;
import cpsc3720.contactservice.ContactService;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
/**
 * The initial JFrame of the program, this window gathers server location data and user data to log in to the game.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class LoginFrame extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField pwdPassword;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_4;

	/**
	 * Create the frame.
	 */
	public LoginFrame(final String globalFont){// throws UnknownHostException{
		setResizable(false);
		setTitle("Login or Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 480);
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);
		
		// Server IP
		JLabel lblServerIp = new JLabel("Server IP:");
		lblServerIp.setFont(Font.getFont(globalFont));
		lblServerIp.setBounds(225, 12, 68, 15);
		getContentPane().add(lblServerIp);
		int connToServer = 140;
		
		textField = new JTextField();
		textField.setText("localhost");
		textField.addFocusListener(new FocusListener() {
            @Override public void focusLost(final FocusEvent pE) {textField.select(0, 0);}
            @Override public void focusGained(final FocusEvent pE) {textField.selectAll();}
        });
		//textField.setFont(Font.getFont(globalFont));
		GhostText ipAddress = new GhostText(textField, "ex: localhost");
		textField.setBounds(311, 10, connToServer, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		// Server Port
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(Font.getFont(globalFont));
		lblPort.setBounds(258, 39, 35, 15);
		getContentPane().add(lblPort);
		
		textField_1 = new JTextField();
		textField_1.setText("8080");
		textField_1.addFocusListener(new FocusListener() {
            @Override public void focusLost(final FocusEvent pE) {textField_1.select(0, 0);}
            @Override public void focusGained(final FocusEvent pE) {textField_1.selectAll();}
        });
		//textField_1.setFont(Font.getFont(globalFont));
		GhostText portNum = new GhostText(textField_1, "ex: 8080");
		textField_1.setBounds(311, 37, connToServer, 19);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		// Separator (x-pos, y-pos, width, height)
		// 1
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 68, 658, 9);
		getContentPane().add(separator);
		// 2
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 75, 295, 9);
		getContentPane().add(separator_1);
		// 3
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(303, 75, 9, 379);
		separator_2.setOrientation(1);
		getContentPane().add(separator_2);
		// 4
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(295, 75, 9, 379);
		getContentPane().add(separator_3);
		// 5
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(303, 75, 379, 9);
		getContentPane().add(separator_4);
		
		// Have an Account?
		JLabel lblHaveAnAccount = new JLabel("Have An Account?");
		lblHaveAnAccount.setFont(new Font("Inconsolata", Font.BOLD, 20));
		lblHaveAnAccount.setBounds(12, 89, 204, 29);
		getContentPane().add(lblHaveAnAccount);
		int haveAcctTxtWidth = 140;
		
		// Username
		JLabel lblUserName = new JLabel("Username:");
		lblUserName.setFont(Font.getFont(globalFont));
		lblUserName.setBounds(12, 149, 82, 15);
		getContentPane().add(lblUserName);
		
		textField_2 = new JTextField("kirk");
		textField_2.addFocusListener(new FocusListener() {
            @Override public void focusLost(final FocusEvent pE) {textField_2.select(0, 0);}
            @Override public void focusGained(final FocusEvent pE) {textField_2.selectAll();}
        });
		//textField_2.setFont(Font.getFont(globalFont));
		GhostText usrNm = new GhostText(textField_2, "Your username");
		textField_2.setBounds(112, 147, haveAcctTxtWidth, 19);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		// Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(Font.getFont(globalFont));
		lblPassword.setBounds(12, 176, 82, 15);
		getContentPane().add(lblPassword);
		
		pwdPassword = new JPasswordField("p");
		pwdPassword.addFocusListener(new FocusListener() {
            @Override public void focusLost(final FocusEvent pE) {pwdPassword.select(0, 0);}
            @Override public void focusGained(final FocusEvent pE) {pwdPassword.selectAll();}
        });
		GhostText pass = new GhostText(pwdPassword, "Your password");
		pwdPassword.setToolTipText("password");
		pwdPassword.setBounds(112, 174, haveAcctTxtWidth, 19);
		getContentPane().add(pwdPassword);
		
		// Login Button
		final JLabel lblIpOrPort = new JLabel("Connection Failed: Incorrect IP or Port");
		lblIpOrPort.setVisible(false);
		lblIpOrPort.setForeground(Color.RED);
		lblIpOrPort.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblIpOrPort.setBounds(27, 375, 300, 15);
		getContentPane().add(lblIpOrPort);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(Font.getFont(globalFont));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				String connAddr = "http://" + textField.getText() + ":" + textField_1.getText();
				String currPassword = new String(pwdPassword.getPassword());
				try {
					final ContactService service = new RestAdapter.Builder()
							.setEndpoint(connAddr)
							.build()
							.create(ContactService.class);
					System.out.println("Connecting to " + connAddr);
					System.out.println("Username: " + textField_2.getText() + " | Password: " + currPassword);
					User tempUser = new User(-1, textField_2.getText(), "", currPassword, false);
					User currUser = service.loginSuccessful(tempUser);
					if(currUser.getIdNum() != -1){
						JoinGameFrame jgFrame = new JoinGameFrame(globalFont, service, currUser);
						jgFrame.setVisible(true);
						dispose();
					}else{
						System.out.println("Username or Password was incorrect!");
						System.out.println("Can't login as " + textField_2.getText());
						lblIpOrPort.setText("Login Failed: Wrong Username or Pass");
						lblIpOrPort.setVisible(true);
						
					}
				} catch (Exception e) {
					System.out.println("Server IP or Port was incorrect!");
					System.out.println("Can't connect to " + connAddr);
					System.out.println(e);
					lblIpOrPort.setText("Connection Failed: Incorrect IP or Port");
					lblIpOrPort.setVisible(true);
				}
			}
		});
		btnLogin.setBounds(151, 404, 117, 25);
		getContentPane().add(btnLogin);
		
		// Need an Account?
		JLabel lblNeedAnAccount = new JLabel("Need An Account?");
		lblNeedAnAccount.setFont(new Font("Inconsolata", Font.BOLD, 20));
		lblNeedAnAccount.setBounds(315, 89, 205, 29);
		getContentPane().add(lblNeedAnAccount);
		int needAcctTxtWidth = 160;
		
		// New Username
		JLabel lblNewUsername = new JLabel("New Username:");
		lblNewUsername.setFont(Font.getFont(globalFont));
		lblNewUsername.setBounds(319, 149, 129, 15);
		getContentPane().add(lblNewUsername);
		
		textField_3 = new JTextField();
		textField_3.setFont(Font.getFont(globalFont));
		textField_3.setBounds(466, 147, needAcctTxtWidth, 19);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		// New Password
		JLabel lblNewPassword = new JLabel("New Password:");
		lblNewPassword.setFont(Font.getFont(globalFont));
		lblNewPassword.setBounds(319, 176, 129, 15);
		getContentPane().add(lblNewPassword);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("password");
		passwordField.setBounds(466, 201, needAcctTxtWidth, 19);
		getContentPane().add(passwordField);
		
		// Retype New Password
		JLabel lblRetypePassword = new JLabel("Retype Password:");
		lblRetypePassword.setFont(Font.getFont(globalFont));
		lblRetypePassword.setBounds(319, 203, 129, 15);
		getContentPane().add(lblRetypePassword);

		passwordField_1 = new JPasswordField();
		passwordField_1.setToolTipText("password");
		passwordField_1.setBounds(466, 174, needAcctTxtWidth, 19);
		getContentPane().add(passwordField_1);
		
		// New Email
		JLabel lblNewEmail = new JLabel("New Email:");
		lblNewEmail.setFont(Font.getFont(globalFont));
		lblNewEmail.setBounds(319, 230, 129, 15);
		getContentPane().add(lblNewEmail);
		
		textField_4 = new JTextField();
		//textField_4.setFont(Font.getFont(globalFont));
		GhostText mail = new GhostText(textField_4, "test@mail.com");
		textField_4.setColumns(10);
		textField_4.setBounds(466, 228, needAcctTxtWidth, 19);
		getContentPane().add(textField_4);
		
		// Create Account Button
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setFont(Font.getFont(globalFont));
		btnCreateAccount.setBounds(485, 404, 142, 25);
		getContentPane().add(btnCreateAccount);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnExit.setFont(null);
		btnExit.setBounds(27, 404, 117, 25);
		getContentPane().add(btnExit);
	}
}
