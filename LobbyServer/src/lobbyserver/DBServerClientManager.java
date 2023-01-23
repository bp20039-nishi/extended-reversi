package lobbyserver;
import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class DBServerClientManager {

	Session session;
	WebSocketContainer container;
	URI uri;

	public DBServerClientManager(String uriString) {
		container = ContainerProvider.getWebSocketContainer();
	    uri = URI.create(uriString);
	}

	public boolean isConnected() {
		System.out.println("[DBServerClientManager] isConnected(): " + session.isOpen());
		return session.isOpen();
	}

	public void sendMessage(String text) {
		System.out.println("[DBServerClientManager] sendMessage(): " + text);
		try {
			session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean connect() {
		System.out.println("[DBServerClientManager] connect(): " + uri);
		try {
			session = container.connectToServer(new DBServerEndPoint(), uri);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void disconnect() throws IOException {
		if(!session.isOpen()) {
			session.close();
		}
	}
}