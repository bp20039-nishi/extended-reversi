package lobbyserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

// エンドポイントは適宜変更する
@ServerEndpoint("/player")
public class PlayerEndPoint {
	//同期化されたリスト
	protected static Map<String,Session> currentConnectedPlayer
    	= Collections.synchronizedMap(new HashMap<>());
	private static List<Session> establishedSessions
		= Collections.synchronizedList(new ArrayList<>());
	protected static ConcurrentLinkedQueue<String> standByQueue 
		= new ConcurrentLinkedQueue<String>();
	//json系のクラス
    static Gson gson = new Gson();
    
    PlayerController playerController = new PlayerController();
    LobbyMessage lobbymessage;
    
    @OnOpen
    public void onOpen(Session session) {
    	establishedSessions.add(session);
        System.out.println("[PlayerEndPoint] onOpen:" + session.getId());
        System.out.println("onOpen 現在接続数 : "+establishedSessions.size());
        
    }


    @OnMessage
    public void onMessage(final String json, final Session session) throws IOException {
        System.out.println("[PlayerEndPoint] onMessage from (session: " + session.getId() + ") msg: " + json);
        lobbymessage = gson.fromJson(json,LobbyMessage.class);
        System.out.println(lobbymessage.getMessage());
        System.out.println(lobbymessage.getPlayer().getuserID());
    	Player player = new Player(lobbymessage.getPlayer().getuserID(),lobbymessage.getPlayer().getpassword());
    	System.out.println("[PlyaerEndPoint] player : "+player);
    	if(lobbymessage.getMessage().equals("login") || lobbymessage.getMessage().equals("sign up")) {
    		if(currentConnectedPlayer.containsKey(player.getuserID()) && lobbymessage.getMessage().equals("login")) {
    			PlayerEndPoint.sendMessage(session,gson.toJson(new LobbyMessage("already login  error",null)));
    			return;
    		}
    		//Map作成
    		System.out.println("loginまたはsign up : Map追加");
        	currentConnectedPlayer.put(player.getuserID(),session);
    	}
        //ログインメッセージ・サインアップ・マッチングの判別
        if(lobbymessage.getMessage().equals("login")) {
        	System.out.println("login processing");
        	playerController.loginFromPlayer(player);
        }else if(lobbymessage.getMessage().equals("sign up")) {
        	System.out.println("sign up processing");
        	playerController.signUpFromPlayer(player);
        }else if(lobbymessage.getMessage().equals("matching")){
        	System.out.println("matching processing");
        	playerController.matching(player.getuserID());
        }else if(lobbymessage.getMessage().equals("cancel")){
        	System.out.println("cancel matching");
        	playerController.cancel(player.getuserID());
        }else if(lobbymessage.getMessage().equals("battleRecord")) {
         	System.out.println("battleRecord processing");
        	playerController.battleRecord(player);
        }else if(lobbymessage.getMessage().equals("rule")) {
        	System.out.println("rule processing");
        	playerController.rule(player);
        }
    }


    @OnClose
    public void onClose(Session session) {
        System.out.println("[PlayerEndPoint] onClose:" + session.getId());
    	establishedSessions.remove(session);
    	currentConnectedPlayer.remove(getSingleKeyFromValue(currentConnectedPlayer,session));
    	System.out.println("onClose 現在接続数 : "+establishedSessions.size());
    	System.out.println("onClose 現在存在するuserID : "+currentConnectedPlayer.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("[PlayerEndPoint] onError:" + session.getId());
    }
    //メッセージ送信(サーバ側はsessionが多数予あるためここで指定する)
	public static void sendMessage(Session session, String message) {
		System.out.println("[PlayerEndPoint] sendMessage(): " + message);
		// 同期送信（sync）
//		try {
//			session.getBasicRemote().sendText(message);
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
		// 非同期送信（async）
		session.getAsyncRemote().sendText(message);
	}
	//valueからkeyを求める
	   public static <K, V> K getSingleKeyFromValue(Map<K, V> map, V value) {
	        for (Map.Entry<K, V> entry : map.entrySet()) {
	            if (Objects.equals(value, entry.getValue())) {
	                return entry.getKey();
	            }
	        }
	        return null;
	    }
	//使わない
//	public void sendBroadcastMessage(String message) {
//		System.out.println("[PlayerEndPoint] sendBroadcastMessage(): " + message);
//		establishedSessions.forEach(session -> {
//			// 非同期送信（async）
//			session.getAsyncRemote().sendText(message);
//		});
//	}
}
