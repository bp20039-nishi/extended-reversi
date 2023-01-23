package all;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.websocket.Session;

import com.google.gson.Gson;

public class Room {
	private final int initTime = 1;
	
	private int roomNumber;		// ルーム番号
	private PlayerInfo white;	// 白のプレイヤー
	private PlayerInfo black;	// 黒のプレイヤー
	private Board board;		// 盤面
	private int passCount = 0;	// 連続パス回数
	private int comRequest = 0;	// 2になれば、ゲーム開始
	
	static Gson gson = new Gson();
	
	
	// ルーム番号と、プレイヤー情報を受け取る。ボードを作成して、黒からゲーム開始
	Room(int roomNumber, PlayerInfo white, PlayerInfo black) {
		this.roomNumber = roomNumber;
		this.white = white;
		this.black = black;
		
		
		board = new Board( this.setCellRate(2) );
		//プレイヤー２人からの通信を受け取り，PlayerInfoにsetSessionを設定してから始める
		/*
		 * エラー吐くのでいったんコメントアウト
		 * changeTurn( black );
		int score[] = new int[2];
		gameFinish(score, Color.Black);
		 */
	}
	
	
	// ターン切り替えを行うクラス　引数のプレイヤーが次の手番
	public void changeTurn( PlayerInfo nextPlayerInfo ) {
				
		int i = 0;
		int j = 0;
		
		
		boolean passFlag = true;
		boolean[][] candidate = board.judgeCandidate( nextPlayerInfo.getColor() );

		/*

		// カードが1枚もなかったら、trueのまま抜ける
		for( int k : nextPlayerInfo.getCardID() )
			if( k != 0 ) passFlag = false;
		*/
		
		// カードを1枚も持っていないとき、置く場所があるか判定 -> 置けなければpass
		
		while( passFlag && i < 8 ) {
			j=0;
			while( passFlag && j < 8 ) {
					if( candidate[i][j] == true ) {
						passFlag = false;
					}
					j++;
			}
			i++;
		}

		
		if( passFlag ) {
			passCount++;
			
			System.out.println("パスします");
			
			/**************************************************
			 * passします。。。。。（画面表示）
			 */
			
			// パスが2回連続で行われたら、ゲーム終了＆勝敗判定
			if( passCount == 2) {
				

				System.out.println("パス２回め");
				
				int[] score =  board.countScore();
				
				// 白が勝ち
				if( score[0] > score[1] ) this.gameFinish( score, Color.White);
				// 黒が勝ち
				else if( score[0] < score[1] ) this.gameFinish( score, Color.Black );
				// 引き分け
				else this.gameFinish( score, Color.None );
			}
			// 連続パス回数が1回のとき（まだゲーム継続）
			else {

				System.out.println("パス1回め");
				if( nextPlayerInfo.getColor() == Color.White ) changeTurn( this.black );
				else changeTurn( this.white );
			}
		}
		

		
		// 置ける場所をプレイヤーに送って、ターン操作開始
		else {
			MessageAppPl sendMessage = new MessageAppPl();
			
			int[] score = this.board.countScore();
			
			// メッセージタイプ、置き場所候補、点数、次手番の残り時間、プレイヤー情報、次の手番の人の色を入れる
			sendMessage.type = 0;
			sendMessage.candidate = candidate;
			sendMessage.a = score[0];
			sendMessage.b = score[1];
			sendMessage.c = nextPlayerInfo.getTime().getTime();
			sendMessage.white = sendAble(this.white);
			sendMessage.black = sendAble(this.black);			
			sendMessage.color = nextPlayerInfo.getColor();
			sendMessage.board = this.board.getBoard();
			sendMessage.cellRate = this.board.getCellRate();
			
			// 両プレイヤーに送信			
			String jsonMessage = gson.toJson(sendMessage);
			this.white.getSession().getAsyncRemote().sendText(jsonMessage);
			this.black.getSession().getAsyncRemote().sendText(jsonMessage);

			
			// カウントダウン開始
			nextPlayerInfo.getTime().startCount();
			
			passCount = 0;
		}
		
	}
	
	
	public void useCard( MessageAppPl receivedMessage ) {
		
		int cardID = receivedMessage.cardID;
		
		MessageAppPl sendMessage;
		
		sendMessage = ApplicationServer.cardList.cardList.get( cardID ).receive( receivedMessage, this.white, this.black, this.board );
		
		//System.out.println("Card[AppServer.sendMesage]:candidate: "+ sendMessage.candidate);
		//System.out.println("white card: "+ sendMessage.white.getCardID());
		//System.out.println("black card: "+ sendMessage.black.getCardID());
		
		
		// カウントストップ
		if( ApplicationServer.cardList.cardList.get( cardID ).isEndTurn() && (receivedMessage.effectStage == 2)) {
			if( sendMessage.color == Color.White ) this.white.getTime().stopCount();
			else this.black.getTime().stopCount();
		}
		
		String jsonMessage = gson.toJson(sendMessage);
		this.white.getSession().getAsyncRemote().sendText(jsonMessage);
		this.black.getSession().getAsyncRemote().sendText(jsonMessage);
	}
	
	
	public void gameFinish( int[] score, Color winner) {
		
		MessageAppPl sendMessage = new MessageAppPl();
		
		
		// 対戦終了時の画面表示
		sendMessage.type = 4;
		sendMessage.a = score[0];
		sendMessage.b = score[1];
		sendMessage.color = winner;
		sendMessage.board = this.board.getBoard();
		String jsonMessage = gson.toJson(sendMessage);
		
		// 戦績の更新（ロビーサーバに戦績送る）
		String winnerID;
		if(winner == Color.White)
			winnerID = this.white.getUserID();
		else
			winnerID = this.black.getUserID();
		
		AppServerEndpoint CRS = new AppServerEndpoint();
		CRS.postFinish(this.roomNumber, winnerID, this.white.getUserID(), this.black.getUserID());
		
		this.white.getSession().getAsyncRemote().sendText(jsonMessage);
		this.black.getSession().getAsyncRemote().sendText(jsonMessage);
		// 部屋壊す
		ApplicationServer.roomController.breakRoom(this.roomNumber);
		
	}
	
