package dbserver;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
// エンドポイントは適宜変更する
@ServerEndpoint("/lobbyserver")
public class DBServerEndPoint {

	//json系のクラス
    static Gson gson = new Gson();
    DBMessage dbmessage;
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("[DBServerEndPoint] onOpen:" + session.getId());
    }


    @OnMessage
    public void onMessage(final String message, final Session session) throws IOException {
        System.out.println("[DBServerEndPoint] onMessage from (session: " + session.getId() + ") msg: " + message);
        dbmessage = gson.fromJson(message,DBMessage.class);
        System.out.println(dbmessage.message);
        System.out.println(dbmessage.player);
        if(dbmessage.message.equals("login")) {
        	//loginがきたときの処理 OK or NG
        	boolean resultCollation = EntityController.collation(dbmessage.player.getuserID(), dbmessage.player.getpassword());
        	System.out.println("Collation result : "+resultCollation);
        	Player player = new Player(dbmessage.player.getuserID(),null);
        	DBMessage dbmessage = new DBMessage("login result",player);
        	dbmessage.result = resultCollation;
        	sendMessage(session,gson.toJson(dbmessage));
        }else if(dbmessage.message.equals("sign up")) {
        	//sing upがきたときの処理 OK or NG
        	boolean resultAddDatabase = EntityController.addDatabase(dbmessage.player.getuserID(), dbmessage.player.getpassword());
        	System.out.println("AddDatabase result : "+resultAddDatabase);
        	Player player = new Player(dbmessage.player.getuserID(),null);
        	DBMessage dbmessage = new DBMessage("sign up result",player);
        	dbmessage.result = resultAddDatabase;
        	sendMessage(session,gson.toJson(dbmessage));
        }else if(dbmessage.message.equals("battleRecord")) {
        	int win = EntityController.sendWin(dbmessage.player.getuserID());
        	int lose = EntityController.sendLose(dbmessage.player.getuserID());
        	String battleRecord = win+" "+lose;
        	Player player = new Player(dbmessage.player.getuserID(),battleRecord);
        	DBMessage dbmessage = new DBMessage("battleRecord result",player);
        	sendMessage(session,gson.toJson(dbmessage));
        }else if(dbmessage.message.equals("battle Winner")) {
        	EntityController.updateBattleRecord(dbmessage.player.getuserID(),"win");
        }else if(dbmessage.message.equals("battle Loser")) {
        	EntityController.updateBattleRecord(dbmessage.player.getuserID(),"lose");
        }else {
        	System.out.println("[DBServerEndPoint] ERROR! 予期しないメッセージです");
        }
    }


    @OnClose
    public void onClose(Session session) {
        System.out.println("[DBServerEndPoint] onClose:" + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("[DBServerEndPoint] onError:" + session.getId());
    }
    //メッセージ送信
	public void sendMessage(Session session, String message) {
		System.out.println("[DBServerEndPoint] sendMessage(): " + message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

