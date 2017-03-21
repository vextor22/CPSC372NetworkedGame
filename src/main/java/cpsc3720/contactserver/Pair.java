package cpsc3720.contactserver;
/**
 * Pair is a convenience object for sending and returning x,y coordinates. 
 * 
 * Pair should NOT be used across the client-server divide. Safe only for use internal to client or server.
 * 
 * @author Matthew Higgins
 */
public class Pair {

	int x,y;
	
	public Pair(){}
	
	public Pair(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