	public void sendBoard(  Color playerColor, Stone[][] board ) {
		
		MessageAppPl boardInfo = new MessageAppPl();
		
		
		boardInfo.type = 1;
		boardInfo.color = playerColor;
		boardInfo.white = this.sendAble(white);
		boardInfo.black = this.sendAble(black);
		boardInfo.board = board;
		
		String message = gson.toJson(boardInfo);

		System.out.println("石の処理送信開始");
		this.white.getSession().getAsyncRemote().sendText(message);
		this.black.getSession().getAsyncRemote().sendText(message);
	}
	
	
	
	public void receive(MessageAppPl message, Session session) {
		
		
		// ゲーム開始時
		if( message.type == 5 ) {
			

			System.out.println("Room type=5");
			
			if( message.color == Color.White ) {
				this.InitializePlayerInfo(this.white, session);
			}
			else {
				this.InitializePlayerInfo( this.black, session);
			}
			
			this.comRequest++;
			
			if( this.comRequest == 2 ) {
				changeTurn(this.black);
			}
		}
		
		// ターン交代時
		if( message.type == 0 ) {
			

			System.out.println("Room type=0");
			
			// 次の手番の人が白ならば
			if( message.color == Color.White ) changeTurn(this.white);
			// 黒ならば
			else changeTurn(this.black);
		}
		
		
		// 石を置く操作を受け取ったとき
		if( message.type == 1 ) {
			
			System.out.println("Room type=1");
			
			// カウントストップ
			if( message.color == Color.White ) {
				this.white.getTime().stopCount();
				// 処理実行
				board.setAndTurnOver( message.color, message.a, message.b, message.c );
				int[] stNum =  this.white.getStone();
				stNum[message.c-1]--;
				this.white.setStone(stNum);
				this.sendBoard( Color.White, board.getBoard());
			}
			else {
				this.black.getTime().stopCount();
				// 処理実行
				board.setAndTurnOver( message.color, message.a, message.b, message.c );
				int[] stNum =  this.black.getStone();
				stNum[message.c-1]--;
				this.black.setStone(stNum);
				this.sendBoard( Color.Black, board.getBoard());
			}
		}
		
		
		// カードを使用した場合
		if( message.type == 2 ) {

			System.out.println("Room type=2");
			this.useCard( message );
		}
		
		// プレイヤーから時間切れの通知を受け取ったとき
		if( message.type == 3 ) {


			System.out.println("Room type=3");
			
			int[] score =  this.board.countScore();
			
			this.white.getTime().stopCount();
			this.black.getTime().stopCount();
			
			// 黒が時間切れのとき
			if( message.color == Color.Black)
				this.gameFinish( score, Color.White);
			// 白が時間切れのとき
			else this.gameFinish(score, Color.Black);
		}
		
	}
	
	
	// ゲーム開始時のPlayerInfoの初期化を行うクラス
	public void InitializePlayerInfo( PlayerInfo pI, Session sS) {
		// セッション
		pI.setSession(sS);
		// 持石
		int[] stone = new int[3];
		stone[0] = 99;
		stone[1] = 5;
		stone[2] = 3;
		pI.setStone(stone);
		// 手札
		ArrayList<Integer> cardList = new ArrayList<>();
		cardList.add(0);
		cardList.add(1);
		cardList.add(2);
		pI.setCardID(cardList);
		// 残り時間
		pI.setTime(initTime);
	}
	
	
	// 初期化時に盤面の得点の重みを設定する（引数で、どんな設定方法にするか決定）
	public int[][] setCellRate( int mode ){
		
		
		int[][] cellRate = new int[8][8];
		Random rand = new Random();
		
		// 全部のマスの倍率が1の設定
		if( mode == 1 ) {
			for(int i=0; i<8; i++)
				Arrays.fill(cellRate[i], 1);
		}
		else if(mode == 2) {
			for(int i=0; i<8; i++)
				for(int j=0; j<8; j++) {
					cellRate[i][j] = rand.nextInt(3)+1;
					if(cellRate[i][j] == 0)
						cellRate[i][j] = 1;
			}
		}
		
		
		return cellRate;
	}

	
	public int getRoomNumber() {
		return this.roomNumber;
	}
	
	public PlayerInfo sendAble(PlayerInfo plin) {
		/*
		 * 引数のPlayerInfoからSessionのないPlayerInfoを作成する
		 *
		 * Playerとの通信見た感じ残り時間intで送ってるからPlayerInfoないのタイマーは送らなくてよさそう
		 * Timer の中にもSessionが入り込んでて送れない原因になってるっぽい
		 */
		
		PlayerInfo send = new PlayerInfo(plin.getUserID(),plin.getColor());
		
		send.setStone(plin.getStone());
		send.setCardID(plin.getCardID());
		send.setBattleRecord(plin.getBattleRecord());
		
		return send;
		
	}
	
	

}
