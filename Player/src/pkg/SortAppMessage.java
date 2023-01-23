package pkg;


import com.google.gson.Gson;


// 受け取ったメッセージ(今はJsonで受け取った体でつくってる)のタイプから、用途を判断するクラス
public class SortAppMessage {
	
    Gson gson = new Gson();
    MessageAppPl receivedMessage;
    ProcessGame pg = new ProcessGame();
	
	SortAppMessage( String message ) {
		
		// 変換：String -> SampleMessage
        this.receivedMessage = gson.fromJson(message, MessageAppPl.class);
        this.sort();
	}
	
	
	// 仕分ける関数
	private void sort() {
		GameInfo.setUsingStoneScore(-1);
		pg.setMessage(this.receivedMessage);
		// 手番交代の指示が来たとき
		if( receivedMessage.type == 0 ) {
			if( GameInfo.getEndGame() ) {
				GameInfo.setEndGame(false);
				if( GameInfo.getMyColor() == Color.White) {
					GameInfo.setMyInfo(receivedMessage.white);
					GameInfo.setEnemyInfo(receivedMessage.black);
				}
				else {
					GameInfo.setMyInfo(receivedMessage.black);
					GameInfo.setEnemyInfo(receivedMessage.white);
				}
				Main.screenFrame.change(ScreenMode.GAME);
			}
			pg.changeTurn();
		}
		
		// 石をおいた処理に対する返答
		else if( receivedMessage.type == 1 ) {
			System.out.println("石の処理結果来た");
			pg.putStone();
		}
		
		// カード使用に対する返答
		else if( receivedMessage.type == 2 ) {
			pg.useCard();	
		}
		
		// 時間切れ通知が来たとき
		else if( receivedMessage.type == 3 ) {
			// 時間切れをRoomに伝える必要がある->ゲーム終了の通知が来る
			pg.timeOut();
		}
		
		// ゲーム終了の通知が来たとき
		else if( receivedMessage.type == 4 ) {
			
			PlayerCommunication.appManager.disconnect();
			// ゲームを終了する
			pg.endGame();
		}
	}
	
	
	
	
	
	
}
