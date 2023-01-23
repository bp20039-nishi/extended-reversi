package all;
import java.util.ArrayList;
import java.util.Arrays;

public class CardDoubleCellRate extends Card {
	
	int cardID = 1;
	String text = "盤面上の一つのマスのウェイトを２倍する";
	boolean endTurn = false;
	boolean usingBoard = true;
	boolean needStone = false;
	
	
	/* 技効果 */
	public MessageAppPl effect1 (MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board) {
		
		MessageAppPl sendMessage = new MessageAppPl();
		
		// マスを指定する際に、一応全部のマス光らせる
		boolean[][] candidate = new boolean[8][8];
		for(int i=0; i<8; i++) {
			Arrays.fill( candidate[i], true );
		}
		
		sendMessage.candidate = candidate;	
		sendMessage.candidate = candidate;	
		sendMessage.type = 2;
		sendMessage.cardID = this.cardID;
		sendMessage.effectStage = 1;
		sendMessage.color = receivedMessage.color;
		sendMessage.endTurn = false;
		sendMessage.usingBoard = this.usingBoard;
		sendMessage.needStone = this.needStone;
		sendMessage.text = this.text;
		
		return sendMessage;
		
	}
	
	public MessageAppPl effect2(MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board) {
		
		MessageAppPl sendMessage = new MessageAppPl();
		int cellPlaceX = receivedMessage.a;									// 指定したマスのx座標
		int cellPlaceY = receivedMessage.b;									// 　〃　のy座標
		int newCellRate = board.getCellRate()[cellPlaceX][cellPlaceY] * 2;	// 新しいマスの得点
		
		// マスの得点を更新
		board.setCellRate( cellPlaceX, cellPlaceY, newCellRate);
		
		// カード使用者が白色の場合
		if( receivedMessage.color == Color.White) {
			
			// カードの残り枚数を修正
			ArrayList<Integer> cardIDList = white.getCardID();
			cardIDList.remove( cardIDList.indexOf(this.cardID) );
			white.setCardID(cardIDList);
			
		}else {
			ArrayList<Integer> cardIDList = black.getCardID();
			cardIDList.remove( cardIDList.indexOf(this.cardID) );
			black.setCardID(cardIDList);
			
		}
		
		
		sendMessage.board = board.getBoard();
		sendMessage.cellRate = board.getCellRate();
		sendMessage.type = 2;
		sendMessage.cardID = this.cardID;
		sendMessage.effectStage = 2;
		sendMessage.endTurn = this.endTurn;
		sendMessage.color = receivedMessage.color;
		sendMessage.white = ApplicationServer.roomController.roomMap.get(receivedMessage.roomNumber).sendAble(white);
		sendMessage.black = ApplicationServer.roomController.roomMap.get(receivedMessage.roomNumber).sendAble(black);
		sendMessage.candidate = board.judgeCandidate(receivedMessage.color);
		sendMessage.usingBoard = this.usingBoard;
		
		return sendMessage;
	}
	
	public boolean isEndTurn() {
		return this.endTurn;
	}

}
