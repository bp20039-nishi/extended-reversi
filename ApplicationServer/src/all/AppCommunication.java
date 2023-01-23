package all;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;



// エンドポイントは適宜変更する
@ServerEndpoint("/appServer")
public class AppCommunication {
	
	
	
	private static Set<Session> establishedSessions
    	= Collections.synchronizedSet(new HashSet<Session>());

    static Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session, EndpointConfig ec) {
    	establishedSessions.add(session);
        System.out.println("[AppServer@OnOpen]: " + session.getId());
    }


    @OnMessage
    public void onMessage(final String message, final Session session) throws IOException {
    	
        System.out.println("[AppServer@OnMessage]: " + message + " (From" + session.getId() + ")");

        
        // 変換：String -> receivedMessage
        MessageAppPl receivedMessage = gson.fromJson(message, MessageAppPl.class);
        
        // roomControllerにmessageを送信する
        ApplicationServer.roomController.receiveFromCommunication( receivedMessage, session );
       
    }


    @OnClose
    public void onClose(Session session) {
        System.out.println("[AppServer@OnClose]: " + session.getId());
    	establishedSessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("[AppServer@OnError]: " + session.getId());
    }

    
	public void sendMessage(Session session, String message) {
		System.out.println("[AppServer@SendMsg]: " + message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void sendBroadcastMessage(String message) {
		System.out.println("[AppServer@sendBroadcast]:  " + message);
		establishedSessions.forEach(session -> {
			// 非同期送信（async）
			session.getAsyncRemote().sendText(message);
		});
	}
}

