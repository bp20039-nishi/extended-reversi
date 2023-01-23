package all;

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

						
			// ルーム作成
			System.out.println("generate room No." + rxMsg.roomNumber);
			System.out.println("Black User: " + rxMsg.userBlack);
			System.out.println("White User: " + rxMsg.userWhite);
			
			PlayerInfo blackPlayer = new PlayerInfo(rxMsg.userBlack, Color.Black);
			PlayerInfo whitePlayer = new PlayerInfo(rxMsg.userWhite, Color.White);
				
			ApplicationServer.roomController.makeRoom(rxMsg.roomNumber, whitePlayer, blackPlayer);

			
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
