package lobbyserver;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.google.gson.Gson;


@ClientEndpoint
public class DBServerEndPoint {

	// 多分あまり行儀が良くない
	static Gson gson = new Gson();
	DBMessage dbmessage;
	PlayerController playerController = new PlayerController();
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("[DBServerEndPoint] onOpen  : " + session.getId());
	}

	@OnMessage
	public void onMessage(Session session,String message) {
	   System.out.println("[DBServerEndPoint] onMessage from (session: " + session.getId() + ") msg: " + message);
	   dbmessage = gson.fromJson(message,DBMessage.class);
	   if(dbmessage.message.equals("login result")) {
		  playerController.loginFromDBServer(dbmessage.player,dbmessage.result);
	   }else if(dbmessage.message.equals("sign up result")) {
		   playerController.signUpFromDBServer(dbmessage.player,dbmessage.result);
	   }else if(dbmessage.message.equals("battleRecord result")){
		   playerController.battleRecordFromDBServer(dbmessage.player);
	   }else {
		   
	   }
	   
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("[DBServerEndPoint] onError");
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("[DBServerEndPoint] onClose: " + session.getId());
	}

}