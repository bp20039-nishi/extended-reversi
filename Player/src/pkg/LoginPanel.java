package pkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {
	private JTextField userIDtextField;
	private JLabel userIDError;
	private JLabel passwordError;
	private JLabel databaseColleationError;
	private JPasswordField passwordField;
	private JButton loginBtn;
	private JButton btnSignUp;
	/**
	 * Create the panel.
	 */
	void login(String userID,String password){
		loginBtn.setEnabled(false);
		btnSignUp.setEnabled(false);
		//ログイン処理
		System.out.println("login === "+userID +":"+ password);
		Player player = new Player(userID,password);
		Main.pC.sendLobbyServer("login",player);
	}
	void signUp(String userID,String password) {
		btnSignUp.setEnabled(false);
		loginBtn.setEnabled(false);
		System.out.println("signup === "+userID +":"+ password);
		//登録処理
		Player player = new Player(userID,password);
		Main.pC.sendLobbyServer("sign up", player);
	}
	void resetErrorText() {
		userIDError.setText("");
		passwordError.setText("");
	}
	void changeUserIDErrorMessage(String s) {
		userIDError.setText(s);
	}
	void changePasswordErrorMessage(String s) {
		passwordError.setText(s);
	}
	void changeDatabaseColleationError(String s) {
		resetErrorText();
		databaseColleationError.setText(s);
		loginBtn.setEnabled(true);
		btnSignUp.setEnabled(true);
	}
	//コンストラクタ
	public LoginPanel() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
	}
	
	public void prepareComponents() {
		JLabel lblNewLabel = new JLabel("～拡張リバーシ～");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 900, 208);
		lblNewLabel.setFont(new Font("HGS行書体", Font.BOLD, 50));
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("UserID");
		lblNewLabel_1.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		lblNewLabel_1.setBounds(248, 237, 103, 40);
		add(lblNewLabel_1);
		
		userIDtextField = new JTextField();
		userIDtextField.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		userIDtextField.setBounds(423, 239, 214, 36);
		add(userIDtextField);
		userIDtextField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new  JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		lblNewLabel_1_1.setBounds(248, 329, 141, 40);
		add(lblNewLabel_1_1);
		
		
		
		loginBtn = new JButton("LOGIN");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean login = true;
				String password = new String(passwordField.getPassword());
				resetErrorText();
				if(userIDtextField.getText().length() == 0) {
					userIDError.setText("userIDが入力されていません");
					login  = false;
				}
				if(password.length() == 0) {
					passwordError.setText("Passwordが入力されていません");
					login  = false;
				}
				if(userIDtextField.getText().length() > 30) {
					userIDError.setText("userIDが長すぎます(30字以下)");
					login  = false;
				}
				if(password.length() > 30) {
					passwordError.setText("Passwordが長すぎます(30字以下)");
					login  = false;
				}
				if(login) {
					login(userIDtextField.getText(),password);
				}
			}
		});
		loginBtn.setForeground(Color.BLACK);
		loginBtn.setBackground(Color.WHITE);
		loginBtn.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		loginBtn.setBounds(235, 440, 186, 40);
		add(loginBtn);
		
		btnSignUp = new JButton("SIGN UP");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean signUp = true;
				String password = new String(passwordField.getPassword());
				resetErrorText();
				if(userIDtextField.getText().length() == 0) {
					userIDError.setText("userIDが入力されていません");
					signUp  = false;
				}
				if(password.length() == 0) {
					passwordError.setText("Passwordが入力されていません");
					signUp  = false;
				}
				if(userIDtextField.getText().length() > 30) {
					userIDError.setText("userIDが長すぎます(30字以下)");
					signUp  = false;
				}
				if(password.length() > 30) {
					passwordError.setText("Passwordが長すぎます(30字以下)");
					signUp  = false;
				}
				if(signUp) {
					signUp(userIDtextField.getText(),password);
				}
			}
		});
		btnSignUp.setBackground(Color.WHITE);
		btnSignUp.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		btnSignUp.setBounds(496, 440, 186, 40);
		add(btnSignUp);
		
		userIDError = new JLabel();
		userIDError.setForeground(Color.RED);
		userIDError.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		userIDError.setBounds(423, 197, 292, 44);
		add(userIDError);
		
		passwordError = new JLabel();
		passwordError.setForeground(Color.RED);
		passwordError.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		passwordError.setBounds(423, 292, 280, 40);
		add(passwordError);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		passwordField.setEchoChar('●');
		passwordField.setBounds(423, 333, 214, 36);
		add(passwordField);
		
		databaseColleationError = new JLabel();
		databaseColleationError.setForeground(Color.RED);
		databaseColleationError.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		databaseColleationError.setBounds(189, 151, 573, 49);
		add(databaseColleationError);
	}
}