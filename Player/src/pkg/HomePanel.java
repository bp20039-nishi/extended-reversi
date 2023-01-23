package pkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class HomePanel extends JPanel {
	JButton matchingBtn; 
	JButton  confirmBattleRecordBtn;
	JButton  confirmRuleBtn;
	JLabel userIDLabel;
	JLabel battleRecordMessage;
	JTextArea ruleMessage;
	/**
	 * Create the panel.
	 */
	public void matching(){
		matchingBtn.setEnabled(false);
		confirmBattleRecordBtn.setEnabled(false);
		confirmRuleBtn.setEnabled(false);
		System.out.println("matching start");
		System.out.println("login後のuserID : "+Main.userID);
		Player player = new Player(Main.userID,null);
		Main.pC.sendLobbyServer("matching",player);
		BoundaryController.changePanel(ScreenMode.MATCHING);
	}
	//戦績ボタン
	public void battleRecord() {
		matchingBtn.setEnabled(false);
		confirmBattleRecordBtn.setEnabled(false);
		confirmRuleBtn.setEnabled(false);
		Player player = new Player(Main.userID,null);
		Main.pC.sendLobbyServer("battleRecord",player);
		
	}
	public void rule() {
		matchingBtn.setEnabled(false);
		confirmBattleRecordBtn.setEnabled(false);
		confirmRuleBtn.setEnabled(false);
		Player player = new Player(Main.userID,null);
		Main.pC.sendLobbyServer("rule",player);
		
	}
	public void resetBtn() {
		matchingBtn.setEnabled(true);
		confirmBattleRecordBtn.setEnabled(true);
		confirmRuleBtn.setEnabled(true);
	}
	public void setUserID() {
		userIDLabel.setText("ようこそ「"+Main.userID+"」さま");
	}

	public HomePanel() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
	}
	
	public void prepareComponents() {
		JLabel lblNewLabel = new JLabel("～拡張リバーシ～");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("HGS行書体", Font.BOLD, 50));
		lblNewLabel.setBounds(211, 65, 447, 149);
		add(lblNewLabel);
		
		matchingBtn = new JButton("プレイ開始");
		matchingBtn.setForeground(Color.BLACK);
		matchingBtn.setFont(new Font("HGS行書体", Font.BOLD, 30));
		matchingBtn.setBackground(Color.WHITE);
		matchingBtn.setBounds(259, 262, 366, 115);
		matchingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ここに戦績ボタンが押された時の処理を書く
				matching();
			}
		});
		add(matchingBtn);
	
			
		
		confirmBattleRecordBtn = new JButton("戦績\r\n");
		confirmBattleRecordBtn.setFont(new Font("HGS行書体", Font.BOLD, 30));
		confirmBattleRecordBtn.setBackground(Color.WHITE);
		confirmBattleRecordBtn.setBounds(472, 449, 186, 40);
		confirmBattleRecordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ここに戦績ボタンが押された時の処理を書く
				battleRecord();
				battleRecordMessage = new JLabel();
				battleRecordMessage.setForeground(Color.RED);
				battleRecordMessage.setText("接続中。。。");
			    JOptionPane.showMessageDialog(lblNewLabel, battleRecordMessage,"戦績", 1);
				Main.screenFrame.homePanel.resetBtn();
			}
		});
		add(confirmBattleRecordBtn);
		
		confirmRuleBtn = new JButton("ルール\r\n");
		confirmRuleBtn.setFont(new Font("HGS行書体", Font.BOLD, 30));
		confirmRuleBtn.setBackground(Color.WHITE);
		confirmRuleBtn.setBounds(211, 449, 186, 40);
		confirmRuleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ここにルールボタンが押された時の処理を書く
				ruleMessage = new JTextArea(15,30);
				ruleMessage.setLineWrap(true);
				ruleMessage.setFont(new Font("HGS行書体", Font.BOLD, 30));
				ruleMessage.setEditable(false);
				rule();
			    JOptionPane.showMessageDialog(lblNewLabel, ruleMessage,"ルール", 1);
			}
		});
		add(confirmRuleBtn);
		
		userIDLabel = new JLabel();
		userIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userIDLabel.setFont(new Font("HGS行書体", Font.BOLD, 20));
		userIDLabel.setBounds(375, 0, 496, 71);
		add(userIDLabel);
	}
}