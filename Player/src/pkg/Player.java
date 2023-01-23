package pkg;

class Player {
	private String userID;
	private String password;
	
	Player(String userID,String password){
		this.userID = userID;
		this.password = password;
	}
	public void setuserID(String userID) {
		this.userID = userID;
	}
	public String getuserID() {
		return this.userID;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public String getpassword() {
		return this.password;
	}
}