package lobbyserver;
import com.google.gson.Gson;

public class PlayerController{
	Gson gson;
	public PlayerController(){
		gson = new Gson();
	}
	//login指示とJsonがくる
	public void loginFromPlayer(Player player) {
		System.out.println("sendDBserver");
		LobbyServerMain.wsDBManager.sendMessage(gson.toJson(new DBMessage("login",player)));
	}
	public void loginFromDBServer(Player player,boolean loginResult) {
		System.out.println("startMethod[loginFromDBserver]");
		System.out.println("map確認 : "+PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()));
		if(loginResult == true) {
			PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()),gson.toJson(new LobbyMessage("login result",player)));
		}else {
			PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()),gson.toJson(new LobbyMessage("login result",null)));
			PlayerEndPoint.currentConnectedPlayer.remove(player.getuserID());
		}
	}
	public void signUpFromPlayer(Player player) {
		System.out.println("sign up---"+player);
		LobbyServerMain.wsDBManager.sendMessage(gson.toJson(new DBMessage("sign up",player)));
	}
	
	public void signUpFromDBServer(Player player,boolean loginResult) {
		System.out.println("startMethod[loginFromDBserver]");
		System.out.println("map確認 : "+PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()));
		if(loginResult == true) {
			PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()),gson.toJson(new LobbyMessage("sign up result",player)));
		}else {
			PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()),gson.toJson(new LobbyMessage("sign up result",null)));
			PlayerEndPoint.currentConnectedPlayer.remove(player.getuserID());
		}
	}
	
	public void matching(String userID) {
		PlayerEndPoint.standByQueue.add(userID);
	}
	public void battleRecord(Player player) {
		LobbyServerMain.wsDBManager.sendMessage(gson.toJson(new DBMessage("battleRecord",player)));
	}
	public void battleRecordFromDBServer(Player player) {
		PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()), gson.toJson(new LobbyMessage("battleRecord result",player)));
	}
	
	public void cancel(String userID) {
		boolean cancel = PlayerEndPoint.standByQueue.remove(userID);
		//cancelできた
		if(cancel == true) {
			PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(userID),gson.toJson(new LobbyMessage("cancel result",null)));
		}
		//cancelがfalseの場合、すでにマッチング完了通知が行ってしまっている.今回は何もしない
	}
	public void rule(Player player) {
		player.setpassword("先手のプレイヤーが黒の石を、後手のプレイヤーが白の石を交互に置いていき、挟んだ石をひっくり返すことで自分の色を増やす。\n石には得点、マスにはウェイトがついており、盤面上の石の得点×石が置かれたマスのウェイトの合計がプレイヤーの得点となる。\nプレイ中は特殊カードを使用することができ、その効果によってゲームを有利に進めることができる。\n盤面が埋まるまでこれを繰り返し、得点が高いプレイヤーが勝利となる。\n ");
		PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(player.getuserID()),gson.toJson(new LobbyMessage("rule result",player)));
	}
	
}