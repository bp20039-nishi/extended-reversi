package pkg;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


@ClientEndpoint
public class PlayerAppServerEndPoint extends EndPoints{

	SortAppMessage sMessage;
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("[client] onOpen" + session.getId());
	}

	@OnMessage
	public void onMessage(String message) {

		// 受信した生のメッセージ
		System.out.println("[playerAppEndpoint] onMessage: " + message);
        sMessage = new SortAppMessage(message);
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("[client] onError");
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("[client] onClose: " + session.getId());
	}

}