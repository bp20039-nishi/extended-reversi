package pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class OseroPanel extends JPanel {
    static final int WIDTH = 500; // 画面サイズ（幅）
    static final int HEIGHT = 550; // 画面サイズ（高さ）
    int lm = 0;    // 左側余白
    int tm = 0;   // 上側余白
    int cs = 50;    // マスのサイズ
    int turn = 1; // 手番（1:黒，2:白)
    
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
    //final Color BLACK2 = new Color(4,15,15);
    
    final String FONT = "MS UI Gothic";
    //Dialog, DialogInput, Monospaced, SanSerif, Serif

    // コンストラクタ（初期化処理）
    public OseroPanel() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addMouseListener(new MouseProc());
    }
    
    
    
    // 画面描画
    public void paintComponent(Graphics g) {
    	
        // 背景
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        // 盤面描画
        for (int i = 0; i < 8; i++) {
            int y = tm + cs * i;
            for (int j = 0; j < 8; j++) {
                int x = lm + cs * j;
                
                g.setColor(BOARDCOLOR);
                g.fillRect(x, y, cs, cs);
                
                g.setColor(BLACKCOLOR);
                g.drawRect(x, y, cs, cs);
                
                if(GameInfo.getCandidateInfo()[i][j]) {
                	g.setColor(CANDCOLOR);
                	g.fillRect(x, y, cs, cs);
                	//g.setColor(BOARDCOLOR);
                	//g.fillRect(x+cs/10, y+cs/10, cs*8/10, cs*8/10);
                    g.setColor(BLACKCOLOR);
                    g.drawRect(x, y, cs, cs);
                }
                
                g.setFont(new Font(FONT,Font.BOLD,24));
                g.setColor(CELLRATACOLOR);
                //g.drawString(String.valueOf(GameInfo.getCellRate()[i][j]),x+cs*3/10, y+cs*8/10);
                drawStringCenter(g, String.valueOf(GameInfo.getCellRate()[i][j]), x+cs/2, y+cs/2);
                
                if (GameInfo.getBoardInfo()[i][j].getColor() != pkg.Color.None) {
                	int stoneScore = GameInfo.getBoardInfo()[i][j].getScore();
                	
                    if (GameInfo.getBoardInfo()[i][j].getColor() == pkg.Color.Black) {
                        g.setColor(BLACKCOLOR);
                        g.fillOval(x+cs/10, y+cs/10, cs*8/10, cs*8/10);

                        //点数の色
                   
                        if(stoneScore == 1)
                        	g.setColor(SCOREB1COLOR);
                        else if(stoneScore == 2)
                        	g.setColor(SCOREB2COLOR);
                        else
                        	g.setColor(SCOREB3COLOR);
                        
                    } else {
                        g.setColor(WHITECOLOR);
                        g.fillOval(x+cs/10, y+cs/10, cs*8/10, cs*8/10);
                        
                        //点数の色
                   
                        if(stoneScore == 1)
                        	g.setColor(SCOREW1COLOR);
                        else if(stoneScore == 2)
                        	g.setColor(SCOREW2COLOR);
                        else
                        	g.setColor(SCOREW3COLOR);
                        
                    }
             
                    //g.drawString(String.valueOf(stoneScore * GameInfo.getCellRate()[i][j]),x+cs*3/10, y+cs*8/10);
                    drawStringCenter(g, String.valueOf(stoneScore * GameInfo.getCellRate()[i][j]), x+cs/2, y+cs/2);
                }
            }
        }
    }
    
    
    
    // クリックされた時の処理用のクラス
    class MouseProc extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
        	
        	// 自分のターンかどうか判断する。
        	if ( GameInfo.isMyTurn() ) {
        		int x = e.getX();
                int y = e.getY();
                int selectedStone = -1;

                // 盤の外側がクリックされたときは何もしないで終了
                if (x < lm) return;
                if (x >= lm+cs*8) return;
                if (y < tm) return;
                if (y >= tm+cs*8) return;
                // クリックされたマスを特定
                int row = (y - tm) / cs;
                int col = (x - lm) / cs;
                
                
                // クリックされたマスが候補手の一つであれば
                if( GameInfo.getCandidateInfo()[row][col] ) {
                    // 送信メッセージの作成&色、座標、石の点数の初期化
                    MessageAppPl sendMessage = new MessageAppPl();
                    sendMessage.roomNumber = GameInfo.getRoomNumber();
                	sendMessage.color = GameInfo.getMyColor();
                	
                    for(int i = 0; i<3; i++){
                        if(Main.screenFrame.gamePanel.stoneScore[i].isSelected()){
                            selectedStone = i;
                            System.out.println(i + " : " + selectedStone);
                            break;
                        }
                        System.out.println(i + " : " + selectedStone);
                    }
                    // カード選択中なら
                	if( GameInfo.isCardUsing() ) {
                    	sendMessage.type = 2;
                    	sendMessage.effectStage = 2;
                    	sendMessage.cardID = GameInfo.getUsingCardID();
                        sendMessage.a = row;
                        sendMessage.b = col;

                        if(GameInfo.isNeedStone()){
                            if(selectedStone < 0) {
                            	return;
                            }
                            sendMessage.c = GameInfo.getUsingStoneScore();
                            //Stoneのselected解除
                            Main.screenFrame.gamePanel.stoneScore[selectedStone].doClick();
                        }
                    	// 送信を行う
            			// メッセージ送信
                        TestGameScreen.testMessageSend(sendMessage);
            			Main.pC.sendAppServer(sendMessage);
                    		
                    	// カード使用を記録
                    	GameInfo.setUsingCardID( -2 );
                    	GameInfo.setCardUsing( false );	
                    }
                    // 石置くとき（どの石を置くか選択されているとき）
                    else{
                        // どこを押したかをAppServerに送信する。
                        if( selectedStone >= 0 ){
                            // この、row cal はXYどっちかわからん
                            sendMessage.type = 1;
                            sendMessage.a = row;
                            sendMessage.b = col;
                            sendMessage.c = GameInfo.getUsingStoneScore();
                			
                    		// 送信を行う
            				// メッセージ送信
                            TestGameScreen.testMessageSend(sendMessage);
            				Main.pC.sendAppServer(sendMessage);
                			
                			
                			// 石置き終わったことを記録
                    		GameInfo.setUsingStoneScore(-1);
                		}
                	}
                }
        	}
        }
    }
    //文字を中央に描画する
    public void drawStringCenter(Graphics g,String text,int x,int y){
     FontMetrics fm = g.getFontMetrics();
	 Rectangle rectText = fm.getStringBounds(text, g).getBounds();
	 x=x-rectText.width/2;
	 y=y-rectText.height/2+fm.getMaxAscent();
		
        g.drawString(text, x, y);
	}
    
    
    // 起動時
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new OseroPanel());
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}