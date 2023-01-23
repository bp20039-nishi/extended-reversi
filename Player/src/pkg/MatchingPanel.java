package pkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class MatchingPanel extends JPanel {
	
	JButton cancelBtn;
	boolean cancelFlag;
	Timer timer;
	
	//猫表示用変数
	ImageIcon nekoImage_1;
	ImageIcon nekoImage_2;
	JLabel nekoLabel_1;
	JLabel nekoLabel_2;
	MediaTracker tracker;
	Image smallImg;
	int x=-100;
	int y=300;
	boolean catFlag;
	
	/**
	 * Create the panel.
	 */
	
	MatchingPanel(){
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
	}
	void cancel() {
		cancelBtn.setEnabled(false);
		cancelFlag = true;
		Main.pC.sendLobbyServer("cancel",new Player(Main.userID,null));
	}
	void matched() {
		
		cancelBtn.setEnabled(false);
		
		MessageAppPl sendMessage = new MessageAppPl();
		sendMessage.type = 5;
		sendMessage.roomNumber = GameInfo.getRoomNumber();
		sendMessage.color = GameInfo.getMyColor();
		System.out.println(GameInfo.getMyColor());
		System.out.println("ルームにSession教えに行く");

		
		PlayerCommunication.appManager.connect();
		Main.pC.sendAppServer( sendMessage );
		
	}
	void resetBtn() {
		cancelBtn.setEnabled(true);
	}
	public void prepareComponents() {
		cancelFlag = false;
		setBorder(new EmptyBorder(5, 5, 5, 5));
				
		JLabel lblNewLabel = new JLabel("まっちんぐ...");
		lblNewLabel.setBounds(350, 200, 200, 156);
		lblNewLabel.setFont(new Font("HGS行書体", Font.PLAIN,30));
		add(lblNewLabel);
		
		cancelBtn = new JButton("キャンセル");
		cancelBtn.setBackground(Color.WHITE);
		cancelBtn.setFont(new Font("HGS行書体", Font.BOLD, 30));
		cancelBtn.setBounds(600, 500,  250, 50);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ここにプレイキャンセルボタンが押された時の処理を書く	
				cancel();
			}
		});
		add(cancelBtn);
		
		
		
		
		//猫表示用
		MediaTracker tracker = new MediaTracker(this);
		
		nekoImage_1 = new ImageIcon(getClass().getResource("neko_1.png"));
		smallImg = nekoImage_1.getImage().getScaledInstance((int) (nekoImage_1.getIconWidth()*0.1), -1, Image.SCALE_SMOOTH);
	    tracker.addImage(smallImg, 1);
	    nekoImage_1 =  new ImageIcon(smallImg);
	    
	    nekoImage_2 = new ImageIcon(getClass().getResource("neko_2.png"));
		smallImg = nekoImage_2.getImage().getScaledInstance((int) (nekoImage_2.getIconWidth()*0.1), -1, Image.SCALE_SMOOTH);
	    tracker.addImage(smallImg, 2);
	    nekoImage_2 =  new ImageIcon(smallImg);
	    
		try {
	        tracker.waitForAll();
	      } catch (InterruptedException e) {
	        System.out.println("猫を小さくするところでエラー");
	      }
		
		nekoLabel_1 = new JLabel(nekoImage_1);
		//nekoLabel_1.setBorder(new LineBorder(Color.BLUE, 2, false));
		nekoLabel_1.setBounds(x/*ここを変えていく*/, y,  150, 50);
		add(nekoLabel_1);
		
		nekoLabel_2 = new JLabel(nekoImage_2);
		//nekoLabel_2.setBorder(new LineBorder(Color.BLUE, 2, false));
		//nekoLabel_2.setBounds(850/*ここを変えていく*/, y,  150, 50);
		add(nekoLabel_2);



		
		timer = new Timer(200,new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				if(catFlag) {
					nekoLabel_1.setBounds(1000, 1000,  150, 50);
					nekoLabel_2.setBounds(x, y, 150, 50);
					nekoLabel_1.repaint();
					nekoLabel_2.repaint();
					catFlag = false;
					
				}else {
					nekoLabel_1.setBounds(x, y,  150, 50);
					nekoLabel_2.setBounds(1000, 1000, 150, 50);
					nekoLabel_1.repaint();
					nekoLabel_2.repaint();
					catFlag = true;
				}
				
				x=x+20;
				if(x>850) x = -100;
				
			}});
		
		timer.start();

	}

}