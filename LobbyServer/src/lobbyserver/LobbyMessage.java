package lobbyserver;

public class LobbyMessage {
	private String message;
	
	private Player player;
	
	LobbyMessage(String message , Player player){
		this.message = message;
		this.player = player;
	}
	
	String getMessage(){
		return this.message;
	}
	Player getPlayer() {
		return this.player;
	}
}