package lobbyserver;

public class MessageLobApp {
	//ロビーサーバとの間でルーム作成時の通信と試合終了時の通信で使う
	/*
	 * 0 : ルーム作成
	 * 1 : ゲーム終了
	 * */
	int roomNumber;
	String userBlack;
	String userWhite;
	String userWinner;
	
	public MessageLobApp(int roomNumber) {
		this.roomNumber = roomNumber;
	}
}
