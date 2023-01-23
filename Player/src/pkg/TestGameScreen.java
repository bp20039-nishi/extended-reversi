package pkg;

import java.util.ArrayList;

public class TestGameScreen {
	
	
	TestGameScreen() {
		
		this.testSetAll();
		
	}
	
	
	// テスト用
	private void testSetAll() {
		int[] sc = new int[2];
		sc[0] = 1;
		sc[1] = 2;
		
		int[][] cR = new int[8][8];
		Stone[][] bI = new Stone[8][8];
		for( int i=0; i<8; i++ ) {
			for( int j=0; j<8; j++ ) {
				bI[i][j] = new Stone();
				switch(i) {
				case 0:
					bI[i][j].setColor(pkg.Color.Black);
					bI[i][j].setScore(1);
					break;
				case 1:
					bI[i][j].setColor(pkg.Color.Black);
					bI[i][j].setScore(2);
					break;
				case 2:
					bI[i][j].setColor(pkg.Color.Black);
					bI[i][j].setScore(3);
					break;
				case 5:
					bI[i][j].setColor(pkg.Color.White);
					bI[i][j].setScore(1);
					break;
				case 6:
					bI[i][j].setColor(pkg.Color.White);
					bI[i][j].setScore(2);
					break;
				case 7:
					bI[i][j].setColor(pkg.Color.White);
					bI[i][j].setScore(3);
					break;
				default:
					bI[i][j].setColor(pkg.Color.None);
					bI[i][j].setScore(0);
				}
				
				cR[i][j] = (i-4)*2;
			}
			
		}
		
		
		boolean[][] ca = new boolean[8][8];
		for( int i=0; i<8; i++ ) {
			for( int j=0; j<8; j++ ) {
				if( i%2 == 0) ca[i][j] = true;
				else ca[i][j] = false;
			}
		}
		
		GameInfo.setRoomNumber(1);
		GameInfo.setMyColor( pkg.Color.White );
		GameInfo.setMyInfo( testSetPlayerInfo("ore", pkg.Color.White, 1) );
		GameInfo.setEnemyInfo( testSetPlayerInfo("teki", pkg.Color.Black, 2));
		GameInfo.setScore( sc );
		GameInfo.setBoardInfo(bI);
		GameInfo.setCellRate(cR);
		GameInfo.setCandidateInfo(ca);
		GameInfo.setMyLeftTime(1000);
		GameInfo.setEnemyLeftTime(1000);
		GameInfo.setMyTurn(true);
		GameInfo.setCardUsing(false);
		GameInfo.setUsingStoneScore(0);
		GameInfo.setUsingCardID(-1);
		GameInfo.setEndGame(true);
	}
	
	
	
	public static PlayerInfo testSetPlayerInfo(String playerID, pkg.Color color, int record) {
		
		PlayerInfo playerInfo = new PlayerInfo( playerID, color);
		//実行のためrecordを一時的にのぞきました
		
		int[] stone = new int[3];
		stone[0] = 99;
		stone[1] = 10;
		stone[2] = 5;
		playerInfo.setStone( stone );
		
		ArrayList<Integer> cardList = new ArrayList<>();
		cardList.add(1);
		cardList.add(3);
		cardList.add(5);
		playerInfo.setCardID(cardList);
		
		
		return playerInfo;
	}
	
	
	public static void testMessageSend( MessageAppPl message) {
		System.out.println("type = "+message.type);
		System.out.println("myColor = "+message.color);
		System.out.println("a = "+message);
		System.out.println("b = "+message);
		System.out.println("c = "+message);
	}
	
	
	
	
	
	
	
	
	
	
	

}
