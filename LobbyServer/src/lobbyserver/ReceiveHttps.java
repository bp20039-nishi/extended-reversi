package lobbyserver;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/")
public class ReceiveHttps {

    static Gson gson = new Gson();

    @Path("/post")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSample(String requestBody) {

		try{

			// リクエストメッセージの処理
			MessageLobApp rxMsg = gson.fromJson(requestBody, MessageLobApp.class);
			System.out.println("break room No." + rxMsg.roomNumber);
			//もらった所の処理(かちまけ当)
			LobbyServerMain.roomController[rxMsg.roomNumber] = 0;
			//ここから戦績の更新を書く。
			Player player1 = new Player(rxMsg.userWinner,null);
			Player player2;
			if(rxMsg.userWinner.equals( rxMsg.userBlack )) {
				player2 = new Player(rxMsg.userWhite,null);
			}else {
				player2 = new Player(rxMsg.userBlack,null);
			}
			LobbyServerMain.wsDBManager.sendMessage(gson.toJson(new DBMessage("battle Winner",player1)));
			LobbyServerMain.wsDBManager.sendMessage(gson.toJson(new DBMessage("battle Loser",player2)));
			// 確認応答
			MessageLobApp respMsg = new MessageLobApp(rxMsg.roomNumber);
			return Response.ok().entity(gson.toJson(respMsg)).build();

		} catch (Exception e) {

			e.printStackTrace();
			int status = 400;
			return Response.status(status).build();

		}
	}
}
