package pkg;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


@ClientEndpoint
public class LobbyServerEndPoint extends EndPoints{

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("[LobbyServerEndPoint] onOpen  : " + session.getId());
	}

	@OnMessage
	public void onMessage(String message) {
		// 受信した生のメッセージ
		System.out.println("[LobbyServerEndPoint] onMessage: " + message);
		LobbyMessage lobbymessage = gson.fromJson(message,LobbyMessage.class);
		if(lobbymessage.getMessage().equals("login result")) {
			System.out.println("[LobbyServerEndPoint] login result");
			if(lobbymessage.getPlayer()==null) {
				BoundaryController.loginError();
			}else {
				Main.userID = lobbymessage.getPlayer().getuserID();
				BoundaryController.setHome();
				BoundaryController.changePanel(ScreenMode.HOME);
			}
			
		}else if(lobbymessage.getMessage().equals("already login  error")){
			BoundaryController.alreadyLoginError();
		}else if(lobbymessage.getMessage().equals("sign up result")) {
			System.out.println("[LobbyServerEndPoint] sign up result");
			if(lobbymessage.getPlayer()==null) {
				BoundaryController.signUpError();
			}else {
				Main.userID = lobbymessage.getPlayer().getuserID();
				BoundaryController.setHome();
				BoundaryController.changePanel(ScreenMode.HOME);
			}
		}else if(lobbymessage.getMessage().equals("matching result")) {
			System.out.println("[LobbyServerEndPoint] matching result");
			int roomNumber = Integer.parseInt(lobbymessage.getPlayer().getuserID());
			String color = lobbymessage.getPlayer().getpassword();
			BoundaryController.matched(roomNumber,color);
		}else if(lobbymessage.getMessage().equals("cancel result")) {
			System.out.println("[LobbyServerEndPoint] cancel result");
			BoundaryController.cancel();
		}else if(lobbymessage.getMessage().equals("battleRecord result")) {
			BoundaryController.showBattleRecord(lobbymessage.getPlayer().getpassword());
		}else if(lobbymessage.getMessage().equals("rule result")) {
			System.out.println("rule result");
			BoundaryController.showRule(lobbymessage.getPlayer().getpassword());
		}
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("[LobbyServerEndPoint] onError");
		System.out.println(t);
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("[LobbyServerEndPoint] onClose: " + session.getId());
	}

}