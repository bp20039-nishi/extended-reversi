package dbserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityController {
	//アカウントの照合
	public static boolean collation(String tentativeUserName, String tentativeUserPass) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//DB接続
		try {
			// データベース接続
			conn = DriverManager.getConnection(
				// ホスト名、データベース名
				"jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13308/db_group_d",
				// ユーザー名
				"group_d",
				// パスワード
				"group_d");

			// SQL文をセット
			ps = conn.prepareStatement("SELECT * FROM userlist");
			// SQL文を実行
			rs = ps.executeQuery();

			// ループして1レコードずつ取得
			while (rs.next()) {
				String name  = rs.getString("name");
				String password = rs.getString("password");
				int win      = rs.getInt("win");
				int lose     = rs.getInt("lose");

				if (name.equals(tentativeUserName) == true && password.equals(tentativeUserPass) == true) {
					result = true;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("データベース接続エラー．");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("データベース接続エラー．");
				}
		}
		return result;
	}
	
	//アカウントの追加
	public static boolean addDatabase(String addUserName, String addUserPass) {
		boolean result = true;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//DB接続
		try {
			// データベース接続
			conn = DriverManager.getConnection(
				// ホスト名、データベース名
				"jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13308/db_group_d",
				// ユーザー名
				"group_d",
				// パスワード
				"group_d");

			// SQL文をセット
			ps = conn.prepareStatement("SELECT * FROM userlist");
			// SQL文を実行
			rs = ps.executeQuery();

			// ループして1レコードずつ取得
			while (rs.next()) {
				String name  = rs.getString("name");
				String password = rs.getString("password");
				int win      = rs.getInt("win");
				int lose     = rs.getInt("lose");

				if (name.equals(addUserName) == true) {	
					result = false;
		            break;
				}
			}
			
			if (result == true) {
				// SQL文をセット
				String sql = "INSERT INTO userlist VALUES (?, ?, 0, 0)";
			
				//実行するSQL文とパラメータを指定する
				ps = conn.prepareStatement(sql);
				ps.setString(1, addUserName);
				ps.setString(2, addUserPass);	
            
				//INSERT文を実行する
				int i = ps.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("データベース接続エラー．");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("データベース接続エラー．");
				}
		}
		return result;
	}
	
	//勝利回数を送信する
	public static int sendWin(String tentativeUser) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int sendWin = 0;
		
		//DB接続
		try {
			// データベース接続
			conn = DriverManager.getConnection(
				// ホスト名、データベース名
				"jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13308/db_group_d",
				// ユーザー名
				"group_d",
				// パスワード
				"group_d");

			// SQL文をセット
			ps = conn.prepareStatement("SELECT * FROM userlist");
			// SQL文を実行
			rs = ps.executeQuery();

			// ループして1レコードずつ取得
			while (rs.next()) {
				String name  = rs.getString("name");
				String password = rs.getString("password");
				int win      = rs.getInt("win");
				int lose     = rs.getInt("lose");

				if (name.equals(tentativeUser) == true) {
					sendWin = win;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("データベース接続エラー．");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("データベース接続エラー．");
				}
		}
		return sendWin;
	}
	
	//敗北回数を送信する
	public static int sendLose(String tentativeUser) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int sendLose = 0;
		
		//DB接続
		try {
			// データベース接続
			conn = DriverManager.getConnection(
				// ホスト名、データベース名
				"jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13308/db_group_d",
				// ユーザー名
				"group_d",
				// パスワード
				"group_d");

			// SQL文をセット
			ps = conn.prepareStatement("SELECT * FROM userlist");
			// SQL文を実行
			rs = ps.executeQuery();

			// ループして1レコードずつ取得
			while (rs.next()) {
				String name  = rs.getString("name");
				String password = rs.getString("password");
				int win      = rs.getInt("win");
				int lose     = rs.getInt("lose");

				if (name.equals(tentativeUser) == true) {
					sendLose = lose;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("データベース接続エラー．");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("データベース接続エラー．");
				}
		}
		return sendLose;
	}
		
	
	//戦績の更新
	public static void updateBattleRecord(String updateUser, String result) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int updateWin = 0;
		int updateLose = 0;
		
		//DB接続
		try {
			// データベース接続
			conn = DriverManager.getConnection(
				// ホスト名、データベース名
				"jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13308/db_group_d",
				// ユーザー名
				"group_d",
				// パスワード
				"group_d");
			
			// SQL文をセット
			ps = conn.prepareStatement("SELECT * FROM userlist");
			// SQL文を実行
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String name  = rs.getString("name");
				String password = rs.getString("password");
				int win      = rs.getInt("win");
				int lose     = rs.getInt("lose");

				if (name.equals(updateUser) == true) {
					updateWin = win;
					updateLose = lose;
					break;
				}
			}

			// SQL文をセット
			if (result.equals("win") == true) {
				Statement stmt = conn.createStatement();
				String sql = "UPDATE userlist SET win=? WHERE name=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, updateWin + 1);
				ps.setString(2, updateUser);
			}else if (result.equals("lose") == true){
				Statement stmt = conn.createStatement();
				String sql = "UPDATE userlist SET lose=? WHERE name=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, updateLose + 1);
				ps.setString(2, updateUser);				
			}
	          
	        //UPDATE文を実行する
	          int i = ps.executeUpdate();
	          

		} catch (SQLException e) {
			System.out.println("データベース接続エラー．");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("データベース接続エラー．");
				}
		}
	}
}
