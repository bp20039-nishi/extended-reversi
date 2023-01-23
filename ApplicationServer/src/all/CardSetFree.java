package all;
import java.util.ArrayList;
import java.util.Arrays;


public class CardSetFree extends Card {
	
	/*
	 * フィールドとして、cardIDとtext（説明文）を持つ
	 * */
	
	int cardID = 0;
	String text = "盤面上のすべてのマス(すでに石が置かれているマスも可)に石を置ける";
	boolean endTurn = true;
	boolean usingBoard = true;
	boolean needStone = true;
	
	
	/* 技効果 */
	public MessageAppPl effect1 (MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board) {
		
		MessageAppPl sendMessage = new MessageAppPl();
		boolean[][] candidate = new boolean[8][8];
		for(int i=0; i<8; i++) {
			Arrays.fill( candidate[i], true );
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
		int stoneRate = receivedMessage.c;
		
		board.setStone( receivedMessage.color, stonePlaceX, stonePlaceY, stoneRate);
		
		
		// カード使用者が白色の場合
		if( receivedMessage.color == Color.White) {
			
			// 石の数を修正
			int[] stone = white.getStone();
			if( stoneRate != 1)	stone[ stoneRate-1 ]--;	// 点数1の石は無限個ある
			white.setStone(stone);
			
			// カードの残り枚数を修正
			ArrayList<Integer> cardIDList = white.getCardID();
			cardIDList.remove( cardIDList.indexOf(this.cardID) );
			white.setCardID(cardIDList);
			
		}else {
			int[] stone = black.getStone();
			if( stoneRate != 1)	stone[ stoneRate-1 ]--;	// 点数1の石は無限個ある
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
