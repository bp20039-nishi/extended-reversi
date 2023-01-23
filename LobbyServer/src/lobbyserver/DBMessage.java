package lobbyserver;

public class DBMessage {
	public String message;
	public Player player;
	public boolean result;
	DBMessage(String message , Player player){
		this.message = message;
		this.player = player;
	}
}