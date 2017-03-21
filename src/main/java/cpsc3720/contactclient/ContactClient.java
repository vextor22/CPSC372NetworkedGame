package cpsc3720.contactclient;
//Test comment, please ignore. -Ella
/**
 * Launches the initial window.
 * 
 * @author Matthew Higgins
 * @author Ella Kokinda
 * @author Chris levesque
 * @author Keven "Beans" Hayes
 */
public class ContactClient {
	public static void main(String[] args) {
		final String globalFont = "Inconsolata";
			LoginFrame logFrame = new LoginFrame(globalFont);
			logFrame.setVisible(true);
	}
}
