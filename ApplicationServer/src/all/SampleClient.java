package all;


public class SampleClient {
	static AppServerEndpoint CRS = new AppServerEndpoint();
	
	public static void main(String[] args) {
		CRS.postFinish(0,"a", "w", "b");
	}
	
	SampleClient() {
	}
}
