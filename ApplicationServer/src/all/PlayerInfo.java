package all;
import java.util.ArrayList;

import javax.websocket.Session;

public class PlayerInfo {

	private final String userID;
	private final Color color;
	private int[] stone;
	private ArrayList<Integer> cardID;
	private int battleRecord = 1;
	
	
	/* sessionが、Jsonで変換時にエラー起きる。 */
	private Session session = null;
	private PlayerTime time;
	
	PlayerInfo(String userID,Color color) {
		this.userID = userID;
		this.color = color;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setStone(int[] stone) {
		this.stone = stone;
	}
	
	public int[] getStone() {
		return this.stone;
	}
	
	public void setCardID(ArrayList<Integer> cardID) {
		this.cardID = new ArrayList<Integer>(cardID);
	}
	
	public ArrayList<Integer> getCardID() {
		return this.cardID;
	}
	
	public void setBattleRecord(int battleRecord) {
		this.battleRecord = battleRecord;
	}
	
	public int getBattleRecord() {
		return this.battleRecord;
	}

	public void setSession(Session session) {
		this.session = session;
		this.time = new PlayerTime(session);
	}
	
	public Session getSession() {
		return this.session;
	}
	
	
	public void setTime(int timeLeft) {
		this.time.setTime(timeLeft);
	}
	
	public PlayerTime getTime() {
		return this.time;
	}
	
}
