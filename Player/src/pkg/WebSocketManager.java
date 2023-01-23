package pkg;
import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WebSocketManager {

	Session session;
	WebSocketContainer container;
	URI uri;
	EndPoints endpoint;

	public WebSocketManager(String uriString, EndPoints endpoint) {
		container = ContainerProvider.getWebSocketContainer();
	    uri = URI.create(uriString);
	    this.endpoint = endpoint;
	}

	public boolean isConnected() {
		System.out.println("[client] isConnected(): " + session.isOpen());
		return session.isOpen();
	}

	public void sendMessage(String text) {
		System.out.println("[client] sendMessage(): " + text);
		try {
			session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean connect() {
		System.out.println("[client] connect(): " + uri);
		try {
			session = container.connectToServer(this.endpoint, uri);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void disconnect() {
		try {
			if(!session.isOpen()) {
				session.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}