package pkg;

import com.google.gson.Gson;

public class PlayerCommunication {
	static String serverEndpoint = "ws://localhost:8090/lobbyserver/player";
	static String appServerEndpoint = "ws://localhost:8080/app/appServer";
	static WebSocketManager lobbyManager;
	static WebSocketManager appManager = new WebSocketManager(appServerEndpoint, new PlayerAppServerEndPoint());
	
	Gson gson;
	
	PlayerCommunication(){
		gson = new Gson();
		lobbyManager = new WebSocketManager(serverEndpoint, new LobbyServerEndPoint());
		lobbyManager.connect();
	}
	
	void sendLobbyServer(String message,Player player){
		lobbyManager.sendMessage(gson.toJson(new LobbyMessage(message,player)));
	}
	
	void sendAppServer(MessageAppPl message) {
		if(appManager.isConnected()) {
			String sendMessage = gson.toJson(message);
			
			System.out.println("sendMessage.type" + message.type);
			appManager.sendMessage(sendMessage);
		}
	}
}