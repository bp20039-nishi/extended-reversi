package pkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {
	
	TestGameScreen test;
	
	JLabel score;
	JEditorPane message;
	JButton surrenderBtn;
	JLabel  timeLimitLabel;
	JLabel  timeLimitLabelName;
	JButton[]  card;
	JToggleButton[]  stoneScore;
	JLabel[]   enemyStone;
	JLabel[]   enemyCard;
	JButton[][]  board;
	JLabel[][]  cellRate = new JLabel[8][8];
	OseroPanel oseroPanel;
	JToggleButton[] cardButtonList;
	JToggleButton[] enCardButtonList;
	int leftTime;
	static Timer timer;
	
	final String FONT = "ＭＳ ゴシック";
	
	int i,j,k;
	
	final Color BOARDCOLOR = new Color(0,170,0);
    final Color CANDCOLOR = new Color(113,255,66);
    final Color SCOREW1COLOR = new Color(255,220,0);
    final Color SCOREW2COLOR = new Color(230,120,20);
    final Color SCOREW3COLOR = new Color(250,50,70);
    final Color SCOREB1COLOR = new Color(66,208,255);
    final Color SCOREB2COLOR = new Color(66,113,255);
    final Color SCOREB3COLOR = new Color(208,66,255);
    final Color CELLRATACOLOR = new Color(36,130,50);
    final Color BLACKCOLOR = new Color(45,58,58);
    final Color WHITECOLOR = new Color(250,250,250);
	
	/**
	 * Create the panel.
	 */
	
	GamePanel(){
		
		TestGameScreen test = new TestGameScreen() ;
		
		this.setLayout(null);
	}
	
	
	public void  DataSet(){
		score.setText(GameInfo.getScore()[0] + " - " + GameInfo.getScore()[1]);
		if(GameInfo.getMyColor() == pkg.Color.White)
			message.setText("<あなたは"+GameInfo.getMyColor()+">\n" +GameInfo.getText());
		else message.setText( "<あなたは"+GameInfo.getMyColor() + ">\n"+ GameInfo.getText());
		
		if(GameInfo.getMyColor() == pkg.Color.White) {
			stoneScore[0].setBackground(SCOREW1COLOR);
			stoneScore[1].setBackground(SCOREW2COLOR);
			stoneScore[2].setBackground(SCOREW3COLOR);
		}
		else if(GameInfo.getMyColor() == pkg.Color.Black) {
			stoneScore[0].setBackground(SCOREB1COLOR);
			stoneScore[1].setBackground(SCOREB2COLOR);
			stoneScore[2].setBackground(SCOREB3COLOR);
		}
		// 自分の石
		int[] stScore = GameInfo.getMyInfo().getStone();
		for( i=0 ; i<3 ; i++ ) {
			stoneScore[i].setText( (i+1) +"点石" + "×" + stScore[i]);
			stoneScore[i].setSelected(false);
			// 石の残数が0なら無効化
			if( stScore[i] <= 0 || !GameInfo.isMyTurn() )
				stoneScore[i].setEnabled( false );
			else stoneScore[i].setEnabled( true );
			
			if(GameInfo.getEndGame())stoneScore[i].setEnabled(false);
	    }
		
		// 自身のカード
		ArrayList<Integer> cardList = GameInfo.getMyInfo().getCardID();
		for( i=0; i<3; i++ ) {
			
			// リストが3枚以下のとき、無効化する
			if( cardList.size() <= i ) {
				cardButtonList[i].setText("");
				cardButtonList[i].setSelected(false);
				cardButtonList[i].setEnabled(false);
			}else {
				cardButtonList[i].setText( cardList.get(i).toString() );
				cardButtonList[i].setSelected(false);
				cardButtonList[i].setEnabled(true);
				if(GameInfo.isMyTurn()){
					System.out.println("i,ID = " + i + ", "+GameInfo.getUsingCardID());
					if(GameInfo.getUsingCardID() == GameInfo.getMyInfo().getCardID().get(i))cardButtonList[i].setSelected(true);
					else if(GameInfo.getUsingCardID() == -2)cardButtonList[i].setEnabled(false);
					else cardButtonList[i].setSelected(false);
				}else {
					cardButtonList[i].setSelected(false);
					cardButtonList[i].setEnabled(false);
				}
			}
			if(GameInfo.getEndGame())cardButtonList[i].setEnabled(false);
			// カード使用後は無効化
			/*
			if( GameInfo.getUsingCardID() == -2 || !GameInfo.isMyTurn() ) {
				cardButtonList[i].setSelected(false);
				cardButtonList[i].setEnabled( false );
			}
			*/
		}
		
		// 敵の石
		int[] enStScore = GameInfo.getEnemyInfo().getStone();
		for( i=0 ; i<3 ; i++ )
			enemyStone[i].setText( (i+1) +"点石" + "×" + enStScore[i]);		
			    
		
		// 敵のカード
		ArrayList<Integer> enCardList = GameInfo.getEnemyInfo().getCardID();
		// リストが3枚以下のとき、無効化する
		for( i=0; i<3; i++ ) {
			
			enCardButtonList[i].setSelected(false);
			
			if( enCardList.size() <= i ) {
				enCardButtonList[i].setText("");
			}else {
				enCardButtonList[i].setText( enCardList.get(i).toString() );
				if(!GameInfo.isMyTurn()){
					if(GameInfo.getUsingCardID() == GameInfo.getEnemyInfo().getCardID().get(i))enCardButtonList[i].setSelected(true);
				}
			}
			enCardButtonList[i].setEnabled(false);
			/*
			// カード使用後は無効化
			if( GameInfo.getUsingCardID() == -2 || !GameInfo.isMyTurn() ) {
				enCardButtonList[i].setSelected(false);
				enCardButtonList[i].setEnabled( false );
			}
			*/
		}
		
		// ホーム画面へのボタン
		if( GameInfo.getEndGame() ) {
			surrenderBtn.setText("ホームへ");
		} else surrenderBtn.setText("降参");
		
		// オセロ画面の再描画
		oseroPanel.repaint();

	}
	
	// カード使用時(effectStage1)のメッセージ送信
	public void cardEffect( int cardNum, boolean select ) {
		
		//GameInfo.setCardUsing(true);	//多分必要ない
		//GameInfo.setUsingCardID( GameInfo.getMyInfo().getCardID().get(cardNum) );//多分必要ない
		
		MessageAppPl sendMessage = new MessageAppPl();
		sendMessage.doOn = select;
		sendMessage.roomNumber = GameInfo.getRoomNumber();
		sendMessage.color = GameInfo.getMyColor();
		sendMessage.cardID = GameInfo.getMyInfo().getCardID().get(cardNum);
		sendMessage.type = 2;
		sendMessage.effectStage = 1;
		// メッセージ送信
		TestGameScreen.testMessageSend(sendMessage);
		Main.pC.sendAppServer(sendMessage);
	}
	
	
	
	public void prepareComponents() {
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//test = new TestGameScreen();
		
		// 更新するやつ
		score = new JLabel(GameInfo.getScore()[0] + " - " + GameInfo.getScore()[1]);
		score.setFont(new Font("HGS行書体", Font.BOLD, 36));
		score.setBorder(new LineBorder(Color.BLACK, 2, true));
		score.setBounds(30, 161, 180, 65);
		score.setHorizontalAlignment(JLabel.CENTER);
		add(score);
		
		
		JLabel you = new JLabel("YOU");
		you.setFont(new Font("HGS行書体", Font.BOLD, 15));
		you.setBounds(45, 236, 45, 13);
		add(you);
		
		
		// 更新するやつ
		message = new JEditorPane();
		message.setFont(new Font("MS明朝", Font.BOLD, 15));
		message.setEditable(false);
		if( GameInfo.isMyTurn() ) message.setText(GameInfo.getText());
		else message.setText(GameInfo.getText());
		message.setBounds(21, 291, 178, 108);
		add(message);
		
		
		// 試合終了時のホームへ戻る画面に変更
		surrenderBtn = new JButton("降参");
		surrenderBtn.setBounds(736, 491, 100, 50);
		surrenderBtn.setFont(new Font("HGS行書体", Font.BOLD, 15));
		
		//surrenderBtn.setEnabled(false);
		surrenderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!GameInfo.getEndGame()) {
					//時間切れのメッセージを作る(自分は負け)
					MessageAppPl sendMessage = new MessageAppPl();
					sendMessage.type = 3;
					sendMessage.roomNumber = GameInfo.getRoomNumber();
					sendMessage.color = GameInfo.getMyInfo().getColor();

					Main.pC.sendAppServer(sendMessage);
				}else {
				Main.screenFrame.homePanel.resetBtn();
				Main.screenFrame.matchingPanel.resetBtn();
				Main.screenFrame.change(ScreenMode.HOME);
				}
			}
		});
		add(surrenderBtn);	
		
		
		//
		timeLimitLabelName = new JLabel("残り時間");
		timeLimitLabelName.setFont(new Font("HGS行書体", Font.BOLD, 20));
		timeLimitLabelName.setHorizontalAlignment(JLabel.CENTER);
		timeLimitLabelName.setBounds(665, 210, 192, 85);
		add(timeLimitLabelName);
		//
		
		// 更新するやつ（カウントダウン実装）
		timeLimitLabel = new JLabel();
		/*
		if( GameInfo.isMyTurn() ) timeLimitLabel.setText( String.valueOf(GameInfo.getMyLeftTime()) );
		else timeLimitLabel.setText( String.valueOf(GameInfo.getEnemyLeftTime()) );
		*/
		timeLimitLabel.setFont(new Font("HGS行書体", Font.BOLD, 36));
		timeLimitLabel.setBorder(new LineBorder(Color.BLACK, 2, true));
		timeLimitLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLimitLabel.setBounds(665, 184, 192, 85);
		add(timeLimitLabel);
		
		timer = new Timer(1000,new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				//自分のターンの時の画面処理
				if( GameInfo.isMyTurn() ) {
					leftTime = GameInfo.getMyLeftTime() - 1;
					if( leftTime < 0 ) leftTime=0;
					timeLimitLabel.setText( String.valueOf(leftTime) );
					if (GameInfo.getMyLeftTime() <= 0)timer.stop();                       //0秒で止める
					k=GameInfo.getMyLeftTime()-1;
					GameInfo.setMyLeftTime(k);                  //ここで1秒ずつ少なくしている
				}
				//相手のターンの時の画面処理
				else {
					leftTime = GameInfo.getEnemyLeftTime() - 1;
					if( leftTime < 0 ) leftTime=0;
					timeLimitLabel.setText( String.valueOf(leftTime) );
					if (GameInfo.getEnemyLeftTime() <= 0)timer.stop();
					k = GameInfo.getEnemyLeftTime()-1;
					GameInfo.setEnemyLeftTime(k);
				}
			}
		}); 
		
		
		// 自身のカード
		ArrayList<Integer> cardList = GameInfo.getMyInfo().getCardID();
		cardButtonList = new JToggleButton[3];
		for(i=0; i<3; i++) {
			cardButtonList[i] = new JToggleButton();
			cardButtonList[i].setBackground(WHITECOLOR);
			cardButtonList[i].setFont(new Font("HGS行書体", Font.BOLD, 15));
		}
		
		// 押されたときの処理(for文が使えないので、一つずつ)
		//stateChangeを使った方がいいかも
		cardButtonList[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println( "cardButtonList[0] : " + cardButtonList[0].isSelected());
				if(cardButtonList[0].isSelected())cardEffect(0,true);
				else cardEffect(0,false); 
			}
		});
		cardButtonList[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardButtonList[1].isSelected())cardEffect(1,true);
				else cardEffect(1,false);
			}
		});
		cardButtonList[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardButtonList[2].isSelected())cardEffect(2,true);
				else cardEffect(2,false);
			}
		});
		
		for( i=0; i < cardList.size(); i++ ) {
	        cardButtonList[i].setBounds(20+(60*i), 435, 57, 96);
	        add( cardButtonList[i] );
		}
		
		
		// 自身の持ち石
		stoneScore = new JToggleButton[3];
		for( i=0 ; i<3 ; i++ ) {
			stoneScore[i] = new JToggleButton();
			stoneScore[i].setBounds(260+(125*i), 483, 106, 70);
			stoneScore[i].setFont(new Font("HGS行書体", Font.BOLD, 15));
				
	    }
		
		
		
			
		
		stoneScore[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!stoneScore[0].isSelected()) {
					GameInfo.setUsingStoneScore(-1);
					System.out.println("stoneScore[0]:通過");
					return;
				}
				// 石選択中を記録
				GameInfo.setUsingStoneScore(1);
				System.out.println("selectStone = " + 1);

				stoneScore[0].setSelected(true);
				stoneScore[1].setSelected(false);
				stoneScore[2].setSelected(false);
			}
		});
		stoneScore[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!stoneScore[1].isSelected()) {
					GameInfo.setUsingStoneScore(-1);
					System.out.println("stoneScore[1]:通過");
					return;
				}
				// 石選択中を記録
				GameInfo.setUsingStoneScore(2);
				System.out.println("selectStone = " + 2);

				stoneScore[0].setSelected(false);
				stoneScore[1].setSelected(true);
				stoneScore[2].setSelected(false);
			}
		});
		stoneScore[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!stoneScore[2].isSelected()) {
					GameInfo.setUsingStoneScore(-1);
					System.out.println("stoneScore[2]:通過");
					return;
				}
				// 石選択中を記録
				GameInfo.setUsingStoneScore(3);
				System.out.println("selectStone = " + 3);

				stoneScore[0].setSelected(false);
				stoneScore[1].setSelected(false);
				stoneScore[2].setSelected(true);
			}
		});
		for(j=0; j<3; j++) 
			add(stoneScore[j]);
		

		// 敵のカード
		enCardButtonList = new JToggleButton[3];
		for( i=0; i<3; i++ ) {
			enCardButtonList[i] = new JToggleButton();
			enCardButtonList[i].setBorder(new LineBorder(Color.BLACK, 2, true));
			enCardButtonList[i].setFont(new Font("HGS行書体", Font.BOLD, 15));
			enCardButtonList[i].setHorizontalAlignment(JLabel.CENTER);
			enCardButtonList[i].setBounds(680+(60*i), 10, 45, 79);
			enCardButtonList[i].setEnabled( false );
			add( enCardButtonList[i] );
		}
			
		
		
		// 敵の持ち石
		int[] enStScore = GameInfo.getMyInfo().getStone();
		enemyStone = new JLabel[3];
		for( i=0 ; i<3 ; i++ ) {
			enemyStone[i] = new JLabel((i+1) +"点石" + "×" + enStScore[i]);
			enemyStone[i].setFont(new Font("HGS行書体", Font.BOLD, 15));
			enemyStone[i].setBorder(new LineBorder(Color.BLACK, 2, true));
			enemyStone[i].setHorizontalAlignment(JLabel.CENTER);
			enemyStone[i].setBounds(260+(125*i), 10, 99, 53);
	        add(enemyStone[i]);
	    }
		//フラグを初期値に
		GameInfo.setCardUsing(false);
		GameInfo.setUsingBoard(true);
		GameInfo.setNeedStone(true);
		
		
		
		oseroPanel = new OseroPanel();
		oseroPanel.setBounds(225, 75, 400, 400);
		add(oseroPanel);
        
	}
	
	
}
