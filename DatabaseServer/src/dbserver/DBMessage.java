package dbserver;

public class DBMessage {
	public String message;
	public Player player;
	//dastabaseの照合or登録の結果を返す.
	public boolean result;
	DBMessage(String message , Player player){
		this.message = message;
		this.player = player;
	}
}
