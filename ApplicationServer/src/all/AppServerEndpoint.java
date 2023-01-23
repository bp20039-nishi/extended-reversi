package all;


import javax.websocket.ClientEndpoint;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@ClientEndpoint
public class AppServerEndpoint {

	private Client client = ClientBuilder.newClient();
	private String lobbyServerURI = "http://localhost:8082";
	private String postPath = "/post";

	static Gson gson = new Gson();


	// HTTP POSTをする例
	void postFinish(int roomNumber, String winner, String white, String black) {

		WebTarget target = client.target(lobbyServerURI).path(postPath);

		MessageLobApp finishMessage = new MessageLobApp(roomNumber);
		finishMessage.userWinner = winner;
		finishMessage.userWhite = white;
		finishMessage.userBlack = black;
		
		System.out.println(gson.toJson(finishMessage));
		String result = target.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(gson.toJson(finishMessage), MediaType.APPLICATION_JSON), String.class);
		System.out.println("[RestClient] postExample: " + result);
	}
}