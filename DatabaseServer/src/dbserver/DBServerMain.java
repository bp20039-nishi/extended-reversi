package dbserver;

import java.util.Scanner;

import org.glassfish.tyrus.server.Server;

public class DBServerMain {

	/* サーバ側のサンプルプログラム
	 * このメインメソッドの例ではサーバインスタンスの生成と起動のみを行っている．
	 */
	//serverの設定
	static String contextRoot = "/dbserver";
	static String protocol = "ws";
	static int port = 8070;
	
    public static void main(String[] args) throws Exception {
        Server server = new Server(protocol, port, contextRoot, null, DBServerEndPoint.class);
        try {
            server.start();
            Scanner sc = new Scanner(System.in);
            while(!sc.nextLine().equals("exit")){
            }
        } finally {
            server.stop();
        }
    }

	DBServerMain() {
	}
}

