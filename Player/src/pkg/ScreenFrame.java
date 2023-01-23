package pkg;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ScreenFrame extends JFrame {

	private static final long serialVersionUID = 1L; //おまじない

	final int WIDTH = 900; //フレームの幅
	final int HEIGHT = 600; //フレームの高さ
	
	CardLayout layout = new CardLayout(); // パネルを持っておく用
	
	LoginPanel loginPanel;
	HomePanel homePanel;
	MatchingPanel matchingPanel;
	GamePanel gamePanel;
	
	ScreenMode screenMode = ScreenMode.LOGIN;
	
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScreenFrame frame = new ScreenFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ScreenFrame() {
		
		this.setTitle("拡張リバーシ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(layout);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
	    ImageIcon icon = new ImageIcon(
	    	ScreenFrame.class.getResource("icon.jpg"));
	    	this.setIconImage(icon.getImage());
		this.pack();
		this.setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e) {
        		if(screenMode == ScreenMode.GAME) {
					MessageAppPl sendMessage = new MessageAppPl();
					sendMessage.type = 3;
					sendMessage.roomNumber = GameInfo.getRoomNumber();
					sendMessage.color = GameInfo.getMyInfo().getColor();

					Main.pC.sendAppServer(sendMessage);
        		}
        	}
		});
		
	}
	
	public void preparePanels() {
		
		loginPanel = new LoginPanel();
		this.add(loginPanel,"ログイン画面");
		homePanel = new HomePanel();
		this.add(homePanel,"ホーム画面");
		matchingPanel = new MatchingPanel();
		this.add(matchingPanel,"マッチング画面");
		gamePanel = new GamePanel();
		this.add(gamePanel,"ゲーム画面");
		
		this.pack();
		
	}
	
	public void prepareComponents() {
		loginPanel.prepareComponents();
		homePanel.prepareComponents();
		matchingPanel.prepareComponents();
		gamePanel.prepareComponents();
	}
	
	
	public void change(ScreenMode s) {
	
		screenMode = s;	
		
		switch(screenMode) {
		case LOGIN:
			layout.show(this.getContentPane(), "ログイン画面");
			loginPanel.requestFocus();
			break;
		case HOME:
			layout.show(this.getContentPane(), "ホーム画面");
			homePanel.requestFocus();
			break;
		case MATCHING:
			layout.show(this.getContentPane(),"マッチング画面");
			matchingPanel.requestFocus();
			break;
		case GAME:
			layout.show(this.getContentPane(), "ゲーム画面");
			gamePanel.requestFocus();
		
		}
	}

}