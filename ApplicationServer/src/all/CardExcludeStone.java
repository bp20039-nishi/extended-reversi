package all;
import java.util.ArrayList;

public class CardExcludeStone extends Card{
	int cardID = 3;
	String text = "盤面上の石を除外する";
	boolean endTurn = false;
	boolean usingBoard = true;
	boolean needStone = false;
	
	/* 技効果 */
	public MessageAppPl effect1 (MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board) {
		
		MessageAppPl sendMessage = new MessageAppPl();
		boolean[][] candidate = new boolean[8][8];
		Stone[][] nowBoard = board.getBoard();
		
		for(int i = 0 ; i<8 ; i++) {
			for(int j = 0 ; j<8 ; j++) {
				if(nowBoard[i][j].getColor() != Color.None)candidate[i][j] = true;
				else candidate[i][j] = false;
			}
		}
		
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
		int stonePlaceX = receivedMessage.a;
		int stonePlaceY = receivedMessage.b;
		
		board.setStone( Color.None, stonePlaceX, stonePlaceY, 1);
		
		
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
