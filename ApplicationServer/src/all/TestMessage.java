package all;

import javax.websocket.Session;

public class TestMessage {
	//private ArrayList<Integer> cardID;
	//private Color color = Color.White;
	//private PlayerTime time;
	private Session session;
	
	TestMessage(Session s) {
		this.session = s;
		// this.time = new PlayerTime(s);
	}
}