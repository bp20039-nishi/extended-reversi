package all;

public abstract class Card {
	
	int cardID;
	String text;
	boolean endTurn;	// 効果後、石置ける=false　置けないでターンエンド=true
	boolean usingBoard;
	boolean needStone;
	
	public abstract MessageAppPl effect1 (MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board);
	
	public abstract MessageAppPl effect2(MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board);
	
	public abstract boolean isEndTurn();
	// 仕分け
	public MessageAppPl receive(MessageAppPl receivedMessage, PlayerInfo white, PlayerInfo black, Board board) {
		
		MessageAppPl sendMessage ;
		
		
		if( receivedMessage.effectStage == 1 ) {
			if(receivedMessage.doOn) {
				sendMessage = this.effect1( receivedMessage, white, black, board);
				sendMessage.doOn = true;
			}else {
				sendMessage = new MessageAppPl();
				sendMessage.type = 2;
				sendMessage.roomNumber = receivedMessage.roomNumber;
				sendMessage.effectStage = 1;
				sendMessage.doOn = false;
				sendMessage.candidate = board.judgeCandidate(receivedMessage.color);
				sendMessage.usingBoard = true;
				sendMessage.needStone = true;
				sendMessage.text = "使用するカードもしくは、石の種類を選択してください";
				
			}
		}
		else {
			sendMessage = this.effect2( receivedMessage, white, black, board );
			
			sendMessage.a = board.countScore()[0];
			sendMessage.b = board.countScore()[1];
			
		}
		
		return sendMessage;
	}
	
	
}
