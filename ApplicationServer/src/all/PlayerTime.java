package all;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.Session;

import com.google.gson.Gson;

public class PlayerTime {

	private static final int plusTime = 30;
	private int turnTime;
	private int timeLeft;
	private Timer timer = new Timer();
	private Count count = new Count();
	private Session session;
	
	PlayerTime(Session session){
		this.session = session;
	}
	
	void setTime(int timeLeft){
		this.timeLeft = timeLeft;
	}
	int getTime() {
		return this.timeLeft + PlayerTime.plusTime;
	}
	
	void startCount(){
		if(count == null){
			timer = new Timer();
			count = new Count();
		}
		turnTime = timeLeft + plusTime;
		timer.schedule(count,0,1000);
	}
	void stopCount(){
		System.out.println("stop:1");
		timer.cancel();
		System.out.println("stop:2");
		count = null;
		if(turnTime < timeLeft) {
			timeLeft = turnTime;
		}
		System.out.println("stop:3");
	}
	
	class Count extends TimerTask{
		public void run() {
			turnTime --;
			if(turnTime < 0){
				//セッションを用いてプレイヤーに通知
				this.timeOut();				
				timer.cancel();
			}
		}
		
		void timeOut() {
			System.out.println("時間切れ");
			MessageAppPl sendMessage = new MessageAppPl();
			Gson gson = new Gson();
			
			sendMessage.type = 3;
			
			String jsonMessage = gson.toJson(sendMessage);
			session.getAsyncRemote().sendText(jsonMessage);
		}
		
	}
	
}