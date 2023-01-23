package pkg;

public class BoundaryController {
	//static LoginScreen loginscreen;
	static void changePanel(ScreenMode s) {
		
		Main.screenFrame.change(s);
		
			
	}
	
	static void loginError() {
		Main.screenFrame.loginPanel.changeDatabaseColleationError("ログインに失敗しました");
	}
	static void alreadyLoginError() {
		Main.screenFrame.loginPanel.changeDatabaseColleationError("すでにログインしているユーザがいます.");
	}
	static void signUpError() {
		Main.screenFrame.loginPanel.changeDatabaseColleationError("アカウント登録に失敗しました");
	}
	static void setHome() {
		Main.screenFrame.homePanel.setUserID();
	}
	static void matched(int roomNumber, String color) {
		
		GameInfo.setRoomNumber( roomNumber );
		
		if( color.equals("WHITE") )
			GameInfo.setMyColor(Color.White);
		else GameInfo.setMyColor(Color.Black);
		
		Main.screenFrame.matchingPanel.matched();
		System.out.println("BoundaryController : matchedメソッドまできました");
	}
	static void cancel() {
		Main.screenFrame.matchingPanel.resetBtn();
		changePanel(ScreenMode.HOME);
		Main.screenFrame.homePanel.resetBtn();
	
	}
	
	static void showBattleRecord(String battleRecord) {
		String[] nums = battleRecord.split(" ");
		Integer i = Integer.valueOf(nums[0]);
		int win = i.intValue();
		i = Integer.valueOf(nums[1]);
		int lose = i.intValue();
		Main.screenFrame.homePanel.battleRecordMessage.setText(win+"勝 "+lose+"敗");
	}
	static void showRule(String rule) {
		System.out.println("show rule");
		Main.screenFrame.homePanel.ruleMessage.setText(rule);
		Main.screenFrame.homePanel.resetBtn();
	}

}
