package all;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.tyrus.server.Server;

public class ApplicationServer {

	
	/* サーバ側のサンプルプログラム
	 * このメインメソッドの例ではサーバインスタンスの生成と起動のみを行っている．
	 */

	static String contextRoot = "/app";
	static String protocol = "ws";
	static int port = 8080;
	public static final String restUri = "http://localhost:8081";
	
	static CardList cardList = new CardList();
	
	static RoomController roomController = new RoomController();
	

	public static void main(String[] args) throws Exception {
		
		Server server = new Server(protocol, port, contextRoot, null, AppCommunication.class);
		final ResourceConfig rc = new ResourceConfig().packages("all");
        final HttpServer restServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(restUri), rc);
		try {
			server.start();
			System.in.read();
		} finally {
			server.stop();
			restServer.shutdownNow();
		}
	}



}
