package lobbyserver;


import javax.websocket.ClientEndpoint;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@ClientEndpoint
public class AppServerEndPoint {

	private Client client = ClientBuilder.newClient();
	private String appServerUri = "http://localhost:8081";
	private String postPath = "/post";

	static Gson gson = new Gson();


	// HTTP POSTをする例
	void postSample(MessageLobApp message) {

		WebTarget target = client.target(appServerUri).path(postPath);
		System.out.println(gson.toJson(message));
		String result = target.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(gson.toJson(message), MediaType.APPLICATION_JSON), String.class);
		System.out.println("[RestClient] postExample: " + result);
	    //プレイヤーに送信
	    System.out.println(PlayerEndPoint.currentConnectedPlayer.get(message.userWhite));
	    System.out.println(PlayerEndPoint.currentConnectedPlayer.get(message.userBlack));
	    PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(message.userWhite),gson.toJson(new LobbyMessage("matching result",new Player(Integer.valueOf(message.roomNumber).toString(),"WHITE"))));
		PlayerEndPoint.sendMessage(PlayerEndPoint.currentConnectedPlayer.get(message.userBlack),gson.toJson(new LobbyMessage("matching result",new Player(Integer.valueOf(message.roomNumber).toString(),"BLACK"))));
		
	}
}