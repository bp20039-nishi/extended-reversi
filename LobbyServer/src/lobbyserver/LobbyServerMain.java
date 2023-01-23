package lobbyserver;

import java.net.URI;
import java.util.Random;
import java.util.Scanner;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.tyrus.server.Server;

import com.google.gson.Gson;

public class LobbyServerMain {

	/* サーバ側のサンプルプログラム
	 * このメインメソッドの例ではサーバインスタンスの生成と起動のみを行っている．
	 */
	//serverの設定
	static String contextRoot = "/lobbyserver";
	static String protocol = "ws";
	static int port = 8090;
	static Gson gson;
	//dbclientの設定
	static String serverEndpoint = "ws://localhost:8070/dbserver/lobbyserver";
	static DBServerClientManager wsDBManager;
	//AppServerの設定
	static AppServerEndPoint appServerClient = new AppServerEndPoint();
	public static final String restUri = "http://localhost:8082";
	
	
	static Random rand = new Random();
	static int roomController[]; //現在のルーム状況を確認 256は一度に作れる最大ルーム数 0が空き、1が入っている.
    public static void main(String[] args) throws Exception {
		roomController = new int[256];
		for(int i=0;i<roomController.length;i++) {
			roomController[i] = 0;
		}
    	gson = new Gson();
    	//client立てる
		wsDBManager = new DBServerClientManager(serverEndpoint);
		wsDBManager.connect();
		//httpApp
		final ResourceConfig rc = new ResourceConfig().packages("lobbyserver");
        final HttpServer restServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(restUri), rc);
		//server立てる
        Server server = new Server(protocol, port, contextRoot, null, PlayerEndPoint.class);
        try {
            server.start();
            watchMatchingQueue();
            Scanner sc = new Scanner(System.in);
            while(!sc.nextLine().equals("exit")){
            }
            sc.close();
        } finally {
        	restServer.shutdownNow();
            server.stop();
        }
    }
	//キューを監視するメソッド
	private static void watchMatchingQueue() {
		while(true) {
		while(PlayerEndPoint.standByQueue.size() > 1) {
			//キューの中に二人以上いる
			System.out.println("現在のキューのサイズ : "+PlayerEndPoint.standByQueue.size());
			String user1 = PlayerEndPoint.standByQueue.poll();
			
			String user2 = PlayerEndPoint.standByQueue.poll();
			int roomNum;
			roomNum = 0;
			while(roomController[roomNum] == 1) {
				roomNum++;
				System.out.println(roomNum);
			}
			roomController[roomNum] = 1;
			MessageLobApp message = new MessageLobApp(roomNum);
			boolean randomBool= rand.nextBoolean();
		    System.out.println("乱数 : "+randomBool);
		    if(randomBool) {
				message.userBlack = user1;
				message.userWhite = user2;
		    }else{
		    	message.userBlack = user2;
				message.userWhite = user1;
		    }
		    
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//AppServerに情報を送信
		    appServerClient.postSample(message);
			}
		}
	}
}

