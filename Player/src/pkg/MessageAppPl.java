package pkg;


public class MessageAppPl {
	
	public int roomNumber;
	public int cardID;		// カード番号
	public boolean doOn;	//カードを選択/非選択
	public int effectStage;	
	public String text;		//カードの説明文記載
	public boolean endTurn;	// カード使用後、石を置ける = false　置けない = true
    public boolean usingBoard;    //カード使用に盤面を用いるか否か
    public boolean needStone;       //カード仕様に石を用いるか否か
	public int type;		// receive    =0:手番交代時  =1:石を置く  =2:カード使用  =3:時間切れ通知
							// send       =0:手番交代時  =1:現在の盤面を返す =2:カード使用 =3:時間切れ通知 =4:ゲーム終了時 =5:ゲーム開始時
	
	
	/*これ以下の変数は、後々まとめられるかも*/
	public Color color;	// receive	  type=0:次手番のプレイヤーの色 type=1:playerColor  =2:カード使用者
						// send			=0,1:送り相手
	
	
	
	public int a;	// receive  type=1:stonePlaceX
					// send     type=1:白の点数   type=4:白の点数
	public int b;	// type=1:stonePlaceY
					// send     type=1:黒の点数   type=4:黒の点数
	public int c;	// type=1:stoneRate
					// send     type=1:手番が始まる人の残り時間
	
	public PlayerInfo white;	// type=0:white(プレイヤーカラー)
	public PlayerInfo black;	// type=0:black(プレイヤーカラー)
	
	public Stone[][] board;		// send		=0:盤面の状態（白黒)
	public int[][] cellRate;	// 
	
	public boolean[][] candidate;	// type=2,cardID=0,a=1:石置く候補マス（全部）
	
}