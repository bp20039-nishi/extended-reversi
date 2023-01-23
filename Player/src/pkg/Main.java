package pkg;

class Main{
	

	static ScreenFrame screenFrame;
	static PlayerCommunication pC = new PlayerCommunication();
	static String userID; //login後にこの変数にuserIDを代入します.
	
	public static void main(String[] args) {
		
		
		//context
		screenFrame = new ScreenFrame();
		screenFrame.preparePanels();
		screenFrame.prepareComponents();

		//最初の画面(完成後はLOGIN)
		screenFrame.change(ScreenMode.LOGIN);
		//screenFrame.change (ScreenMode.GAME);
		screenFrame.setVisible(true);
		
		
		
	}
	
	
}