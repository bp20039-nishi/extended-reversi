package pkg;

import java.util.Arrays;

public class ProcessGame {
	
	MessageAppPl receivedMessage;
	
	public void changeTurn() {
		
		// 候補手の更新
		GameInfo.setCandidateInfo( receivedMessage.candidate );
		GameInfo.setBoardInfo(receivedMessage.board);
		GameInfo.setCellRate(receivedMessage.cellRate);
		
		//点数更新
		int score[] = {receivedMessage.a , receivedMessage.b};
		GameInfo.setScore(score);
		
		GameInfo.setUsingCardID(-1);
		GameInfo.setCardUsing(false);
		
		System.out.println("Appの中身" + receivedMessage.color);
		// 次自分の手番
		if ( receivedMessage.color == GameInfo.getMyColor() ) {
			
			GameInfo.setText("使用するカードもしくは、石の種類を選択してください");
			
			// 自分の残り時間更新
			GameInfo.setMyLeftTime( receivedMessage.c );
			GameInfo.setMyTurn(true);

			System.out.println("俺の色（一致）" + GameInfo.getMyColor());
			
		}
		// 次相手の手番
		else {
			GameInfo.setText("相手の手番です");
			// 相手の残り時間更新
			GameInfo.setEnemyLeftTime( receivedMessage.c );
			GameInfo.setMyTurn(false);

			System.out.println("俺の色（不一致）" + GameInfo.getMyColor());
			
		}
		//描画
		System.out.println("再描画");
		//Main.screenFrame.gamePanel.prepareComponents();
		//Main.screenFrame.gamePanel.repaint();
		//Main.screenFrame.gamePanel.update();
		Main.screenFrame.gamePanel.DataSet();
		System.out.println("再描画完了");
		
		GamePanel.timer.start();
	}
	
	public void putStone() {
		

		System.out.println("ボードの更新開始");
		// ボード情報を更新
		GameInfo.setBoardInfo( receivedMessage.board );

		System.out.println("ボードの更新開始1");
		boolean candidate[][] = new boolean[8][8];
		for(int i=0; i<8; i++ ) {
			Arrays.fill( candidate[i], false );
		}
		GameInfo.setCandidateInfo(candidate);

		System.out.println("ボードの更新開始2");
		if(GameInfo.getMyColor() == receivedMessage.white.getColor()) {
			GameInfo.setMyInfo(receivedMessage.white);
			GameInfo.setEnemyInfo(receivedMessage.black);
		}else {
			GameInfo.setMyInfo(receivedMessage.black);
			GameInfo.setEnemyInfo(receivedMessage.white);
		}	
		

		System.out.println("画面更新");
		
		//描画
		Main.screenFrame.gamePanel.DataSet();
		System.out.println("再描画完了");
		
		
		//Messageの作成・送信
		if(GameInfo.isMyTurn()) sendChangeTurn();
	}
	
	public void useCard() {
		// カード効果第一段階
		if( receivedMessage.effectStage == 1 ) {
			
			GameInfo.setCardUsing(receivedMessage.doOn);
			if(receivedMessage.doOn)GameInfo.setUsingCardID(receivedMessage.cardID);
			else GameInfo.setUsingCardID(-1);
			//カードテキスト
			GameInfo.setText(receivedMessage.text);
			// 候補手の更新
			GameInfo.setCandidateInfo( receivedMessage.candidate );
			// ボードの使用
			GameInfo.setUsingBoard(receivedMessage.usingBoard);
			//石を選択するか
			GameInfo.setNeedStone(receivedMessage.needStone);
			

			/****** 画面再描画 ******/
			System.out.println("画面更新");
			Main.screenFrame.gamePanel.DataSet();
			System.out.println("再描画完了");
			
			if(receivedMessage.doOn) {
				if((!GameInfo.isUsingBoard()) && GameInfo.isMyTurn()) {
					new DontUseBoard(GameInfo.getUsingCardID(), Main.screenFrame);
				}
			}
			
		}
		// カード効果第二段階
		else if( receivedMessage.effectStage == 2 ) {
			
			//playerinfo更新
			if(GameInfo.isMyTurn()) {
				if(GameInfo.getMyColor() == receivedMessage.white.getColor())GameInfo.setMyInfo(receivedMessage.white);
				else GameInfo.setMyInfo(receivedMessage.black);
			}else {
				if(GameInfo.getMyColor() == receivedMessage.white.getColor())GameInfo.setEnemyInfo(receivedMessage.black);
				else GameInfo.setEnemyInfo(receivedMessage.white);
			}
			
			// ボード情報を更新
			GameInfo.setBoardInfo( receivedMessage.board );
			GameInfo.setCellRate(receivedMessage.cellRate);
			
			if(receivedMessage.usingBoard) {
				int score[] = {receivedMessage.a , receivedMessage.b};
				GameInfo.setScore(score);
			}
			

			/****** 画面再描画 ******/
			System.out.println("画面更新");
			Main.screenFrame.gamePanel.DataSet();
			System.out.println("再描画完了");
			
			// カード使用後、ターンを終了する場合
			if( receivedMessage.endTurn ) {
				if(GameInfo.isMyTurn()) sendChangeTurn();
			}
			// カード使用後、石を置くことができる場合
			else {
				GameInfo.setCandidateInfo(receivedMessage.candidate);
				System.out.println("画面更新");
				Main.screenFrame.gamePanel.DataSet();
				System.out.println("再描画完了");
			}
		}
	}
	
	public void timeOut() {
		//時間切れのメッセージを作る(自分は負け)
		MessageAppPl sendMessage = new MessageAppPl();
		
		sendMessage.type = 3;
		sendMessage.roomNumber = GameInfo.getRoomNumber();
		sendMessage.color = GameInfo.getMyInfo().getColor();
		

		System.out.println("時間切れ送る");
		
		Main.pC.sendAppServer(sendMessage);
	}
	
	public void endGame() {
		
		System.out.println("AppServerから結果届く");
		
		
		//点数更新
		int score[] = {receivedMessage.a , receivedMessage.b};
		GameInfo.setScore(score);
		//勝敗
		if(GameInfo.getMyColor() == receivedMessage.color)GameInfo.setText("勝ち");
		else if(receivedMessage.color == Color.None) GameInfo.setText("引き分け");
		else GameInfo.setText("負け");
		//盤面の更新
		GameInfo.setBoardInfo(receivedMessage.board);
		//残り時間カウントの停止
		GamePanel.timer.stop();
		
		GameInfo.setEndGame(true);
		System.out.println("画面更新");
		Main.screenFrame.gamePanel.DataSet();
		System.out.println("再描画完了");
		
		Main.screenFrame.homePanel.resetBtn();
		
		//Main.screenFrame.change(ScreenMode.HOME);
	}
	
	public void setMessage(MessageAppPl m) {
		this.receivedMessage = m;
	}
	
	//相手のターンを呼ぶ
	private void sendChangeTurn() {
		
		System.out.println("ターンチェンジ開始");
		
		MessageAppPl sendMessage = new MessageAppPl();

		System.out.println("ターンチェンジ送信１");
		sendMessage.type = 0;
		System.out.println("ターンチェンジ送信２");
		sendMessage.roomNumber = GameInfo.getRoomNumber();
		System.out.println("ターンチェンジ送信３");
		sendMessage.color = GameInfo.getEnemyInfo().getColor();
		System.out.println("ターンチェンジ送信４");
		
		Main.pC.sendAppServer(sendMessage);

		System.out.println("ターンチェンジ送信");
	}
	
	
	
	
	
	
	
	
	
}
