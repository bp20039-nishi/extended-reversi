package all;
import java.util.ArrayList;
import java.util.Arrays;

public class CardGetThreePointsStone extends Card {
	
	int cardID = 2;
	String text = "点数3の石を一つ手に入れる";
	boolean endTurn = false;
	boolean usingBoard = false;
	boolean needStone = true;
	
	/* 技効果 */
	public MessageAppPl effect1 (MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board) {
		
		MessageAppPl sendMessage = new MessageAppPl();

		boolean[][] candidate = new boolean[8][8];
		for(int i=0; i<8; i++) {
			Arrays.fill( candidate[i], false );
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
		int stoneRate = 3;	// 今回追加する石の点数（ =3 ）
		
		// カード使用者が白色の場合
		if( receivedMessage.color == Color.White) {
			
			// 石の数を修正
			int[] stone = white.getStone();
			stone[ stoneRate-1 ]++;		// 点数3の石を一つ増やす
			white.setStone(stone);
			
			// カードの残り枚数を修正
			ArrayList<Integer> cardIDList = white.getCardID();
			cardIDList.remove( cardIDList.indexOf(this.cardID) );
			white.setCardID(cardIDList);
			
		}else {
			
			int[] stone = black.getStone();
			stone[ stoneRate-1 ]++;
			black.setStone(stone);
			
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
