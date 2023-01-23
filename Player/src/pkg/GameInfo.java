package pkg;

public class GameInfo {
	
	private static int roomNumber;
	private static pkg.Color myColor;
	private static PlayerInfo myInfo;
	private static PlayerInfo enemyInfo;
	private static int[] score = {2,2};
	private static Stone[][] boardInfo;
	private static int[][] cellRate;
	private static boolean[][] candidateInfo;
	private static boolean[][] tmpCandidateInfo;
	private static int myLeftTime;
	private static int enemyLeftTime;
	private static boolean myTurn;
	private static String text;
	private static boolean isCardUsing;
	private static int usingStoneScore;
	private static int usingCardID;	// 使う前は-1、使い終わったら、そのターン中-2
	private static boolean endGame = true;
    private static boolean usingBoard;
    private static boolean needStone;
	
	
	// 初期化時に、すでに作成していた自身のプレイヤー情報をいれる	
	public static PlayerInfo getMyInfo() {
		return myInfo;
	}
	public static void setMyInfo(PlayerInfo newMyInfo) {
		myInfo = newMyInfo;
	}

	public static pkg.Color getMyColor() {
		return myColor;
	}
	public static void setMyColor(pkg.Color newMyColor) {
		myColor = newMyColor;
	}

	public static Stone[][] getBoardInfo() {
		return boardInfo;
	}
	public static void setBoardInfo(Stone[][] newBoardInfo) {
		boardInfo = newBoardInfo;
	}

	public static PlayerInfo getEnemyInfo() {
		return enemyInfo;
	}
	public static void  setEnemyInfo(PlayerInfo newEnemyInfo) {
		enemyInfo = newEnemyInfo;
	}

	public static boolean[][] getCandidateInfo() {
		return candidateInfo;
	}
	public static void setCandidateInfo(boolean[][] newCandidateInfo) {
		candidateInfo = newCandidateInfo;
	}

	public static int getMyLeftTime() {
		return myLeftTime;
	}
	public static void setMyLeftTime(int myLeftTime) {
		GameInfo.myLeftTime = myLeftTime;
	}

	public static int getEnemyLeftTime() {
		return enemyLeftTime;
	}
	public static void setEnemyLeftTime(int enemyLeftTime) {
		GameInfo.enemyLeftTime = enemyLeftTime;
	}

	public static int getRoomNumber() {
		return roomNumber;
	}
	public static void setRoomNumber(int roomNumber) {
		GameInfo.roomNumber = roomNumber;
	}

	public static int[] getScore() {
		return score;
	}
	public static void setScore(int[] score) {
		
		if(GameInfo.getMyColor()==Color.White)
			GameInfo.score = score;
		else {
			int[] scoreIn = {score[1], score[0]};
			GameInfo.score = scoreIn;
		}
			
			
	}
	
	public static int[][] getCellRate() {
		return cellRate;
	}
	public static void setCellRate(int[][] cellRate) {
		GameInfo.cellRate = cellRate;
	}

	public static boolean isMyTurn() {
		return myTurn;
	}
	public static void setMyTurn(boolean yourTurn) {
		GameInfo.myTurn = yourTurn;
	}
	
	public static String getText() {
		return text;
	}
	public static void setText(String text) {
		GameInfo.text = text;
	}
	
	public static boolean isCardUsing() {
		return isCardUsing;
	}
	public static void setCardUsing(boolean isCardUsing) {
		GameInfo.isCardUsing = isCardUsing;
	}
	
	public static int getUsingStoneScore() {
		return usingStoneScore;
	}
	public static void setUsingStoneScore(int usingStoneScore) {
		GameInfo.usingStoneScore = usingStoneScore;
	}
	
	public static int getUsingCardID() {
		return usingCardID;
	}
	public static void setUsingCardID(int usingCardID) {
		GameInfo.usingCardID = usingCardID;
	}
	public static boolean getEndGame() {
		return endGame;
	}
	public static void setEndGame(boolean endGame) {
		GameInfo.endGame = endGame;
	}

    public static boolean isUsingBoard(){
        return usingBoard;
    }

    public static void setUsingBoard(boolean usingBoard){
        GameInfo.usingBoard = usingBoard;
    }
    
    public static boolean isNeedStone() {
    	return needStone;
    }
    
    public static void setNeedStone(boolean ns) {
    	GameInfo.needStone = ns;
    }
    
	public static boolean[][] getTmpCandidateInfo() {
		return tmpCandidateInfo;
	}
	public static void setTmpCandidateInfo(boolean[][] tmp) {
		GameInfo.tmpCandidateInfo = tmp;
	}
}
